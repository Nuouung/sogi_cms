package cms.sogi_cms.cms.archive.service;

import cms.sogi_cms.cms.archive.dto.ArchiveCategoryCreateUpdateDto;
import cms.sogi_cms.cms.archive.dto.ArchiveCategoryResponseDto;
import cms.sogi_cms.cms.archive.dto.ArchiveCategorySearch;
import cms.sogi_cms.cms.archive.entity.ArchiveCategory;
import cms.sogi_cms.cms.support.pagination.Paging;

import java.util.List;

public interface ArchiveCategoryService {

    // c
    Long saveArchiveCategory(ArchiveCategoryCreateUpdateDto archiveCategoryDto);

    // r
    ArchiveCategory getArchiveCategoryById(Long id);

    ArchiveCategory getArchiveCategoryByCategoryName(String categoryName);

    List<ArchiveCategoryResponseDto> getAllArchiveCategory();

    Paging<ArchiveCategoryResponseDto> getArchiveCategoryList(ArchiveCategorySearch archiveCategorySearch);

    // u
    void updateArchiveCategory(Long id, ArchiveCategoryCreateUpdateDto archiveCategoryDto);

    // d
    void deleteArchiveCategory(Long id);

    // utils
    ArchiveCategoryResponseDto toResponseDto(ArchiveCategory archiveCategory);
}
