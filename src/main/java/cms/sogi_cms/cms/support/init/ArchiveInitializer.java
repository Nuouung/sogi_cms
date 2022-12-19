package cms.sogi_cms.cms.support.init;

import cms.sogi_cms.cms.archive.dto.ArchiveCategoryCreateUpdateDto;
import cms.sogi_cms.cms.archive.dto.ArchiveCreateUpdateDto;
import cms.sogi_cms.cms.archive.service.ArchiveCategoryService;
import cms.sogi_cms.cms.archive.service.ArchiveService;
import cms.sogi_cms.cms.support.utils.InitializeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class ArchiveInitializer {

    @Value("${spring.jpa.hibernate.ddl-auto:none}")
    private String ddlMode;

    private final ArchiveCategoryService archiveCategoryService;
    private final ArchiveService archiveService;

    @PostConstruct
    public void archiveDataInit() throws IOException {
        if (InitializeUtils.isDataInit(ddlMode)) {
            log.info("[SOGICMS] 아카이브 데이터 초기화 작업을 수행합니다. [대상 데이터 베이스 테이블 = SOGI_ARCHIVE_CATEGORY / SOGI_ARCHIVE]");

            // 공지사항 카테고리 추가
            ArchiveCategoryCreateUpdateDto archiveCategoryDto = new ArchiveCategoryCreateUpdateDto();
            archiveCategoryDto.setCategoryName("notice-board");
            archiveCategoryDto.setCategoryKoreanName("공지사항");

            archiveCategoryService.saveArchiveCategory(archiveCategoryDto);

            // 공지사항 게시글 하나 추가
            ArchiveCreateUpdateDto archiveDto = new ArchiveCreateUpdateDto();
            archiveDto.setUsername("냐항");
            archiveDto.setTitle("제목이여라");
            archiveDto.setContentHtml("헬로여라");
            archiveDto.setContentPlain("헬로여라");
            archiveDto.setContentSummary("헬로여라");
            archiveDto.setArchiveCategoryId(archiveCategoryService.getArchiveCategoryByCategoryName("notice-board").getId());
            archiveDto.setCreatedDateTime(LocalDateTime.now());
            archiveDto.setLastModifiedDateTime(LocalDateTime.now());
            archiveDto.setPublish(true);
            archiveDto.setSticky(false);

            archiveService.saveArchive(archiveDto);
        }
    }
}
