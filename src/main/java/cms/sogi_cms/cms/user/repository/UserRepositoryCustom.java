package cms.sogi_cms.cms.user.repository;

import cms.sogi_cms.cms.user.dto.UserSearch;
import cms.sogi_cms.cms.user.entity.User;

import java.util.List;

public interface UserRepositoryCustom {

    List<User> findList(UserSearch userSearch);
    Long count(UserSearch userSearch);
}
