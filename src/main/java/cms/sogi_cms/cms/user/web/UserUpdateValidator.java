package cms.sogi_cms.cms.user.web;

import cms.sogi_cms.cms.support.utils.FileUtils;
import cms.sogi_cms.cms.user.dto.UserCreateUpdateDto;
import cms.sogi_cms.cms.user.entity.User;
import cms.sogi_cms.cms.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserUpdateValidator implements Validator {

    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCreateUpdateDto userDto = (UserCreateUpdateDto) target;
        BindingResult bindingResult = (BindingResult) errors;

        User user = userService.getUserById(userDto.getId())
                .orElseThrow(() -> new UsernameNotFoundException("회원을 조회할 수 없습니다"));

        if (!user.getUsername().equals(userDto.getUsername())) {
            bindingResult.addError(new FieldError("userCreateUpdateDto", "username", "아이디는 다를 수 없습니다."));
        }

        // 5MB를 넘거나 JPG, PNG가 아니면
        if (!userDto.getProfilePicture().isEmpty()) {
            if (!isProperProfilePicture(userDto)) {
                bindingResult.addError(new FieldError("userCreateUpdateDto", "profilePicture", "파일의 확장자 혹은 용량을 재검토해 주십시오."));
            }
        }
    }

    private boolean isProperProfilePicture(UserCreateUpdateDto userDto) {
        return userDto.getProfilePicture().getSize() < 5242880 &&
                (
                        "jpg".equals(FileUtils.getExtension(userDto.getProfilePicture().getOriginalFilename())) ||
                        "png".equals(FileUtils.getExtension(userDto.getProfilePicture().getOriginalFilename()))
                );
    }
}
