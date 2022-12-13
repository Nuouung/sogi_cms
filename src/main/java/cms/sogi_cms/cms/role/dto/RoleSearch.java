package cms.sogi_cms.cms.role.dto;

import cms.sogi_cms.cms.support.pagination.PagingSearch;
import cms.sogi_cms.cms.support.pagination.SortDirection;

public class RoleSearch extends PagingSearch {

    public RoleSearch(Integer pageNumber, Integer size, String sortProperty, SortDirection sortDirection, Boolean isPaged) {
        super(pageNumber, size, sortProperty, sortDirection, isPaged);
    }
}
