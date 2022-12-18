package cms.sogi_cms.cms.archive.entity;

import cms.sogi_cms.cms.archive.dto.ArchiveCategoryCreateUpdateDto;
import cms.sogi_cms.cms.support.SogiConstant;
import cms.sogi_cms.cms.support.pagination.SortDirection;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = SogiConstant.MAIN_SITE_PREFIX + "_ARCHIVE_CATEGORY")
public class ArchiveCategory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryName;
    private String categoryKoreanName;

    private String adminFormSkin; // 게시 할 때 내보내는 페이지 스킨 (관리자) - default : form.html (admin 경로)
    private String adminListSkin; // 관리자단 리스트 페이지 스킨 - default : list.html (admin 경로)
    private String userListSkin; // 사용자단 리스트 페이지 스킨 - default : list.html (user 경로)
    private String userDetailSkin; // 사용자단 상세 페이지 스킨 - default : detail.html

    private Integer pageSize;
    private SortDirection sortDirection;

    public static ArchiveCategory create(ArchiveCategoryCreateUpdateDto dto) {
        return ArchiveCategory.builder()
                .categoryName(dto.getCategoryName())
                .categoryKoreanName(dto.getCategoryKoreanName())
                .adminFormSkin(dto.getAdminFormSkin())
                .adminListSkin(dto.getAdminListSkin())
                .userListSkin(dto.getUserListSkin())
                .userDetailSkin(dto.getUserDetailSkin())
                .pageSize(dto.getPageSize())
                .sortDirection(dto.getSortDirection())
                .build();
    }

    public void update(ArchiveCategoryCreateUpdateDto archiveCategoryDto) {
//        this.categoryName = archiveCategoryDto.getCategoryName(); 카테고리 이름 (실질적인 아이디값은 변경할 수 없다)
        this.categoryKoreanName = archiveCategoryDto.getCategoryKoreanName();
        this.adminFormSkin = archiveCategoryDto.getAdminFormSkin();
        this.adminListSkin = archiveCategoryDto.getAdminListSkin();
        this.userListSkin = archiveCategoryDto.getUserListSkin();
        this.userDetailSkin = archiveCategoryDto.getUserDetailSkin();
        this.pageSize = archiveCategoryDto.getPageSize();
        this.sortDirection = archiveCategoryDto.getSortDirection();
    }
}
