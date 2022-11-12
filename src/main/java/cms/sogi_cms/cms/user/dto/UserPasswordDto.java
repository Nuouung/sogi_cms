package cms.sogi_cms.cms.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserPasswordDto {
    @NotBlank
    private String password;
    @NotBlank
    private String passwordCheck;
}
