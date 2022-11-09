package cms.sogi_cms.cms.support.pagination;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class Paging<T> {

    private List<T> contents;
    private long total;
    private PagingSearch pagingSearch;

    public Paging(List<T> contents, long total, PagingSearch pagingSearch) {
        this.contents = contents;
        this.total = total;
        this.pagingSearch = pagingSearch;
    }

    public int getTotalPages() {
        return getSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) getSize());
    }

    public int getSize() {
        return pagingSearch.getIsPaged() ? pagingSearch.getSize() : contents.size();
    }

    public boolean hasNext() {
        return getPageNumber() + 1 < getTotalPages();
    }

    public boolean isLast() {
        return !hasNext();
    }

    public int getPageNumber() {
        return pagingSearch.getIsPaged() ? pagingSearch.getPageNumber() : 0;
    }
}
