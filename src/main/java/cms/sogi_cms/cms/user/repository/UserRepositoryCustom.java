package cms.sogi_cms.cms.user.repository;

import cms.sogi_cms.cms.user.dto.UserSearch;
import cms.sogi_cms.cms.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {

    Page<User> findPage(UserSearch userSearch);
    Integer count(UserSearch userSearch);
}
