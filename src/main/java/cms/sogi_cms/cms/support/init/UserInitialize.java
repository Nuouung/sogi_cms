package cms.sogi_cms.cms.support.init;

import cms.sogi_cms.cms.support.utils.InitializeUtils;
import cms.sogi_cms.cms.user.dto.UserCreateUpdateDto;
import cms.sogi_cms.cms.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserInitialize {

    @Value("${spring.jpa.hibernate.ddl-auto:none}")
    private String ddlMode;

    private final UserService userService;

    @PostConstruct
    public void userDataInit() {
        if (InitializeUtils.isDataInit(ddlMode)) {
            log.info("[SOGICMS] 회원 데이터 초기화 작업을 수행합니다. [대상 데이터 베이스 테이블 = SOGI_USER]");

            UserCreateUpdateDto dto = new UserCreateUpdateDto();
            dto.setUsername("jin6016");
            dto.setPassword("dlwlstjrakstp");
            dto.setLastname("이");
            dto.setFirstname("진석");
            dto.setEmail("jin6016@gmail.com");
            dto.setIsMailing(false);
            dto.setPhoneNumberFront("010");
            dto.setPhoneNumberMiddle("8139");
            dto.setPhoneNumberLast("2468");
            dto.setGender("M");
            dto.setBirthdayYear("1994");
            dto.setBirthdayMonth("6");
            dto.setBirthdayDay("16");
            dto.setIsBirthdaySolar(true);
            dto.setRoadNameAddress("경기 양주시 평화로1429번길 43-90");
            dto.setLotNumberAddress("경기 양주시 덕계동 732");
            dto.setDetailAddress("1층");
            dto.setZipCode("11442");

            userService.saveUser(dto);

            for (int i = 0; i < 10; i++) {
                UserCreateUpdateDto dtoo = new UserCreateUpdateDto();
                dtoo.setUsername("user" + (i + 2));
                dtoo.setPassword("dlwlstjrakstp");
                dtoo.setLastname("테스트");
                dtoo.setFirstname("회원");
                dtoo.setEmail("testUser@gmail.com");
                dtoo.setIsMailing(false);
                dtoo.setPhoneNumberFront("010");
                dtoo.setPhoneNumberMiddle("1111");
                dtoo.setPhoneNumberLast("2222");
                dtoo.setGender("X");
                dtoo.setBirthdayYear("2000");
                dtoo.setBirthdayMonth("1");
                dtoo.setBirthdayDay("1");
                dtoo.setIsBirthdaySolar(true);
                dtoo.setRoadNameAddress("경기 양주시 평화로1429번길 43-90");
                dtoo.setLotNumberAddress("경기 양주시 덕계동 732");
                dtoo.setDetailAddress("1층");
                dtoo.setZipCode("11442");

                userService.saveUser(dtoo);
            }
        }
    }
}
