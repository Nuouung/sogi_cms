package cms.sogi_cms.cms.role.service.impl;

import cms.sogi_cms.cms.authority.entity.Authority;
import cms.sogi_cms.cms.authority.repository.AuthorityRepository;
import cms.sogi_cms.cms.authority.service.AuthorityService;
import cms.sogi_cms.cms.role.dto.RoleAuthorityResponseDto;
import cms.sogi_cms.cms.role.dto.RoleCreateUpdateDto;
import cms.sogi_cms.cms.role.dto.RoleResponseDto;
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
import java.util.stream.Collectors;

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
    public List<Role> getRoleListContainCertainAuthority(String authorityName) {
        return roleRepository.getRoleListByAuthorityName(authorityName);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Paging<RoleResponseDto> getRoleList(RoleSearch roleSearch) {
        List<RoleResponseDto> contents = roleRepository.findList(roleSearch).stream()
                .map(this::toRoleResponseDto)
                .collect(Collectors.toList());
        long total = roleRepository.count();

        return new Paging<>(contents, total, roleSearch);
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

    @Override
    public RoleResponseDto toRoleResponseDto(Role role) {
        RoleResponseDto dto = new RoleResponseDto();

        dto.setId(role.getId());
        dto.setRoleName(role.getRoleName());
        dto.setKoreanName(role.getKoreanName());
        dto.setDescription(role.getDescription());

        List<String> authorityNameList = new ArrayList<>();
        for (RoleAuthority roleAuthority : role.getRoleAuthorityList()) {
            authorityNameList.add(roleAuthority.getAuthority().getAuthorityName());
        }

        dto.setAuthorityNameList(authorityNameList);
        dto.setRoleAuthorityList(getRoleAuthorityResponseDtoList(role));
        dto.setAdmin(role.isAdmin());
        dto.setDefaultUser(role.isDefaultUser());
        dto.setRegisterDate(role.getRegisterDate());

        return dto;
    }

    @Override
    public List<RoleAuthorityResponseDto> getRoleAuthorityResponseDtoList(Role role) {
        List<RoleAuthorityResponseDto> list = new ArrayList<>();

        for (RoleAuthority roleAuthority : role.getRoleAuthorityList()) {
            RoleAuthorityResponseDto roleAuthorityResponseDto = new RoleAuthorityResponseDto();

            roleAuthorityResponseDto.setId(roleAuthority.getId());
            roleAuthorityResponseDto.setRole(role);
            roleAuthorityResponseDto.setAuthority(roleAuthority.getAuthority());

            list.add(roleAuthorityResponseDto);
        }

        return list;
    }
}
