package cms.sogi_cms.cms.archive.dto;

import cms.sogi_cms.cms.archive.entity.ArchiveCategory;
import cms.sogi_cms.cms.archive.entity.ArchiveFileRelation;
import cms.sogi_cms.cms.user.entity.User;
import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ArchiveResponseDto {

    private Long id;

    private String username;

    private String title;
    private String contentHtml;
    private String contentPlain;
    private String contentSummary;

    private String archiveCategoryName;

    private List<String> filePathList = new ArrayList<>();

    private LocalDateTime createdDateTime;
    private LocalDateTime lastModifiedDateTime;

    private Integer hit; // 조회수
    private Integer recommend; // 추천수

    private boolean isPublish; // 게시 여부
    private boolean isSticky; // 고정글 여부

    private LocalDate stickyStartDate;
    private LocalDate stickyEndDate;
}
