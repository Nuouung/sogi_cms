package cms.sogi_cms.cms.user.web;

import cms.sogi_cms.cms.user.dto.UserCreateUpdateDto;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

@Component
public class UserCreateValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCreateUpdateDto userDto = (UserCreateUpdateDto) target;
        BindingResult bindingResult = (BindingResult) errors;

        if (StringUtils.hasText(userDto.getPassword()) && StringUtils.hasText(userDto.getPasswordCheck())) {
            if (!userDto.getPassword().equals(userDto.getPasswordCheck())) {
                bindingResult.addError(new FieldError("userCreateUpdateDto", "password", "비밀번호의 값이 일치하지 않습니다."));
            }
        }
    }
}
