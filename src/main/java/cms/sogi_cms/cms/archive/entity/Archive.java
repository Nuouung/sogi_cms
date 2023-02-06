package cms.sogi_cms.cms.archive.entity;

import cms.sogi_cms.cms.archive.dto.ArchiveCreateUpdateDto;
import cms.sogi_cms.cms.file.entity.File;
import cms.sogi_cms.cms.support.SogiConstant;
import cms.sogi_cms.cms.user.entity.User;
import lombok.*;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = SogiConstant.MAIN_SITE_PREFIX + "_ARCHIVE")
public class Archive {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    private String title;
    @Column(columnDefinition = "LONGTEXT")
    private String contentHtml;
    @Column(columnDefinition = "LONGTEXT")
    private String contentPlain;
    @Column(columnDefinition = "LONGTEXT")
    private String contentSummary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARCHIVE_CATEGORY_ID")
    private ArchiveCategory archiveCategory;

    @OneToMany(mappedBy = "archive")
    private List<ArchiveFileRelation> archiveFileRelations = new ArrayList<>();

    private LocalDateTime createdDateTime;
    private LocalDateTime lastModifiedDateTime;

    private Integer hit; // 조회수
    private Integer recommend; // 추천수

    private boolean isPublish; // 게시 여부
    private boolean isSticky; // 고정글 여부

    private LocalDate stickyStartDate;
    private LocalDate stickyEndDate;

    public static Archive create(ArchiveCreateUpdateDto dto, User user, ArchiveCategory archiveCategory) {
        return Archive.builder()
                .user(user)
                .title(dto.getTitle())
                .contentHtml(dto.getContentHtml())
                .contentPlain(dto.getContentPlain())
                .contentSummary(dto.getContentSummary())
                .archiveCategory(archiveCategory)
                .createdDateTime(LocalDateTime.now())
                .lastModifiedDateTime(LocalDateTime.now())
                .hit(0)
                .recommend(0)
                .isPublish(dto.getIsPublish() != null && dto.getIsPublish())
                .isSticky(dto.getIsSticky() != null && dto.getIsSticky())
                .stickyStartDate(StringUtils.hasText(dto.getStickyStartDate()) ? LocalDate.parse(dto.getStickyStartDate()) : null)
                .stickyEndDate(StringUtils.hasText(dto.getStickyEndDate()) ? LocalDate.parse(dto.getStickyEndDate()) : null)
                .build();
    }
}