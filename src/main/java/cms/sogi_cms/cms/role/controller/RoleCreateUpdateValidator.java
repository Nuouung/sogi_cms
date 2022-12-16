package cms.sogi_cms.cms.role.controller;

import cms.sogi_cms.cms.role.dto.RoleCreateUpdateDto;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

@Component
public class RoleCreateUpdateValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        RoleCreateUpdateDto roleDto = (RoleCreateUpdateDto) target;
        BindingResult bindingResult = (BindingResult) errors;

        if (!StringUtils.hasText(roleDto.getRoleName())) {
            bindingResult.addError(new FieldError("roleCreateUpdateDto", "roleName", "역할 아이디 값을 입력해 주세요."));
        }

        // 영어만 가능
        if (!roleDto.getRoleName().matches("^[a-zA-Z_\\-]*$")) {
            bindingResult.addError(new FieldError("roleCreateUpdateDto", "roleName", "역할 아이디 값을 영어만 입력할 수 있습니다."));
        }

        if (!StringUtils.hasText(roleDto.getKoreanName())) {
            bindingResult.addError(new FieldError("roleCreateUpdateDto", "koreanName", "역할 이름을 입력해 주세요."));
        }

        if (!StringUtils.hasText(roleDto.getDescription())) {
            bindingResult.addError(new FieldError("roleCreateUpdateDto", "description", "역할 설명을 입력해 주세요."));
        }
    }
}
