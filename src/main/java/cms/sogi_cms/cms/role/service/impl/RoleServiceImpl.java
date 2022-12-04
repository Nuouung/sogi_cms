package cms.sogi_cms.cms.role.service.impl;

import cms.sogi_cms.cms.authority.entity.Authority;
import cms.sogi_cms.cms.authority.repository.AuthorityRepository;
import cms.sogi_cms.cms.authority.service.AuthorityService;
import cms.sogi_cms.cms.role.dto.RoleCreateUpdateDto;
import cms.sogi_cms.cms.role.dto.RoleSearch;
import cms.sogi_cms.cms.role.entity.Role;
import cms.sogi_cms.cms.role.entity.RoleAuthority;
import cms.sogi_cms.cms.role.repository.RoleAuthorityRepository;
import cms.sogi_cms.cms.role.repository.RoleRepository;
import cms.sogi_cms.cms.role.service.RoleService;
import cms.sogi_cms.cms.support.pagination.Paging;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final AuthorityService authorityService;

    private final RoleAuthorityRepository roleAuthorityRepository;

    @Override
    public Long saveRole(RoleCreateUpdateDto roleDto) {
        // 역할 생성
        Role role = Role.create(roleDto);

        // 역할에 연결되어 있는 권한들 추출
        List<RoleAuthority> roleAuthorityList = getRoleAuthorityList(roleDto, role);

        // 역할과 역할, 권한 매핑 정보를 디비에 저장
        roleRepository.save(role);
        roleAuthorityRepository.saveAll(roleAuthorityList);

        return role.getId();
    }

    private List<RoleAuthority> getRoleAuthorityList(RoleCreateUpdateDto roleDto, Role role) {
        List<RoleAuthority> roleAuthorityList = new ArrayList<>();
        for (String authorityName : roleDto.getAuthorityNameList()) {
            Authority authority = authorityService.getAuthorityByAuthorityName(authorityName);
            roleAuthorityList.add(RoleAuthority.create(role, authority));
        }

        return roleAuthorityList;
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("역할을 조회할 수 없습니다."));
    }

    @Override
    public Role getRoleByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("역할을 조회할 수 없습니다."));
    }

    @Override
    public Role getDefaultUserRole() {
        return roleRepository.getDefaultUserRole();
    }

    @Override
    public Paging<Role> getRoleList(RoleSearch roleSearch) {
        return null;
    }

    @Override
    public Long getTotalNumber(RoleSearch roleSearch) {
        return null;
    }

    @Override
    public void updateRole(Long id, RoleCreateUpdateDto roleDto) {
        // 역할 변경
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("역할 정보를 찾을 수 없습니다."));

        role.update(roleDto);

        // 역할에 연결되어 있는 기존 역할, 권한 매핑 정보 삭제 -> 새로 생성
        roleAuthorityRepository.deleteAll(role.getRoleAuthorityList());
        List<RoleAuthority> roleAuthorityList = getRoleAuthorityList(roleDto, role);

        //  역할, 권한 매핑 정보를 디비에 저장 (role은 엔티티 매니저에 의해 관리되는 상태이므로 새로 작업할 필요 없음)
        roleAuthorityRepository.saveAll(roleAuthorityList);
    }

    @Override
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("역할 정보를 찾을 수 없습니다."));

        // 매핑된 역할과 권한 관계 정보들을 먼저 삭제한다.
        roleAuthorityRepository.deleteAll(role.getRoleAuthorityList());

        // 역할을 삭제한다.
        roleRepository.delete(role);
    }
}
