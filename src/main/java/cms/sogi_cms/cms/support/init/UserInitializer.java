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
        }
    }
}
