package cms.sogi_cms.cms.archive.controller;

import cms.sogi_cms.cms.archive.dto.ArchiveCategoryCreateUpdateDto;
import cms.sogi_cms.cms.archive.dto.ArchiveCategoryResponseDto;
import cms.sogi_cms.cms.archive.dto.ArchiveCreateUpdateDto;
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
public class ArchiveCreateUpdateValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ArchiveCreateUpdateDto archiveDto = (ArchiveCreateUpdateDto) target;
        BindingResult bindingResult = (BindingResult) errors;

        if (!StringUtils.hasText(archiveDto.getTitle())) {
            bindingResult.addError(new FieldError("archiveCreateUpdateDto", "title", "제목을 입력해 주십시오."));
        }

        if (!StringUtils.hasText(archiveDto.getContentHtml())) {
            bindingResult.addError(new FieldError("archiveCreateUpdateDto", "contentHtml", "본문을 입력해 주십시오."));
        }

        if (!StringUtils.hasText(archiveDto.getContentSummary())) {
            bindingResult.addError(new FieldError("archiveCreateUpdateDto", "contentSummary", "본문 요약을 입력해 주십시오."));
        }
    }
}
