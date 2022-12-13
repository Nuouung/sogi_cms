package cms.sogi_cms.cms.authority.repository.impl;

import cms.sogi_cms.cms.authority.dto.AuthoritySearch;
import cms.sogi_cms.cms.authority.entity.Authority;
import cms.sogi_cms.cms.authority.repository.AuthorityRepositoryCustom;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static cms.sogi_cms.cms.authority.entity.QAuthority.*;

@RequiredArgsConstructor
public class AuthorityRepositoryImpl implements AuthorityRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    @Override
    public List<Authority> findList(AuthoritySearch authoritySearch) {

        return queryFactory.selectFrom(authority)
                .orderBy(order(authoritySearch))
                .offset(authoritySearch.getOffset())
                .limit(authoritySearch.getSize())
                .fetch();
    }

    @Override
    public Long count(AuthoritySearch authoritySearch) {
        Long count = queryFactory.select(authority.count())
                .from(authority)
                .fetchOne();

        return count == null ? 0 : count;
    }

    private OrderSpecifier<?> order(AuthoritySearch authoritySearch) {
        if (authoritySearch.getSortProperty() == null || authoritySearch.getSortDirection() == null)
            return new OrderSpecifier<>(Order.DESC, authority.id);

        throw new IllegalArgumentException("정렬 기준 값이 올바르지 않습니다.");
    }
}
