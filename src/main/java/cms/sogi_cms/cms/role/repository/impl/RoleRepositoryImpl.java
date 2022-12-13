package cms.sogi_cms.cms.role.repository.impl;

import cms.sogi_cms.cms.role.dto.RoleSearch;
import cms.sogi_cms.cms.role.entity.Role;
import cms.sogi_cms.cms.role.repository.RoleRepositoryCustom;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static cms.sogi_cms.cms.role.entity.QRole.*;

@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Role> findList(RoleSearch roleSearch) {
        return queryFactory.selectFrom(role)
                .orderBy(order(roleSearch))
                .offset(roleSearch.getOffset())
                .limit(roleSearch.getSize())
                .fetch();
    }

    @Override
    public Long count(RoleSearch roleSearch) {
        Long count = queryFactory.select(role.count())
                .from(role)
                .fetchOne();

        return count == null ? 0 : count;
    }

    private OrderSpecifier<?> order(RoleSearch roleSearch) {
        if (roleSearch.getSortProperty() == null || roleSearch.getSortDirection() == null)
            return new OrderSpecifier<>(Order.DESC, role.id);

        throw new IllegalArgumentException("정렬 기준 값이 올바르지 않습니다.");
    }
}
