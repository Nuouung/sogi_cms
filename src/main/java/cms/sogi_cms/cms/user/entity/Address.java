package cms.sogi_cms.cms.user.entity;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Address {

    private String address; // 주소
    private String addressSub; // 상세주소
    private String zipCode; // 우편번호
}
