package cms.sogi_cms.cms.user.repository.impl;

import cms.sogi_cms.cms.support.pagination.Paging;
import cms.sogi_cms.cms.user.dto.UserSearch;
import cms.sogi_cms.cms.user.entity.User;
import cms.sogi_cms.cms.user.repository.UserRepositoryCustom;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static cms.sogi_cms.cms.user.entity.QUser.*;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    @Override
    public Paging<User> findPage(UserSearch userSearch) {
        List<User> contents = queryFactory.selectFrom(user)
                .orderBy(order(userSearch))
                .where(
                        usernameLike(userSearch.getUsername()),
                        betweenRegisteredDateTime(userSearch.getRegisteredDateTime_start(), userSearch.getRegisteredDateTime_end()),
                        lastnameLike(userSearch.getLastname()),
                        firstnameList(userSearch.getFirstname()),
                        emailEquals(userSearch.getEmail()),
                        isMailing(userSearch.isMailing()),
                        genderEquals(userSearch.getGender()),
                        isBirthdaySolar(userSearch.isBirthdaySolar()),
                        isActive(userSearch.isActive()),
                        isDeleted(userSearch.isDeleted()))
                .offset(userSearch.getOffset())
                .limit(userSearch.getSize())
                .fetch();

        Long count = queryFactory.select(user.count())
                .from(user)
                .where(
                        usernameLike(userSearch.getUsername()),
                        betweenRegisteredDateTime(userSearch.getRegisteredDateTime_start(), userSearch.getRegisteredDateTime_end()),
                        lastnameLike(userSearch.getLastname()),
                        firstnameList(userSearch.getFirstname()),
                        emailEquals(userSearch.getEmail()),
                        isMailing(userSearch.isMailing()),
                        genderEquals(userSearch.getGender()),
                        isBirthdaySolar(userSearch.isBirthdaySolar()),
                        isActive(userSearch.isActive()),
                        isDeleted(userSearch.isDeleted()))
                .fetchOne();

        return new Paging<>(contents, count == null ? 0 : count, userSearch);
    }

    @Override
    public Long count(UserSearch userSearch) {
        Long count = queryFactory.select(user.count())
                .from(user)
                .where(
                        usernameLike(userSearch.getUsername()),
                        betweenRegisteredDateTime(userSearch.getRegisteredDateTime_start(), userSearch.getRegisteredDateTime_end()),
                        lastnameLike(userSearch.getLastname()),
                        firstnameList(userSearch.getFirstname()),
                        emailEquals(userSearch.getEmail()),
                        isMailing(userSearch.isMailing()),
                        genderEquals(userSearch.getGender()),
                        isBirthdaySolar(userSearch.isBirthdaySolar()),
                        isActive(userSearch.isActive()),
                        isDeleted(userSearch.isDeleted()))
                .fetchOne();

        return count == null ? 0 : count;
    }

    private Predicate isDeleted(boolean deleted) {
        return user.isDeleted.eq(deleted);
    }

    private Predicate isActive(boolean active) {
        return user.isActive.eq(active);
    }

    private Predicate isBirthdaySolar(boolean birthdaySolar) {
        return user.isBirthdaySolar.eq(birthdaySolar);
    }

    private Predicate genderEquals(String gender) {
        return gender != null ? user.gender.eq(gender) : null;
    }

    private Predicate isMailing(Boolean mailing) {
        return user.isMailing.eq(mailing);
    }

    private Predicate emailEquals(String email) {
        return email != null ? user.email.eq(email) : null;
    }

    private Predicate firstnameList(String firstname) {
        return firstname != null ? user.firstname.contains(firstname) : null;
    }

    private Predicate lastnameLike(String lastname) {
        return lastname != null ? user.lastname.contains(lastname) : null;
    }

    private Predicate betweenRegisteredDateTime(LocalDateTime registeredDateTime_start, LocalDateTime registeredDateTime_end) {
        return registeredDateTime_start != null && registeredDateTime_end != null ?
                user.registeredDateTime.between(registeredDateTime_start, registeredDateTime_end)
                : null;
    }

    private Predicate usernameLike(String username) {
        return username != null ? user.username.contains(username) : null;
    }

    private OrderSpecifier<?> order(UserSearch userSearch) {
        if (userSearch.getSortProperty() == null || userSearch.getSortDirection() == null)
            return new OrderSpecifier<>(Order.DESC, user.id);

        Order direction = switch (userSearch.getSortDirection()) {
            case DESC -> Order.DESC;
            case ASC -> Order.ASC;
        };

        switch (userSearch.getSortProperty()) {
            case "id" :
                return new OrderSpecifier<>(direction, user.id);
            case "registeredDateTime" :
                return new OrderSpecifier<>(direction, user.registeredDateTime);
            case "username" :
                return new OrderSpecifier<>(direction, user.username);
        }

        throw new IllegalArgumentException("정렬 기준 값이 올바르지 않습니다.");
    }
}
