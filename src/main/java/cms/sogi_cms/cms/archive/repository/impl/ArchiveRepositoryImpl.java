package cms.sogi_cms.cms.archive.repository.impl;

import cms.sogi_cms.cms.archive.dto.ArchiveCategorySearch;
import cms.sogi_cms.cms.archive.dto.ArchiveSearch;
import cms.sogi_cms.cms.archive.entity.Archive;
import cms.sogi_cms.cms.archive.entity.ArchiveCategory;
import cms.sogi_cms.cms.archive.entity.QArchive;
import cms.sogi_cms.cms.archive.repository.ArchiveCategoryRepositoryCustom;
import cms.sogi_cms.cms.archive.repository.ArchiveRepositoryCustom;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static cms.sogi_cms.cms.archive.entity.QArchive.*;
import static cms.sogi_cms.cms.archive.entity.QArchiveCategory.archiveCategory;

@RequiredArgsConstructor
public class ArchiveRepositoryImpl implements ArchiveRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    @Override
    public List<Archive> findList(ArchiveSearch archiveSearch) {
        return queryFactory.selectFrom(archive)
                .orderBy(order(archiveSearch))
                .offset(archiveSearch.getOffset())
                .limit(archiveSearch.getSize())
                .fetch();
    }

    @Override
    public Long count(ArchiveSearch archiveSearch) {
        Long count = queryFactory.select(archive.count())
                .from(archive)
                .fetchOne();

        return count == null ? 0 : count;
    }

    private OrderSpecifier<?> order(ArchiveSearch archiveSearch) {
        if (archiveSearch.getSortProperty() == null || archiveSearch.getSortDirection() == null)
            return new OrderSpecifier<>(Order.DESC, archive.id);

        throw new IllegalArgumentException("정렬 기준 값이 올바르지 않습니다.");
    }
}
