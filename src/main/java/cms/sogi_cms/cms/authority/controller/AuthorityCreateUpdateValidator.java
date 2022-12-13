package cms.sogi_cms.cms.authority.controller;

import cms.sogi_cms.cms.authority.dto.AuthorityCreateUpdateDto;
import cms.sogi_cms.cms.user.dto.UserCreateUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class AuthorityCreateUpdateValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        AuthorityCreateUpdateDto authorityDto = (AuthorityCreateUpdateDto) target;
        BindingResult bindingResult = (BindingResult) errors;

        // TODO 알파벳 검증 필요 (authorityName은 영어만 올 수 있음)

        if (!StringUtils.hasText(authorityDto.getAuthorityName())) {
            bindingResult.addError(new FieldError("authorityCreateUpdateDto", "authorityName", "권한 아이디 값을 입력해 주십시오."));
        }

        if (!StringUtils.hasText(authorityDto.getAuthorityKoreanName())) {
            bindingResult.addError(new FieldError("authorityCreateUpdateDto", "authorityKoreanName", "권한 이름을 입력해 주십시오."));
        }

        if (!StringUtils.hasText(authorityDto.getDescription())) {
            bindingResult.addError(new FieldError("authorityCreateUpdateDto", "description", "권한 설명을 입력해 주십시오."));
        }

        if (authorityDto.getHttpMethod() == null) {
            bindingResult.addError(new FieldError("authorityCreateUpdateDto", "httpMethod", "권한 http 메소드를 선택해 주십시오."));
        }

        if (!StringUtils.hasText(authorityDto.getUrlPath())) {
            bindingResult.addError(new FieldError("authorityCreateUpdateDto", "urlPath", "권한 값을 입력해 주십시오."));
        }
    }
}
