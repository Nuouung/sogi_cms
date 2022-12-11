package cms.sogi_cms.cms.user.web;

import cms.sogi_cms.cms.user.dto.UserCreateUpdateDto;
import cms.sogi_cms.cms.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserCreateValidator implements Validator {

    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCreateUpdateDto userDto = (UserCreateUpdateDto) target;
        BindingResult bindingResult = (BindingResult) errors;

        if (userService.getUserByUsername(userDto.getUsername()).isPresent()) {
            bindingResult.addError(new FieldError("userCreateUpdateDto", "username", "이미 존재하는 아이디입니다."));
        }

        if (StringUtils.hasText(userDto.getPassword()) && StringUtils.hasText(userDto.getPasswordCheck())) {
            if (!userDto.getPassword().equals(userDto.getPasswordCheck())) {
                bindingResult.addError(new FieldError("userCreateUpdateDto", "password", "비밀번호의 값이 일치하지 않습니다."));
            }
        }

//        // 5MB를 넘거나
//        userDto.getProfilePicture().getOriginalFilename()
//        if (userDto.getProfilePicture().getSize() > 5242880 ||)
    }
}
