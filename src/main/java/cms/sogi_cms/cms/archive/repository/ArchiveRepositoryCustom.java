package cms.sogi_cms.cms.archive.repository;

import cms.sogi_cms.cms.archive.dto.ArchiveSearch;
import cms.sogi_cms.cms.archive.entity.Archive;

import java.util.List;

public interface ArchiveRepositoryCustom {

    List<Archive> findList(ArchiveSearch archiveSearch);
    Long count(ArchiveSearch archiveSearch);
}
