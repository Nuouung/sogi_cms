package cms.sogi_cms.cms.archive.service.impl;

import cms.sogi_cms.cms.archive.dto.ArchiveCreateUpdateDto;
import cms.sogi_cms.cms.archive.dto.ArchiveResponseDto;
import cms.sogi_cms.cms.archive.dto.ArchiveSearch;
import cms.sogi_cms.cms.archive.entity.Archive;
import cms.sogi_cms.cms.archive.entity.ArchiveCategory;
import cms.sogi_cms.cms.archive.entity.ArchiveFileRelation;
import cms.sogi_cms.cms.archive.repository.ArchiveFileRelationRepository;
import cms.sogi_cms.cms.archive.repository.ArchiveRepository;
import cms.sogi_cms.cms.archive.service.ArchiveCategoryService;
import cms.sogi_cms.cms.archive.service.ArchiveService;
import cms.sogi_cms.cms.file.entity.File;
import cms.sogi_cms.cms.file.service.FileService;
import cms.sogi_cms.cms.support.pagination.Paging;
import cms.sogi_cms.cms.user.entity.User;
import cms.sogi_cms.cms.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ArchiveServiceImpl implements ArchiveService {

    private final ArchiveRepository archiveRepository;
    private final ArchiveCategoryService archiveCategoryService;
    private final UserService userService;
    private final FileService fileService;

    private final ArchiveFileRelationRepository archiveFileRelationRepository;

    @Override
    public Long saveArchive(ArchiveCreateUpdateDto archiveDto) throws IOException {

        // 회원 조회
        User user = userService.getUserByUsername(archiveDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("회원을 조회할 수 없습니다."));

        // 아카이브 카테고리 조회
        ArchiveCategory archiveCategory = archiveCategoryService.getArchiveCategoryById(archiveDto.getArchiveCategoryId());

        Archive archive = Archive.create(archiveDto, user, archiveCategory);

        archiveRepository.save(archive);

        // 함께 등록된 파일들 저장, ArchiveFileRelation을 통해 파일과 아카이브 연결
        saveFileAndConnectWithArchive(archiveDto, archive);

        return archive.getId();
    }

    private void saveFileAndConnectWithArchive(ArchiveCreateUpdateDto archiveDto, Archive archive) throws IOException {
        for (MultipartFile multipartFile : archiveDto.getMultipartFileList()) {
            Long fileId = fileService.saveFile(multipartFile);
            File file = fileService.getFileByFileId(fileId);

            ArchiveFileRelation archiveFileRelation = ArchiveFileRelation.create(archive, file);

            archiveFileRelationRepository.save(archiveFileRelation);
        }
    }

    @Override
    public ArchiveResponseDto getArchiveById(Long id) {
        return null;
    }

    @Override
    public ArchiveResponseDto getArchiveByTitle(String title) {
        return null;
    }

    @Override
    public Paging<ArchiveResponseDto> getArchiveList(ArchiveSearch archiveSearch) {
        return null;
    }

    @Override
    public Long getTotalNumber(ArchiveSearch archiveSearch) {
        return null;
    }

    @Override
    public void updateArchive(Long id, ArchiveCreateUpdateDto archiveDto) {

    }

    @Override
    public void deleteArchive(Long id) {

    }
}
