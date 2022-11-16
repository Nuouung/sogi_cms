package cms.sogi_cms.cms.file.service.impl;

import cms.sogi_cms.cms.file.entity.File;
import cms.sogi_cms.cms.file.entity.ThumbnailFile;
import cms.sogi_cms.cms.file.repository.FileRepository;
import cms.sogi_cms.cms.file.repository.ThumbnailFileRepository;
import cms.sogi_cms.cms.file.service.FileService;
import cms.sogi_cms.cms.support.SogiConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {

    private final String UPLOAD_WHITE_LIST = "upload_white_list";

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

        // 파일 생성 후 저장
        FileUtils.forceMkdir(new java.io.File(uploadDirectory));

        java.io.File uploadFile = new java.io.File(uploadDirectory + hashedFilename);
        FileCopyUtils.copy(
                multipartFile.getInputStream(),
                new FileOutputStream(uploadFile));
        log.debug("[SOGICMS] 파일이 저장되었습니다. 파일 저장 경로 및 파일명: {}", uploadFile.getAbsolutePath());

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

        log.debug("[SOGICMS] 파일 업로드 경로: {}", uploadDirectory);

        return uploadDirectory.toString();
    }

    // TODO validation으로 옮길 것
    private boolean uploadFileValidation(MultipartFile multipartFile, StringBuilder uploadErrorMessage) {
        // TODO 설정 db 만들어야 한다.
        List<String> uploadWhiteList = List.of(new String[]{"jpg"});
        Long uploadSizeLimit = 100000L;

        // 지원하지 않는 업로드 파일인 경우, 파일을 저장하지 않고 에러 정보를 담는다.
        if (!isUploadPossible(multipartFile.getOriginalFilename(), uploadWhiteList)) {
            uploadErrorMessage.append("시스템이 지원하지 않는 확장자를 가진 파일입니다.");
            return false;
        }

        // 업로드하고자 하는 파일이 시스템이 허용하는 최대 크기보다 큰 경우, 마찬가지로 파일을 저장하지 않고 에러 정보를 담는다.
        if (multipartFile.getSize() > uploadSizeLimit) {
            uploadErrorMessage
                    .append("파일의 크기가 시스템의 허용 범위보다 큽니다. 업로드 가능 최대 크기: ")
                    .append(uploadSizeLimit)
                    .append("byte");
            return false;
        }

        return true;
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
    public void deleteFile() {

    }

    @Override
    public String makeThumbnail(java.io.File uploadFile) throws IOException {
        String thumbnailFilePath = uploadFile.getAbsolutePath().replace(".", "_thumbnail.");

        Thumbnails.of(uploadFile)
                .sourceRegion(Positions.CENTER, 400, 300)
                .size(400, 300)
                .toFile(thumbnailFilePath);

        return thumbnailFilePath;
    }
}
