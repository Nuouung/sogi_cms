package cms.sogi_cms.cms.support;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SogiConstant {

    // 메인 사이트 테이블 접두사
    public static final String MAIN_SITE_PREFIX = "SOGI";

    // 웹 애플리케이션 루트 경로
    public static final String PROJECT_ROOT_PATH = System.getProperty("user.dir");

    // url 관련 경로
    public static final String SITE_PATH = "/sogi";
    public static final String API_PATH = "/api";
    public static final String ADMIN_PATH = "/admin";
    public static final String UPLOAD_PATH = PROJECT_ROOT_PATH + "/file/upload";

}
