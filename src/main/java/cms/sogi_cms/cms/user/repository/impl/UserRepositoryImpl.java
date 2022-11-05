package cms.sogi_cms.cms.user.repository.impl;

import cms.sogi_cms.cms.user.dto.UserSearch;
import cms.sogi_cms.cms.user.entity.User;
import cms.sogi_cms.cms.user.repository.UserRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class UserRepositoryImpl implements UserRepositoryCustom {

    @Override
    public Page<User> findPage(UserSearch userSearch, Pageable pageable) {
        return null;
        // https://adrenal.tistory.com/25
    }

    @Override
    public Integer findTotalUserNumber(UserSearch userSearch) {
        return null;
    }
}
