package cms.sogi_cms.cms.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResultDto {
    private boolean isSuccess;
    private String loginUsername;
}
