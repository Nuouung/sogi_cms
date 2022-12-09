package cms.sogi_cms.cms.user.dto;

import cms.sogi_cms.cms.role.entity.Role;
import cms.sogi_cms.cms.support.pagination.PagingSearch;
import cms.sogi_cms.cms.support.pagination.SortDirection;
import cms.sogi_cms.cms.support.utils.PagingUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    private List<Role> roleList;

    private Boolean isActive;
    private Boolean isDeleted;

    public UserSearch(Integer pageNumber, Integer size, String sortProperty, SortDirection sortDirection, Boolean isPaged, String username, String registeredDateTime_start, String registeredDateTime_end, String name, String email, Boolean isMailing, String gender, Boolean isBirthdaySolar, Boolean isActive, Boolean isDeleted) {
        super(pageNumber, size, sortProperty, sortDirection, isPaged);
        this.username = username;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.registeredDateTime_start = registeredDateTime_start == null ? null : LocalDate.parse(registeredDateTime_start, formatter).atStartOfDay();
        this.registeredDateTime_end = registeredDateTime_end == null ? null : LocalDate.parse(registeredDateTime_end, formatter).atStartOfDay();
        this.name = name;
        this.email = email;
        this.isMailing = isMailing;
        this.gender = gender;
        this.isBirthdaySolar = isBirthdaySolar;
        this.isActive = isActive;
        this.isDeleted = isDeleted;
    }
}
