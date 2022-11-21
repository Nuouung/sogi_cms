package cms.sogi_cms.cms.support.init;

import cms.sogi_cms.cms.configuration.entity.OptionType;
import cms.sogi_cms.cms.configuration.service.ConfigurationService;
import cms.sogi_cms.cms.support.utils.InitializeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConfigurationInitialize {

    @Value("${spring.jpa.hibernate.ddl-auto:none}")
    private String ddlMode;

    private final ConfigurationService configurationService;

    @PostConstruct
    public void dataInit() {
        if (InitializeUtils.isDataInit(ddlMode)) {
            log.info("[SOGICMS] 설정 데이터 초기화 작업을 수행합니다. [대상 데이터 베이스 테이블 = SOGI_CONFIGURATION]");
            configurationService.createConfiguration(
                    "file",
                    "upload_white_list",
                    "jpg,jpeg,png,gif,bmp,ppt,pptx,xls,xlsx,doc,docx,pdf,txt,hwp,swf,wmv,mp4,flv,ogv,ogg,mp3,m4a,wav,wma,zip,rar,7zip",
                    "업로드 허용 확장자",
                    OptionType.TEXTAREA,
                    ",로 구분하여 빈칸 없이 기입해 주십시오.");

            configurationService.createConfiguration(
                    "file",
                    "thumbnail_default_width",
                    "400",
                    "썸네일 생성 기본 가로 길이",
                    OptionType.TEXT,
                    "기준은 px입니다.");

            configurationService.createConfiguration(
                    "file",
                    "thumbnail_default_height",
                    "300",
                    "썸네일 생성 기본 세로 길이",
                    OptionType.TEXT,
                    "기준은 px입니다.");

            configurationService.createConfiguration(
                    "file",
                    "upload_limit_size",
                    "268435456",
                    "최대 허용 업로드 크기",
                    OptionType.TEXT,
                    "기준은 byte입니다.");
        }
    }
}
