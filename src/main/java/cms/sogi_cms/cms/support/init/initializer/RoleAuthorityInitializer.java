package cms.sogi_cms.cms.support.init.initializer;

import cms.sogi_cms.cms.authority.dto.AuthorityCreateUpdateDto;
import cms.sogi_cms.cms.authority.entity.HttpMethod;
import cms.sogi_cms.cms.authority.service.AuthorityService;
import cms.sogi_cms.cms.role.dto.RoleCreateUpdateDto;
import cms.sogi_cms.cms.role.service.RoleService;
import cms.sogi_cms.cms.support.SogiConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RoleAuthorityInitializer {

    private final RoleService roleService;
    private final AuthorityService authorityService;

    public void roleAuthorityDataInit() {
        log.info("[SOGICMS] 역할/권한 데이터 초기화 작업을 수행합니다. [대상 데이터 베이스 테이블 = SOGI_AUTHORITY / SOGI_ROLE / SOGI_ROLE_AUTHORITY_RELATION]");

        // 어드민 페이지 접근 권한
        AuthorityCreateUpdateDto authorityDto = new AuthorityCreateUpdateDto();
        authorityDto.setAuthorityName("admin-access");
        authorityDto.setAuthorityKoreanName("관리자단 접근 권한");
        authorityDto.setDescription("관리자 메인 페이지에 접근할 수 있는 권한");
        authorityDto.setHttpMethod(HttpMethod.GET);
        authorityDto.setUrlPath(SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/**");
        authorityDto.setPriority(100);
        authorityDto.setDefault(true);

        authorityService.saveAuthority(authorityDto);

        // ROLE_ADMIN
        RoleCreateUpdateDto roleDto = new RoleCreateUpdateDto();
        roleDto.setRoleName("ROLE_ADMIN");
        roleDto.setKoreanName("최고관리자");
        roleDto.setDescription("시스템 최고 관리자");
        roleDto.setAdmin(true);
        roleDto.setDefaultUser(false);
        roleDto.setAuthorityNameList(List.of("admin-access"));

        roleService.saveRole(roleDto);

        // ROLE_USER
        RoleCreateUpdateDto roleDto2 = new RoleCreateUpdateDto();
        roleDto2.setRoleName("ROLE_USER");
        roleDto2.setKoreanName("일반회원");
        roleDto2.setDescription("회원가입시 부여되는 일반회원 역할");
        roleDto2.setAdmin(false);
        roleDto2.setDefaultUser(true);

        roleService.saveRole(roleDto2);

        // ROLE_MANAGER
        RoleCreateUpdateDto roleDto3 = new RoleCreateUpdateDto();
        roleDto3.setRoleName("ROLE_MANAGER");
        roleDto3.setKoreanName("매니저");
        roleDto3.setDescription("매니저");
        roleDto3.setAdmin(false);
        roleDto3.setDefaultUser(false);
        roleDto3.setAuthorityNameList(List.of("admin-access"));

        roleService.saveRole(roleDto3);
    }
}
