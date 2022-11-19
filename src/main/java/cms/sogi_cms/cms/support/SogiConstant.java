package cms.sogi_cms.cms.support;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SogiConstant {

    // 운영체제가 어떤 구분자를 쓰는지 반환한다.
    public static final char SYSTEM_SEPARATOR = File.separatorChar;

    // 메인 사이트 테이블 접두사
    public static final String MAIN_SITE_PREFIX = "SOGI";

    // 웹 애플리케이션 루트 경로
    public static final String PROJECT_ROOT_PATH = System.getProperty("user.dir");

    // 사이트 전역 상수
    public static final String SITE_CONSTANT = "sogi";
    public static final String API_CONSTANT = "api";
    public static final String ADMIN_CONSTANT = "admin";

    // 파일 업로드 경로 최상위 위치
    public static final String UPLOAD_PATH = PROJECT_ROOT_PATH + SYSTEM_SEPARATOR + "uploadFile";

    // url 관련 경로
    public static final String SITE_PATH = "/" + SITE_CONSTANT;
    public static final String API_PATH = "/" + API_CONSTANT;
    public static final String ADMIN_PATH = "/" + ADMIN_CONSTANT;

}
