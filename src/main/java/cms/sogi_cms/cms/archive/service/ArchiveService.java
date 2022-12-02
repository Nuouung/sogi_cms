package cms.sogi_cms.cms.archive.service;

import cms.sogi_cms.cms.archive.dto.ArchiveCreateUpdateDto;
import cms.sogi_cms.cms.archive.dto.ArchiveResponseDto;
import cms.sogi_cms.cms.archive.dto.ArchiveSearch;
import cms.sogi_cms.cms.support.pagination.Paging;

public interface ArchiveService {

    // c
    Long saveArchive(ArchiveCreateUpdateDto archiveDto);

    // r
    ArchiveResponseDto getArchiveById(Long id);

    ArchiveResponseDto getArchiveByTitle(String title);

    Paging<ArchiveResponseDto> getArchiveList(ArchiveSearch archiveSearch);

    Long getTotalNumber(ArchiveSearch archiveSearch);

    // u
    void updateArchive(Long id, ArchiveCreateUpdateDto archiveDto);

    // d
    void deleteArchive(Long id);
}
