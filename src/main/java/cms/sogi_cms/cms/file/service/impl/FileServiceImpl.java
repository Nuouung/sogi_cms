package cms.sogi_cms.cms.file.service.impl;

import cms.sogi_cms.cms.file.entity.File;
import cms.sogi_cms.cms.file.service.FileService;
import cms.sogi_cms.cms.support.SogiConstant;
import cms.sogi_cms.cms.support.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MimeType;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {

    private final String UPLOAD_WHITE_LIST = "upload_white_list";

    @Override
    public Long saveFile(MultipartFile multipartFile) throws IOException {

        StringBuilder uploadErrorMessage = new StringBuilder();
        Boolean isUploadSuccess = uploadFileValidation(multipartFile, uploadErrorMessage);

        String uploadDirectory = getUploadDirectory();
        UUID hashedFilename = UUID.randomUUID();

        // 파일 생성 후 저장
        java.io.File uploadFile = new java.io.File(uploadDirectory + hashedFilename);
        FileCopyUtils.copy(
                multipartFile.getInputStream(),
                new FileOutputStream(uploadFile));
        log.debug("[SOGICMS] 파일이 저장되었습니다.");

        File.builder()
                .fileOriginalName(multipartFile.getOriginalFilename())
                .fileExtension(FileUtils.getExtension(multipartFile.getOriginalFilename()))
                .fileMimeType(URLConnection.guessContentTypeFromName(multipartFile.getOriginalFilename()))
                .build();
        return null;
    }

    private String getUploadDirectory() {
        StringBuilder uploadDirectory = new StringBuilder();
        YearMonth yearMonth = YearMonth.from(LocalDate.now());

        uploadDirectory
                .append(SogiConstant.UPLOAD_PATH).append(FileUtils.getSystemSeparator())
                .append(SogiConstant.SITE_PATH).append(FileUtils.getSystemSeparator())
                .append(yearMonth.getYear()).append(FileUtils.getSystemSeparator())
                .append(yearMonth.getMonth()).append(FileUtils.getSystemSeparator());

        log.debug("[SOGICMS] 파일 업로드 경로: {}", uploadDirectory);

        return uploadDirectory.toString();
    }

    private boolean uploadFileValidation(MultipartFile multipartFile, StringBuilder uploadErrorMessage) {
        // TODO 설정 db 만들어야 한다.
        List<String> uploadWhiteList = List.of(new String[]{"jpg"});
        Long uploadSizeLimit = 100L;

        // 지원하지 않는 업로드 파일인 경우, 파일을 저장하지 않고 에러 정보를 담는다.
        if (!FileUtils.isUploadPossible(multipartFile.getOriginalFilename(), uploadWhiteList)) {
            uploadErrorMessage.append("시스템이 지원하지 않는 확장자를 가진 파일입니다.");
            return false;
        }

        // 업로드하고자 하는 파일이 시스템이 허용하는 최대 크기보다 큰 경우, 마찬가지로 파일을 저장하지 않고 에러 정보를 담는다.
        if (multipartFile.getSize() > uploadSizeLimit) {
            uploadErrorMessage.append("파일의 크기가 시스템의 허용 범위보다 큽니다.");
            return false;
        }

        return true;
    }

    @Override
    public void deleteFile() {

    }

    @Override
    public void makeThumbnail() {

    }
}
