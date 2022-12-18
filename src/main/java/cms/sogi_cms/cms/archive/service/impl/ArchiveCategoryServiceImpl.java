package cms.sogi_cms.cms.archive.service.impl;

import cms.sogi_cms.cms.archive.dto.ArchiveCategoryCreateUpdateDto;
import cms.sogi_cms.cms.archive.dto.ArchiveCategoryResponseDto;
import cms.sogi_cms.cms.archive.entity.ArchiveCategory;
import cms.sogi_cms.cms.archive.repository.ArchiveCategoryRepository;
import cms.sogi_cms.cms.archive.service.ArchiveCategoryService;
import cms.sogi_cms.cms.support.pagination.SortDirection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        if (archiveCategoryDto.getAdminFormSkin() == null) {
            archiveCategoryDto.setAdminFormSkin("form.html");
        }

        if (archiveCategoryDto.getAdminListSkin() == null) {
            archiveCategoryDto.setAdminListSkin("list.html");
        }

        if (archiveCategoryDto.getUserListSkin() == null) {
            archiveCategoryDto.setUserListSkin("list.html");
        }

        if (archiveCategoryDto.getUserDetailSkin() == null) {
            archiveCategoryDto.setUserDetailSkin("detail.html");
        }
    }

    @Override
    public ArchiveCategory getArchiveCategoryById(Long id) {
        return archiveCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("아카이브 카테고리를 찾을 수 없습니다."));
    }

    @Override
    public List<ArchiveCategoryResponseDto> getAllArchiveCategory() {
        return archiveCategoryRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
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
