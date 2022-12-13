package cms.sogi_cms.cms.authority.dto;

import cms.sogi_cms.cms.support.pagination.PagingSearch;
import cms.sogi_cms.cms.support.pagination.SortDirection;

public class AuthoritySearch extends PagingSearch {

    public AuthoritySearch(Integer pageNumber, Integer size, String sortProperty, SortDirection sortDirection, Boolean isPaged) {
        super(pageNumber, size, sortProperty, sortDirection, isPaged);
    }
}
