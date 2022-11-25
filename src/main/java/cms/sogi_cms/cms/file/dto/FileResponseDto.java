package cms.sogi_cms.cms.file.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FileResponseDto {

    private String username;

    private String filePath;
    private String fileOriginalName;
    private String fileHashedName;

    private String fileExtension;
    private String fileMimeType;

    private Long fileSize;

    private LocalDateTime registeredDateTime;

    private Long downloadCount;
}
