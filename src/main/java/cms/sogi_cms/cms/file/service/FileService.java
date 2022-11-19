package cms.sogi_cms.cms.file.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileService {

    // c
    Long saveFile(MultipartFile multipartFile) throws IOException;

    // r

    // u


    // d
    void deleteFile(Long id) throws FileNotFoundException;

    // etc
    String makeThumbnail(java.io.File uploadFile) throws IOException;
    void addOneDownloadCount(Long id) throws IOException;
}
