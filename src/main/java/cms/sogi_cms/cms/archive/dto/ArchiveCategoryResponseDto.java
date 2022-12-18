package cms.sogi_cms.cms.archive.dto;

import cms.sogi_cms.cms.support.pagination.SortDirection;
import lombok.Data;

@Data
public class ArchiveCategoryResponseDto {

    private Long id;

    private String categoryName;
    private String categoryKoreanName;

    private String adminFormSkin; // 게시 할 때 내보내는 페이지 스킨 (관리자) - default : form.html (admin 경로)
    private String adminListSkin; // 관리자단 리스트 페이지 스킨 - default : list.html (admin 경로)
    private String userListSkin; // 사용자단 리스트 페이지 스킨 - default : list.html (user 경로)
    private String userDetailSkin; // 사용자단 상세 페이지 스킨 - default : detail.html

    private Integer pageSize;
    private SortDirection sortDirection;
}
