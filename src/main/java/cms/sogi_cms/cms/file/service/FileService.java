package cms.sogi_cms.cms.file.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    // c
    Long saveFile(MultipartFile multipartFile) throws IOException;

    // r

    // u


    // d
    void deleteFile();

    void makeThumbnail();
}
