package cms.sogi_cms.cms.role.dto;

import cms.sogi_cms.cms.role.entity.RoleAuthority;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoleResponseDto {

    private Long id;

    private String roleName;
    private String koreanName;

    private String description;

    private List<String> authorityNameList = new ArrayList<>();
    private List<RoleAuthority> roleAuthorityList = new ArrayList<>();

    private boolean isAdmin; // 최고 관리자 역할인지
    private boolean isDefaultUser; // 기본 회원인지
}
