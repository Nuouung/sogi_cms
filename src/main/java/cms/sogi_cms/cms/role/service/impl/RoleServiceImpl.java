package cms.sogi_cms.cms.role.service.impl;

import cms.sogi_cms.cms.authority.entity.Authority;
import cms.sogi_cms.cms.role.entity.Role;
import cms.sogi_cms.cms.role.entity.RoleAuthority;
import cms.sogi_cms.cms.role.repository.RoleRepository;
import cms.sogi_cms.cms.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

}
