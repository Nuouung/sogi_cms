package cms.sogi_cms.cms.archive.service.impl;

import cms.sogi_cms.cms.archive.dto.ArchiveCategoryCreateUpdateDto;
import cms.sogi_cms.cms.archive.dto.ArchiveCategoryResponseDto;
import cms.sogi_cms.cms.archive.dto.ArchiveCategorySearch;
import cms.sogi_cms.cms.archive.entity.ArchiveCategory;
import cms.sogi_cms.cms.archive.repository.ArchiveCategoryRepository;
import cms.sogi_cms.cms.archive.service.ArchiveCategoryService;
import cms.sogi_cms.cms.support.pagination.Paging;
import cms.sogi_cms.cms.support.pagination.SortDirection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArchiveCategoryServiceImpl implements ArchiveCategoryService {

    private final ArchiveCategoryRepository archiveCategoryRepository;

    @Override
    public Long saveArchiveCategory(ArchiveCategoryCreateUpdateDto archiveCategoryDto) {

        initializeArchiveCategorySkins(archiveCategoryDto);
        initializePageSizeAndSortDirection(archiveCategoryDto);

        ArchiveCategory archiveCategory = ArchiveCategory.create(archiveCategoryDto);

        archiveCategoryRepository.save(archiveCategory);

        return archiveCategory.getId();
    }

    private void initializePageSizeAndSortDirection(ArchiveCategoryCreateUpdateDto archiveCategoryDto) {
        if (archiveCategoryDto.getPageSize() == null) {
            archiveCategoryDto.setPageSize(10);
        }

        if (archiveCategoryDto.getSortDirection() == null) {
            archiveCategoryDto.setSortDirection(SortDirection.DESC);
        }
    }

    private void initializeArchiveCategorySkins(ArchiveCategoryCreateUpdateDto archiveCategoryDto) {
        if (!StringUtils.hasText(archiveCategoryDto.getAdminFormSkin())) {
            archiveCategoryDto.setAdminFormSkin("form.html");
        }

        if (!StringUtils.hasText(archiveCategoryDto.getAdminListSkin())) {
            archiveCategoryDto.setAdminListSkin("list.html");
        }

        if (!StringUtils.hasText(archiveCategoryDto.getUserListSkin())) {
            archiveCategoryDto.setUserListSkin("list.html");
        }

        if (!StringUtils.hasText(archiveCategoryDto.getUserDetailSkin())) {
            archiveCategoryDto.setUserDetailSkin("detail.html");
        }
    }

    @Override
    public ArchiveCategory getArchiveCategoryById(Long id) {
        return archiveCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("아카이브 카테고리를 찾을 수 없습니다."));
    }

    @Override
    public ArchiveCategory getArchiveCategoryByCategoryName(String categoryName) {
        return archiveCategoryRepository.getArchiveCategoryByCategoryName(categoryName)
                .orElseThrow(() -> new EntityNotFoundException("아카이브 카테고리를 찾을 수 없습니다."));
    }

    @Override
    public List<ArchiveCategoryResponseDto> getAllArchiveCategory() {
        return archiveCategoryRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public Paging<ArchiveCategoryResponseDto> getArchiveCategoryList(ArchiveCategorySearch archiveCategorySearch) {
        List<ArchiveCategoryResponseDto> contents = archiveCategoryRepository.findList(archiveCategorySearch).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
        Long total = archiveCategoryRepository.count(archiveCategorySearch);

        return new Paging<>(contents, total, archiveCategorySearch);
    }

    @Override
    public void updateArchiveCategory(Long id, ArchiveCategoryCreateUpdateDto archiveCategoryDto) {
        ArchiveCategory archiveCategory = archiveCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("아카이브 카테고리를 찾을 수 없습니다."));

        archiveCategory.update(archiveCategoryDto);
    }

    @Override
    public void deleteArchiveCategory(Long id) {
        ArchiveCategory archiveCategory = archiveCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("아카이브 카테고리를 찾을 수 없습니다."));

        archiveCategoryRepository.delete(archiveCategory);
    }

    @Override
    public ArchiveCategoryResponseDto toResponseDto(ArchiveCategory archiveCategory) {
        ArchiveCategoryResponseDto dto = new ArchiveCategoryResponseDto();

        dto.setId(archiveCategory.getId());
        dto.setCategoryName(archiveCategory.getCategoryName());
        dto.setCategoryKoreanName(archiveCategory.getCategoryKoreanName());
        dto.setAdminFormSkin(archiveCategory.getAdminFormSkin());
        dto.setAdminListSkin(archiveCategory.getAdminListSkin());
        dto.setUserListSkin(archiveCategory.getUserListSkin());
        dto.setUserDetailSkin(archiveCategory.getUserDetailSkin());
        dto.setPageSize(archiveCategory.getPageSize());
        dto.setSortDirection(archiveCategory.getSortDirection());

        return dto;
    }
}
