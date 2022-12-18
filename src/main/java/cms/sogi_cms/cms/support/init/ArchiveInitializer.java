package cms.sogi_cms.cms.support.init;

import cms.sogi_cms.cms.archive.dto.ArchiveCategoryCreateUpdateDto;
import cms.sogi_cms.cms.archive.service.ArchiveCategoryService;
import cms.sogi_cms.cms.support.utils.InitializeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
public class ArchiveInitializer {

    @Value("${spring.jpa.hibernate.ddl-auto:none}")
    private String ddlMode;

    private final ArchiveCategoryService archiveCategoryService;

    @PostConstruct
    public void archiveDataInit() {
        if (InitializeUtils.isDataInit(ddlMode)) {
            log.info("[SOGICMS] 아카이브 데이터 초기화 작업을 수행합니다. [대상 데이터 베이스 테이블 = SOGI_ARCHIVE_CATEGORY / SOGI_ARCHIVE]");

            // 공지사항 카테고리 추가
            ArchiveCategoryCreateUpdateDto archiveCategoryDto = new ArchiveCategoryCreateUpdateDto();
            archiveCategoryDto.setCategoryName("notice-board");
            archiveCategoryDto.setCategoryKoreanName("공지사항");

            archiveCategoryService.saveArchiveCategory(archiveCategoryDto);
        }
    }
}
