package cms.sogi_cms.cms.support.init;

import cms.sogi_cms.cms.support.utils.InitializeUtils;
import cms.sogi_cms.cms.user.dto.UserCreateUpdateDto;
import cms.sogi_cms.cms.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserInitializer {

    @Value("${spring.jpa.hibernate.ddl-auto:none}")
    private String ddlMode;

    private final UserService userService;

    @PostConstruct
    public void userDataInit() throws IOException {
        if (InitializeUtils.isDataInit(ddlMode)) {
            log.info("[SOGICMS] 회원 데이터 초기화 작업을 수행합니다. [대상 데이터 베이스 테이블 = SOGI_USER]");

            UserCreateUpdateDto dto = new UserCreateUpdateDto();
            dto.setUsername("jin6016");
            dto.setPassword("wlstjr57!");
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
            dto.setRoleName("ROLE_ADMIN");

            userService.saveUser(dto);

            UserCreateUpdateDto dto2 = new UserCreateUpdateDto();
            dto2.setUsername("iamYourFather");
            dto2.setPassword("wlstjr57!");
            dto2.setLastname("이");
            dto2.setFirstname("진석");
            dto2.setEmail("jin6016@gmail.com");
            dto2.setIsMailing(false);
            dto2.setPhoneNumberFront("010");
            dto2.setPhoneNumberMiddle("8139");
            dto2.setPhoneNumberLast("2468");
            dto2.setGender("M");
            dto2.setBirthdayYear("1994");
            dto2.setBirthdayMonth("6");
            dto2.setBirthdayDay("16");
            dto2.setIsBirthdaySolar(true);
            dto2.setRoadNameAddress("경기 양주시 평화로1429번길 43-90");
            dto2.setLotNumberAddress("경기 양주시 덕계동 732");
            dto2.setDetailAddress("1층");
            dto2.setZipCode("11442");
            dto2.setRoleName("ROLE_MANAGER");

            userService.saveUser(dto2);

            UserCreateUpdateDto dto3 = new UserCreateUpdateDto();
            dto3.setUsername("iamYourNotFather");
            dto3.setPassword("wlstjr57!");
            dto3.setLastname("이");
            dto3.setFirstname("진석");
            dto3.setEmail("jin6016@gmail.com");
            dto3.setIsMailing(false);
            dto3.setPhoneNumberFront("010");
            dto3.setPhoneNumberMiddle("8139");
            dto3.setPhoneNumberLast("2468");
            dto3.setGender("M");
            dto3.setBirthdayYear("1994");
            dto3.setBirthdayMonth("6");
            dto3.setBirthdayDay("16");
            dto3.setIsBirthdaySolar(true);
            dto3.setRoadNameAddress("경기 양주시 평화로1429번길 43-90");
            dto3.setLotNumberAddress("경기 양주시 덕계동 732");
            dto3.setDetailAddress("1층");
            dto3.setZipCode("11442");
            dto3.setRoleName("ROLE_USER");

            userService.saveUser(dto3);
        }
    }
}
