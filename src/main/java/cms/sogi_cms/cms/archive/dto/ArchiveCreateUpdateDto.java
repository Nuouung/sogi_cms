package cms.sogi_cms.cms.archive.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ArchiveCreateUpdateDto {

    private Long id;

    private String username;

    private String title;
    private String contentHtml;
    private String contentPlain;
    private String contentSummary;

    private Long archiveCategoryId;

    private List<MultipartFile> multipartFileList;

    private LocalDateTime createdDateTime;
    private LocalDateTime lastModifiedDateTime;

    private Integer hit; // 조회수
    private Integer recommend; // 추천수

    private boolean isPublish; // 게시 여부
    private boolean isSticky; // 고정글 여부

    private LocalDate stickyStartDate;
    private LocalDate stickyEndDate;
}
