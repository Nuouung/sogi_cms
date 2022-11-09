package cms.sogi_cms.cms.user.dto;

import cms.sogi_cms.cms.support.pagination.PagingSearch;
import cms.sogi_cms.cms.support.pagination.SortDirection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Getter @Setter
public class UserSearch extends PagingSearch {

    private String username; // 아이디

    private LocalDateTime registeredDateTime_start;
    private LocalDateTime registeredDateTime_end;

    private String name; // 회원 이름

    private String email;
    private Boolean isMailing;

    private String gender;
    private Boolean isBirthdaySolar;

//    private Role role;

    private Boolean isActive;
    private Boolean isDeleted;

    public UserSearch(Integer pageNumber, Integer size, String sortProperty, SortDirection sortDirection, Boolean isPaged, String username, LocalDateTime registeredDateTime_start, LocalDateTime registeredDateTime_end, String name, String email, Boolean isMailing, String gender, Boolean isBirthdaySolar, Boolean isActive, Boolean isDeleted) {
        super(pageNumber, size, sortProperty, sortDirection, isPaged);
        this.username = username;
        this.registeredDateTime_start = registeredDateTime_start;
        this.registeredDateTime_end = registeredDateTime_end;
        this.name = name;
        this.email = email;
        this.isMailing = isMailing;
        this.gender = gender;
        this.isBirthdaySolar = isBirthdaySolar;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
    }

    @Override
    public String queryString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.queryString());

        if (StringUtils.hasText(getUsername())) {
            sb.append("&amp;username=").append(getUsername());
        }

        if (StringUtils.hasText(getName())) {
            sb.append("&amp;name=").append(getName());
        }

        if (StringUtils.hasText(getEmail())) {
            sb.append("&amp;email=").append(getEmail());
        }

        if (getIsMailing() != null) {
            sb.append("&amp;isMailing=").append(getIsMailing());
        }

        if (StringUtils.hasText(getGender())) {
            sb.append("&amp;gender=").append(getGender());
        }

        if (getIsBirthdaySolar() != null) {
            sb.append("&amp;isBirthdaySolar=").append(getIsBirthdaySolar());
        }

        if (getIsActive() != null) {
            sb.append("&amp;isActive=").append(getIsActive());
        }

        if (getIsDeleted() != null) {
            sb.append("&amp;isDeleted=").append(getIsDeleted());
        }

        return sb.toString();
    }
}
