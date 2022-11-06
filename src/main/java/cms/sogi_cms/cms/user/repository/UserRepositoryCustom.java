package cms.sogi_cms.cms.user.repository;

import cms.sogi_cms.cms.support.pagination.Paging;
import cms.sogi_cms.cms.user.dto.UserSearch;
import cms.sogi_cms.cms.user.entity.User;

public interface UserRepositoryCustom {

    Paging<User> findPage(UserSearch userSearch);
    Long count(UserSearch userSearch);
}
