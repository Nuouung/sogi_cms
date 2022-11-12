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

    public UserSearch(Integer pageNumber, Integer size, String sortProperty, SortDirection sortDirection, Boolean isPaged, String username, String registeredDateTime_start, String registeredDateTime_end, String name, String email, Boolean isMailing, String gender, Boolean isBirthdaySolar, Boolean isActive, Boolean isDeleted) {
        super(pageNumber, size, sortProperty, sortDirection, isPaged);
        this.username = username;

        if (registeredDateTime_start != null) {
            LocalDateTime parse = LocalDateTime.parse(registeredDateTime_start);
        }
//        this.registeredDateTime_start = registeredDateTime_start;
//        this.registeredDateTime_end = registeredDateTime_end;
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
            sb.append("&username=").append(getUsername());
        }

        if (StringUtils.hasText(getName())) {
            sb.append("&name=").append(getName());
        }

        if (StringUtils.hasText(getEmail())) {
            sb.append("&email=").append(getEmail());
        }

        if (getIsMailing() != null) {
            sb.append("&isMailing=").append(getIsMailing());
        }

        if (StringUtils.hasText(getGender())) {
            sb.append("&gender=").append(getGender());
        }

        if (getIsBirthdaySolar() != null) {
            sb.append("&isBirthdaySolar=").append(getIsBirthdaySolar());
        }

        if (getIsActive() != null) {
            sb.append("&isActive=").append(getIsActive());
        }

        if (getIsDeleted() != null) {
            sb.append("&isDeleted=").append(getIsDeleted());
        }

        return sb.toString();
    }
}
