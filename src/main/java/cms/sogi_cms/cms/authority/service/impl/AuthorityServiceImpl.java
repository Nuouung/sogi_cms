package cms.sogi_cms.cms.authority.service.impl;

import cms.sogi_cms.cms.authority.dto.AuthorityCreateUpdateDto;
import cms.sogi_cms.cms.authority.dto.AuthoritySearch;
import cms.sogi_cms.cms.authority.entity.Authority;
import cms.sogi_cms.cms.authority.repository.AuthorityRepository;
import cms.sogi_cms.cms.authority.service.AuthorityService;
import cms.sogi_cms.cms.role.entity.Role;
import cms.sogi_cms.cms.role.entity.RoleAuthority;
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

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Override
    public Long saveAuthority(AuthorityCreateUpdateDto authorityDto) {
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
    public Paging<Authority> getAuthorityList(AuthoritySearch authoritySearch) {
        return null;
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
    public Long getTotalNumber(AuthoritySearch authoritySearch) {
        return null;
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

        authorityRepository.delete(authority);
    }
}
