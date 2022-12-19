package cms.sogi_cms.cms.authority.controller;

import cms.sogi_cms.cms.authority.dto.AuthorityCreateUpdateDto;
import cms.sogi_cms.cms.authority.dto.AuthorityResponseDto;
import cms.sogi_cms.cms.authority.service.AuthorityService;
import cms.sogi_cms.cms.user.dto.UserCreateUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthorityCreateUpdateValidator implements Validator {

    private final AuthorityService authorityService;

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        AuthorityCreateUpdateDto authorityDto = (AuthorityCreateUpdateDto) target;
        BindingResult bindingResult = (BindingResult) errors;

        if (!StringUtils.hasText(authorityDto.getAuthorityName())) {
            bindingResult.addError(new FieldError("authorityCreateUpdateDto", "authorityName", "권한 아이디 값을 입력해 주십시오."));
        }

        // 중복체크
        List<AuthorityResponseDto> authorities = authorityService.getAllAuthorityList();
        for (AuthorityResponseDto authority : authorities) {
            if (authority.getAuthorityName().equals(authorityDto.getAuthorityName())) {
                bindingResult.addError(new FieldError("authorityCreateUpdateDto", "authorityName", "중복된 권한 아이디 값이 존재합니다."));
            }
        }

        // 영어만 가능
        if (!authorityDto.getAuthorityName().matches("^[a-zA-Z_\\-]*$")) {
            bindingResult.addError(new FieldError("authorityCreateUpdateDto", "authorityName", "권한 아이디 값을 영어만 입력할 수 있습니다."));
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
