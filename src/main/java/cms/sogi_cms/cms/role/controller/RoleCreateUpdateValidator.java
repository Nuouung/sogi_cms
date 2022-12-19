package cms.sogi_cms.cms.role.controller;

import cms.sogi_cms.cms.role.dto.RoleCreateUpdateDto;
import cms.sogi_cms.cms.role.entity.Role;
import cms.sogi_cms.cms.role.service.RoleService;
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
public class RoleCreateUpdateValidator implements Validator {

    private final RoleService roleService;

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

        // 중복체크
        List<Role> roles = roleService.getAllRoles();
        for (Role role : roles) {
            String roleNameUpperCase = roleDto.getRoleName().toUpperCase();

            if (role.getRoleName().equals(roleNameUpperCase)) {
                bindingResult.addError(new FieldError("roleCreateUpdateDto", "roleName", "중복된 역할 아이디 값이 존재합니다."));
            }

            // ROLE_을 안달고 서밋을 누른 회원을 위해 디비에서 가져온 값에서 ROLE_을 없애고 한번 더 검증을 진행한다.
            // 철저하게 아무도 들어올 수 없게
            String roleNameWithoutRoleSuffix = role.getRoleName().substring("ROLE_".length());
            if (roleNameWithoutRoleSuffix.equals(roleNameUpperCase)) {
                bindingResult.addError(new FieldError("roleCreateUpdateDto", "roleName", "중복된 역할 아이디 값이 존재합니다."));
            }
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
