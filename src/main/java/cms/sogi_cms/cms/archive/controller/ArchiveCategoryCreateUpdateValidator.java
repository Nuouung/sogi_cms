package cms.sogi_cms.cms.archive.controller;

import cms.sogi_cms.cms.archive.dto.ArchiveCategoryCreateUpdateDto;
import cms.sogi_cms.cms.archive.dto.ArchiveCategoryResponseDto;
import cms.sogi_cms.cms.archive.service.ArchiveCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ArchiveCategoryCreateUpdateValidator implements Validator {

    private final ArchiveCategoryService archiveCategoryService;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ArchiveCategoryCreateUpdateDto archiveCategoryDto = (ArchiveCategoryCreateUpdateDto) target;
        BindingResult bindingResult = (BindingResult) errors;

        if (!StringUtils.hasText(archiveCategoryDto.getCategoryName())) {
            bindingResult.addError(new FieldError("archiveCategoryCreateUpdateDto", "categoryName", "카테고리 아이디 값을 입력해 주십시오."));
        }

        // 중복 체크
        List<ArchiveCategoryResponseDto> archiveCategories = archiveCategoryService.getAllArchiveCategory();
        for (ArchiveCategoryResponseDto archiveCategory : archiveCategories) {
            if (archiveCategory.getCategoryName().equals(archiveCategoryDto.getCategoryName())) {
                bindingResult.addError(new FieldError("archiveCategoryCreateUpdateDto", "categoryName", "중복되는 카테고리 아이디 값이 존재합니다."));
            }
        }

        // 영어만 가능
        if (!archiveCategoryDto.getCategoryName().matches("^[a-zA-Z_\\-]*$")) {
            bindingResult.addError(new FieldError("archiveCategoryCreateUpdateDto", "categoryName", "카테고리 아이디 값은 영어만 입력할 수 있습니다."));
        }

        if (!StringUtils.hasText(archiveCategoryDto.getCategoryKoreanName())) {
            bindingResult.addError(new FieldError("archiveCategoryCreateUpdateDto", "categoryKoreanName", "카테고리 이름을 입력해 주십시오."));
        }
    }
}
