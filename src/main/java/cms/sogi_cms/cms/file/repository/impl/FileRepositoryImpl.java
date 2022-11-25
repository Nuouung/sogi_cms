package cms.sogi_cms.cms.file.repository.impl;

import cms.sogi_cms.cms.file.dto.FileSearch;
import cms.sogi_cms.cms.file.entity.File;
import cms.sogi_cms.cms.file.entity.QFile;
import cms.sogi_cms.cms.file.repository.FileRepositoryCustom;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static cms.sogi_cms.cms.file.entity.QFile.file;

@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    @Override
    public List<File> findList(FileSearch fileSearch) {
        return queryFactory.selectFrom(file)
                .orderBy(order(fileSearch))
                .where()
                .offset(fileSearch.getOffset())
                .limit(fileSearch.getSize())
                .fetch();
    }

    @Override
    public Long count(FileSearch fileSearch) {
        Long count = queryFactory.select(file.count())
                .from(file)
                .where()
                .fetchOne();

        return count == null ? 0 : count;
    }

    private OrderSpecifier<?> order(FileSearch fileSearch) {
        if (fileSearch.getSortProperty() == null || fileSearch.getSortDirection() == null)
            return new OrderSpecifier<>(Order.DESC, file.id);

        Order direction = switch (fileSearch.getSortDirection()) {
            case DESC -> Order.DESC;
            case ASC -> Order.ASC;
        };

        switch (fileSearch.getSortProperty()) {
            case "id" :
                return new OrderSpecifier<>(direction, file.id);
            case "registeredDateTime" :
                return new OrderSpecifier<>(direction, file.registeredDateTime);
        }

        throw new IllegalArgumentException("정렬 기준 값이 올바르지 않습니다.");
    }
}
