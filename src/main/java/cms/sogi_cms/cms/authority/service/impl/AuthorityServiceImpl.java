package cms.sogi_cms.cms.authority.service.impl;

import cms.sogi_cms.cms.authority.dto.AuthorityCreateUpdateDto;
import cms.sogi_cms.cms.authority.dto.AuthorityResponseDto;
import cms.sogi_cms.cms.authority.dto.AuthoritySearch;
import cms.sogi_cms.cms.authority.entity.Authority;
import cms.sogi_cms.cms.authority.repository.AuthorityRepository;
import cms.sogi_cms.cms.authority.service.AuthorityService;
import cms.sogi_cms.cms.role.entity.Role;
import cms.sogi_cms.cms.role.entity.RoleAuthority;
import cms.sogi_cms.cms.role.repository.RoleAuthorityRepository;
import cms.sogi_cms.cms.support.SogiConstant;
import cms.sogi_cms.cms.support.pagination.Paging;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;
    private final RoleAuthorityRepository roleAuthorityRepository;

    @Override
    public Long saveAuthority(AuthorityCreateUpdateDto authorityDto) {
        // priority가 null이면 100으로 초기화
        if (authorityDto.getPriority() == null) {
            authorityDto.setPriority(100);
        }

        // authorityName을 소문자로 치환
        authorityDto.setAuthorityName(authorityDto.getAuthorityName().toLowerCase());

       Authority authority = Authority.create(authorityDto);
        authorityRepository.save(authority);

        return authority.getId();
    }

    @Override
    public Authority getAuthorityById(Long id) {
        return authorityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("권한 정보를 조회할 수 없습니다."));
    }

    @Override
    public Authority getAuthorityByAuthorityName(String authorityName) {
        return authorityRepository.findByAuthorityName(authorityName)
                .orElseThrow(() -> new EntityNotFoundException("권한 정보를 조회할 수 없습니다."));
    }

    @Override
    public Paging<AuthorityResponseDto> getAuthorityList(AuthoritySearch authoritySearch) {
        List<AuthorityResponseDto> contents = authorityRepository.findList(authoritySearch).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
        long total = authorityRepository.count();

        return new Paging<>(contents, total, authoritySearch);
    }

    @Override
    public Long getTotalNumber(AuthoritySearch authoritySearch) {
        return authorityRepository.count(authoritySearch);
    }

    @Override
    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceMap() {
        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resourceMap = new LinkedHashMap<>();
        authorityRepository.findAll()
                .forEach(authority -> {
                    ArrayList<ConfigAttribute> configAttributeList = new ArrayList<>();

                    authority.getRoleAuthorityList()
                            .forEach(roleAuthority -> {
                                Role role = roleAuthority.getRole();
                                configAttributeList.add(new SecurityConfig(role.getRoleName()));
                            });

                    resourceMap.put(
                            new AntPathRequestMatcher(authority.getUrlPath()),
                            configAttributeList);
                });

        return resourceMap;
    }

    @Override
    public List<AuthorityResponseDto> getAllAuthorityList() {
        return authorityRepository.findAll().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateAuthority(Long id, AuthorityCreateUpdateDto authorityDto) {
        Authority authority = authorityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("권한 정보를 조회할 수 없습니다."));

        authority.update(authorityDto);
    }

    @Override
    public void deleteAuthority(Long id) {
        Authority authority = authorityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("권한 정보를 조회할 수 없습니다."));

        // 권한을 가지고 있는 모든 역할에서 삭제하려고 하는 이 권한 삭제
        List<RoleAuthority> roleAuthorityList = authority.getRoleAuthorityList();
        roleAuthorityRepository.deleteAll(roleAuthorityList);

        authorityRepository.delete(authority);
    }

    @Override
    public AuthorityResponseDto toResponseDto(Authority authority) {
        AuthorityResponseDto dto = new AuthorityResponseDto();

        dto.setId(authority.getId());
        dto.setAuthorityName(authority.getAuthorityName());
        dto.setAuthorityKoreanName(authority.getAuthorityKoreanName());
        dto.setDescription(authority.getDescription());
        dto.setHttpMethod(authority.getHttpMethod().name());
        dto.setUrlPath(authority.getUrlPath());
        dto.setPriority(authority.getPriority());
        dto.setDefault(authority.isDefault());
        dto.setRegisterDateTime(authority.getRegisterDateTime());
        dto.setLastModifiedDateTime(authority.getLastModifiedDateTime());

        return dto;
    }
}
