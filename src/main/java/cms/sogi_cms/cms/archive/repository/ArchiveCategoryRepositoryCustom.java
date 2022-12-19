package cms.sogi_cms.cms.archive.repository;

import cms.sogi_cms.cms.archive.dto.ArchiveCategorySearch;
import cms.sogi_cms.cms.archive.entity.ArchiveCategory;

import java.util.List;

public interface ArchiveCategoryRepositoryCustom {

    List<ArchiveCategory> findList(ArchiveCategorySearch archiveCategorySearch);
    Long count(ArchiveCategorySearch archiveCategorySearch);
}
