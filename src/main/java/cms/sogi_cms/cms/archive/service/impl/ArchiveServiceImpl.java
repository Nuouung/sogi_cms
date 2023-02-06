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

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        if (archiveDto.getMultipartFileList() != null) {
            for (MultipartFile multipartFile : archiveDto.getMultipartFileList()) {
                if (!multipartFile.isEmpty()) {
                    Long fileId = fileService.saveFile(multipartFile);
                    File file = fileService.getFileByFileId(fileId);

                    ArchiveFileRelation archiveFileRelation = ArchiveFileRelation.create(archive, file);

                    archiveFileRelationRepository.save(archiveFileRelation);
                }
            }
        }
    }

    @Override
    public Archive getArchiveById(Long id) {
        return archiveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("아카이브를 찾을 수 없습니다."));
    }

    @Override
    public ArchiveResponseDto getArchiveByTitle(String title) {
        return null;
    }

    @Override
    public Paging<ArchiveResponseDto> getArchiveList(ArchiveSearch archiveSearch) {
        List<ArchiveResponseDto> contents = archiveRepository.findList(archiveSearch).stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
        Long total = archiveRepository.count(archiveSearch);

        return new Paging<>(contents, total, archiveSearch);
    }

    @Override
    public ArchiveResponseDto toResponseDto(Archive archive) {
        ArchiveResponseDto dto = new ArchiveResponseDto();

        dto.setId(archive.getId());
        dto.setUsername(archive.getUser().getUsername());
        dto.setTitle(archive.getTitle());
        dto.setContentHtml(archive.getContentHtml());
        dto.setContentPlain(archive.getContentPlain());
        dto.setContentSummary(archive.getContentSummary());
        dto.setArchiveCategoryName(archive.getArchiveCategory().getCategoryName());
        dto.setFilePathList(getFilePathList(archive));
        dto.setCreatedDateTime(archive.getCreatedDateTime());
        dto.setLastModifiedDateTime(archive.getLastModifiedDateTime());
        dto.setHit(archive.getHit());
        dto.setRecommend(archive.getRecommend());
        dto.setPublish(archive.isPublish());
        dto.setSticky(archive.isSticky());
        dto.setStickyStartDate(archive.getStickyStartDate());
        dto.setStickyEndDate(archive.getStickyEndDate());

        return dto;
    }

    private List<String> getFilePathList(Archive archive) {
        List<String> filePathList = new ArrayList<>();
        for (ArchiveFileRelation archiveFileRelation : archive.getArchiveFileRelations()) {
            File file = archiveFileRelation.getFile();
            filePathList.add(file.getFilePath());
        }

        return filePathList;
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
