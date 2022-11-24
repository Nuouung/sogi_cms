package cms.sogi_cms.cms.support.pagination;

import cms.sogi_cms.cms.support.utils.PagingUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

@Getter @Setter
public class PagingSearch {

    private Integer pageNumber;
    private Integer size;

    private String sortProperty;
    private SortDirection sortDirection;

    private Boolean isPaged;

    /**
     * PagingSearch 생성자. pageNumber와 size가 기준값보다 작으면 예외를 뱉는다. (페이지 0은 1페이지)
     * @param pageNumber 0보다 커야함
     * @param size 1보다 커야함
     * @param sortProperty 정렬되는 필드
     * @param sortDirection SortDirection.ASC, SortDirection.DESC 중 하나
     * @param isPaged 페이징 적용하는지
     */
    public PagingSearch(Integer pageNumber, Integer size, String sortProperty, SortDirection sortDirection, Boolean isPaged) {
        if (pageNumber != null) {
            if (pageNumber < 0) {
                throw new IllegalArgumentException("페이지는 0보다 작을 수 없습니다.");
            }
        }

        if (size != null) {
            if (size < 1) {
                throw new IllegalArgumentException("사이즈는 1보다 작을 수 없습니다");
            }
        }

        this.pageNumber = pageNumber == null ? 0 : pageNumber;
        this.size = size == null ? 10 : size;
        this.sortProperty = sortProperty;
        this.sortDirection = sortDirection;
        this.isPaged = isPaged == null || isPaged;
    }

    public long getOffset() {
        return (long) size * (long) pageNumber;
    }

    public boolean hasPrevious() {
        return pageNumber > 0;
    }

    /**
     * 이전 페이지가 있다면 이전 페이지를, 없다면 1페이지를 반환한다.
     */
    public PagingSearch previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    public PagingSearch first() {
        return new PagingSearch(0, size, sortProperty, sortDirection, isPaged);
    }

    public PagingSearch previous() {
        return getPageNumber() == 0 ? this : new PagingSearch(pageNumber - 1, size, sortProperty, sortDirection, isPaged);
    }

    public PagingSearch next() {
        return new PagingSearch(pageNumber + 1, size, sortProperty, sortDirection, isPaged);
    }

    public String getQueryString() throws IllegalAccessException {
        return PagingUtils.getQueryString(new StringBuilder(), this); // 현재 클래스가 가진 필드 정보를 이용해 쿼리스트링 추출
    }
}
