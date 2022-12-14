package cms.sogi_cms.cms.file.service.impl;

import cms.sogi_cms.cms.configuration.entity.Configuration;
import cms.sogi_cms.cms.configuration.service.ConfigurationService;
import cms.sogi_cms.cms.file.dto.FileResponseDto;
import cms.sogi_cms.cms.file.dto.FileSearch;
import cms.sogi_cms.cms.file.entity.File;
import cms.sogi_cms.cms.file.entity.ThumbnailFile;
import cms.sogi_cms.cms.file.repository.FileRepository;
import cms.sogi_cms.cms.file.repository.ThumbnailFileRepository;
import cms.sogi_cms.cms.file.service.FileService;
import cms.sogi_cms.cms.support.SogiConstant;
import cms.sogi_cms.cms.support.pagination.Paging;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {

    private final String CONFIGURATION_ID = "file";
    private final String UPLOAD_WHITE_LIST = "upload_white_list";
    private final String THUMBNAIL_DEFAULT_WIDTH = "thumbnail_default_width";
    private final String THUMBNAIL_DEFAULT_HEIGHT = "thumbnail_default_height";
    private final String UPLOAD_LIMIT_SIZE = "upload_limit_size";

    private final ConfigurationService configurationService;

    private final FileRepository fileRepository;
    private final ThumbnailFileRepository thumbnailFileRepository;

    @Override
    public Long saveFile(MultipartFile multipartFile) throws IOException {
        StringBuilder uploadErrorMessage = new StringBuilder();
        boolean isUploadSuccess = uploadFileValidation(multipartFile, uploadErrorMessage);
        if (!isUploadSuccess) {
            throw new IOException(uploadErrorMessage.toString());
        }

        String uploadDirectory = getUploadDirectory();
        String hashedFilename = UUID.randomUUID() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());

        // ?????? ?????? ??? ??????
        FileUtils.forceMkdir(new java.io.File(uploadDirectory));

        java.io.File uploadFile = new java.io.File(uploadDirectory + hashedFilename);
        FileCopyUtils.copy(
                multipartFile.getInputStream(),
                new FileOutputStream(uploadFile));
        log.debug("[SOGICMS] ????????? ?????????????????????. ?????? ?????? ?????? ??? ?????????: {}", uploadFile.getAbsolutePath());

        File file = File.create(multipartFile, uploadFile, uploadDirectory, hashedFilename);

        if (file.getFileMimeType().contains("image")) {
            String thumbnailFilePath = makeThumbnail(uploadFile);
            ThumbnailFile thumbnailFile = ThumbnailFile.create(new java.io.File(thumbnailFilePath));

            file.setThumbnailFile(thumbnailFile);

            thumbnailFileRepository.save(thumbnailFile);
        }

        fileRepository.save(file);

        return file.getId();
    }

    private String getUploadDirectory() {
        StringBuilder uploadDirectory = new StringBuilder();
        YearMonth yearMonth = YearMonth.from(LocalDate.now());

        uploadDirectory
                .append(SogiConstant.UPLOAD_PATH)
                .append(SogiConstant.SYSTEM_SEPARATOR)
                .append(SogiConstant.SITE_CONSTANT)
                .append(SogiConstant.SYSTEM_SEPARATOR)
                .append(yearMonth.getYear())
                .append(SogiConstant.SYSTEM_SEPARATOR)
                .append(yearMonth.getMonth().getValue())
                .append(SogiConstant.SYSTEM_SEPARATOR);

        log.debug("[SOGICMS] ?????? ????????? ??????: {}", uploadDirectory);

        return uploadDirectory.toString();
    }

    private boolean uploadFileValidation(MultipartFile multipartFile, StringBuilder uploadErrorMessage) {
        List<String> uploadWhiteList = getUploadWhiteList();

        int uploadLimitSize = Integer.parseInt(
                configurationService.getConfigurationById(CONFIGURATION_ID, UPLOAD_LIMIT_SIZE).getOptionValue());

        // ???????????? ?????? ????????? ????????? ??????, ????????? ???????????? ?????? ?????? ????????? ?????????.
        if (!isUploadPossible(multipartFile.getOriginalFilename(), uploadWhiteList)) {
            uploadErrorMessage.append("???????????? ???????????? ?????? ???????????? ?????? ???????????????.");
            return false;
        }

        // ?????????????????? ?????? ????????? ???????????? ???????????? ?????? ???????????? ??? ??????, ??????????????? ????????? ???????????? ?????? ?????? ????????? ?????????.
        if (multipartFile.getSize() > uploadLimitSize) {
            uploadErrorMessage
                    .append("????????? ????????? ???????????? ?????? ???????????? ?????????. ????????? ?????? ?????? ??????: ")
                    .append(uploadLimitSize)
                    .append("byte");
            return false;
        }

        return true;
    }

    private List<String> getUploadWhiteList() {
        String stringWhiteList = configurationService.getConfigurationById(CONFIGURATION_ID, UPLOAD_WHITE_LIST).getOptionValue();
        return List.of(stringWhiteList.split(","));
    }

    private boolean isUploadPossible(String filename, List<String> uploadWhiteList) {
        String extension = FilenameUtils.getExtension(filename);
        for (String s : uploadWhiteList) {
            if (extension.equals(s)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public File getFileByFileId(Long id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("????????? ?????? ??? ????????????."));
    }

    @Override
    public Paging<FileResponseDto> getFileList(FileSearch fileSearch) {
        List<FileResponseDto> contents = fileRepository.findList(fileSearch).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
        Long total = fileRepository.count(fileSearch);

        return new Paging<>(contents, total, fileSearch);
    }

    @Override
    public FileResponseDto toResponseDto(File file) {
        FileResponseDto dto = new FileResponseDto();
        dto.setFilePath(file.getFilePath());
        dto.setFileOriginalName(file.getFileOriginalName());
        dto.setFileHashedName(file.getFileHashedName());
        dto.setFileExtension(file.getFileExtension());
        dto.setFileMimeType(file.getFileMimeType());
        dto.setFileSize(file.getFileSize());
        dto.setRegisteredDateTime(file.getRegisteredDateTime());
        dto.setDownloadCount(file.getDownloadCount());

        return dto;
    }

    @Override
    public void deleteFile(Long id) throws FileNotFoundException {
        File fileInformation = fileRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException("????????? ?????? ??? ????????????."));

        // ?????? ?????? ??????
        deleteActualFile(fileInformation);

        // ????????????????????? ?????? ?????? ?????? ??????
        deleteFileInformation(fileInformation);
    }

    private void deleteFileInformation(File fileInformation) {
        if (fileInformation.getFileMimeType().contains("image")) {
            ThumbnailFile thumbnailFile = fileInformation.getThumbnailFile();
            thumbnailFileRepository.delete(thumbnailFile);
        }

        fileRepository.delete(fileInformation);
    }

    private void deleteActualFile(File fileInformation) {
        java.io.File file = new java.io.File(fileInformation.getFilePath());

        if (file.exists()) {
            deleteFile(fileInformation.getFileOriginalName(), file);
        } else {
            log.info("[SOGICMS] [{}] ????????? ???????????? ????????????.", fileInformation.getFileOriginalName());
        }

        // ????????? ??????????????? ???????????? ???????????? ??????.
        if (fileInformation.getFileMimeType().contains("image")) {
            ThumbnailFile thumbnailFileInformation = fileInformation.getThumbnailFile();

            java.io.File thumbnailFile = new java.io.File(thumbnailFileInformation.getFilePath());

            String thumbnailFileName = fileInformation.getFileOriginalName() + "??? ????????? ??????";
            if (thumbnailFile.exists()) {
                deleteFile(thumbnailFileName, thumbnailFile);
            } else {
                log.info("[SOGICMS] [{}] ????????? ???????????? ????????????.", thumbnailFileName);
            }
        }
    }

    private void deleteFile(String fileName, java.io.File file) {
        boolean deleteSuccess = file.delete();

        if (deleteSuccess) {
            log.info("[SOGICMS] [{}] ????????? ??????????????? ?????????????????????.", fileName);
        } else {
            log.info("[SOGICMS] [{}] ????????? ??????????????? ???????????? ???????????????.", fileName);
        }
    }

    @Override
    public String makeThumbnail(java.io.File uploadFile) throws IOException {
        String thumbnailFilePath = uploadFile.getAbsolutePath().replace(".", "_thumbnail.");

        int thumbnailDefaultWidth = Integer.parseInt(
                configurationService.getConfigurationById(CONFIGURATION_ID, THUMBNAIL_DEFAULT_WIDTH).getOptionValue());

        int thumbnailDefaultHeight = Integer.parseInt(
                configurationService.getConfigurationById(CONFIGURATION_ID, THUMBNAIL_DEFAULT_HEIGHT).getOptionValue());

        Thumbnails.of(uploadFile)
                .sourceRegion(Positions.CENTER, thumbnailDefaultWidth, thumbnailDefaultHeight)
                .size(thumbnailDefaultWidth, thumbnailDefaultHeight)
                .toFile(thumbnailFilePath);

        return thumbnailFilePath;
    }

    @Override
    public void addOneDownloadCount(Long id) throws IOException {
        File fileInformation = fileRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException("????????? ?????? ??? ????????????."));

        fileInformation.addOneDownloadCount();
    }
}
