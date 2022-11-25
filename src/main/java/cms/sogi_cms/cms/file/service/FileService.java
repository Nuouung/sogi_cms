package cms.sogi_cms.cms.file.service;

import cms.sogi_cms.cms.file.dto.FileResponseDto;
import cms.sogi_cms.cms.file.dto.FileSearch;
import cms.sogi_cms.cms.support.pagination.Paging;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileService {

    // c
    Long saveFile(MultipartFile multipartFile) throws IOException;

    // r
    Paging<FileResponseDto> getFileList(FileSearch fileSearch);

    // u


    // d
    void deleteFile(Long id) throws FileNotFoundException;

    // etc
    String makeThumbnail(java.io.File uploadFile) throws IOException;
    void addOneDownloadCount(Long id) throws IOException;
}
