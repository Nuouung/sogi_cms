package cms.sogi_cms.cms.user.dto;

import cms.sogi_cms.cms.support.pagination.PagingSearch;
import cms.sogi_cms.cms.support.pagination.SortDirection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class UserSearch extends PagingSearch {

    private String username; // 아이디

    private LocalDateTime registeredDateTime_start;
    private LocalDateTime registeredDateTime_end;

    private String lastname; // 이름
    private String firstname; // 성

    private String email;
    private boolean isMailing;

    private String gender;
    private boolean isBirthdaySolar;

//    private Role role;

    private boolean isActive;
    private boolean isDeleted;

    public UserSearch(int pageNumber, int size, String sortProperty, SortDirection sortDirection, boolean isPaged, String username, LocalDateTime registeredDateTime_start, LocalDateTime registeredDateTime_end, String lastname, String firstname, String email, boolean isMailing, String gender, boolean isBirthdaySolar, boolean isActive, boolean isDeleted) {
        super(pageNumber, size, sortProperty, sortDirection, isPaged);
        this.username = username;
        this.registeredDateTime_start = registeredDateTime_start;
        this.registeredDateTime_end = registeredDateTime_end;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.isMailing = isMailing;
        this.gender = gender;
        this.isBirthdaySolar = isBirthdaySolar;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
    }
}
