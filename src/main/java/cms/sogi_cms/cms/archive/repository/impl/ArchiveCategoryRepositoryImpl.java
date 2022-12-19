package cms.sogi_cms.cms.archive.repository.impl;

import cms.sogi_cms.cms.archive.dto.ArchiveCategorySearch;
import cms.sogi_cms.cms.archive.entity.ArchiveCategory;
import cms.sogi_cms.cms.archive.entity.QArchiveCategory;
import cms.sogi_cms.cms.archive.repository.ArchiveCategoryRepositoryCustom;
import cms.sogi_cms.cms.authority.dto.AuthoritySearch;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static cms.sogi_cms.cms.archive.entity.QArchiveCategory.*;
import static cms.sogi_cms.cms.authority.entity.QAuthority.authority;

@RequiredArgsConstructor
public class ArchiveCategoryRepositoryImpl implements ArchiveCategoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    @Override
    public List<ArchiveCategory> findList(ArchiveCategorySearch archiveCategorySearch) {
        return queryFactory.selectFrom(archiveCategory)
                .orderBy(order(archiveCategorySearch))
                .offset(archiveCategorySearch.getOffset())
                .limit(archiveCategorySearch.getSize())
                .fetch();
    }

    @Override
    public Long count(ArchiveCategorySearch archiveCategorySearch) {
        Long count = queryFactory.select(archiveCategory.count())
                .from(archiveCategory)
                .fetchOne();

        return count == null ? 0 : count;
    }

    private OrderSpecifier<?> order(ArchiveCategorySearch archiveCategorySearch) {
        if (archiveCategorySearch.getSortProperty() == null || archiveCategorySearch.getSortDirection() == null)
            return new OrderSpecifier<>(Order.DESC, archiveCategory.id);

        throw new IllegalArgumentException("정렬 기준 값이 올바르지 않습니다.");
    }
}
