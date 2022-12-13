package cms.sogi_cms.cms.role.dto;

import cms.sogi_cms.cms.authority.entity.Authority;
import cms.sogi_cms.cms.role.entity.Role;
import cms.sogi_cms.cms.support.SogiConstant;
import lombok.*;

import javax.persistence.*;

@Data
public class RoleAuthorityResponseDto {

    private Long id;

    private Role role;
    private Authority authority;

}
