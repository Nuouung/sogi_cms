package cms.sogi_cms.cms.file.dto;

import cms.sogi_cms.cms.support.pagination.PagingSearch;
import cms.sogi_cms.cms.support.pagination.SortDirection;

public class FileSearch extends PagingSearch {

    public FileSearch(Integer pageNumber, Integer size, String sortProperty, SortDirection sortDirection, Boolean isPaged) {
        super(pageNumber, size, sortProperty, sortDirection, isPaged);
    }
}
