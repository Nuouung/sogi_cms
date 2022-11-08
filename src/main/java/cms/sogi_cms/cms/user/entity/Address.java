package cms.sogi_cms.cms.user.entity;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Address {

    private String roadNameAddress; // 도로명 주소
    private String lotNumberAddress; // 지번 주소
    private String detailAddress; // 상세 주소
    private String zipCode; // 우편번호
    private String extraAddress; // 참고사항
}
