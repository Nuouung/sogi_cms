package cms.sogi_cms.cms.archive.dto;

import cms.sogi_cms.cms.support.pagination.PagingSearch;
import cms.sogi_cms.cms.support.pagination.SortDirection;

public class ArchiveSearch extends PagingSearch {

    public ArchiveSearch(Integer pageNumber, Integer size, String sortProperty, SortDirection sortDirection, Boolean isPaged) {
        super(pageNumber, size, sortProperty, sortDirection, isPaged);
    }
}
