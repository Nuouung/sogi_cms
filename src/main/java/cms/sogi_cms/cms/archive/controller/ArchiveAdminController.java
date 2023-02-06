package cms.sogi_cms.cms.archive.controller;

import cms.sogi_cms.cms.archive.dto.*;
import cms.sogi_cms.cms.archive.entity.Archive;
import cms.sogi_cms.cms.archive.entity.ArchiveCategory;
import cms.sogi_cms.cms.archive.entity.ArchiveFileRelation;
import cms.sogi_cms.cms.archive.service.ArchiveCategoryService;
import cms.sogi_cms.cms.archive.service.ArchiveService;
import cms.sogi_cms.cms.authority.dto.AuthorityCreateUpdateDto;
import cms.sogi_cms.cms.file.entity.File;
import cms.sogi_cms.cms.support.SogiConstant;
import cms.sogi_cms.cms.support.pagination.Paging;
import cms.sogi_cms.cms.user.dto.UserCreateUpdateDto;
import cms.sogi_cms.cms.user.dto.UserResponseDto;
import cms.sogi_cms.cms.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/archive")
@RequiredArgsConstructor
public class ArchiveAdminController {

    private final ArchiveService archiveService;
    private final ArchiveCategoryService archiveCategoryService;

    private final ArchiveCreateUpdateValidator archiveCreateUpdateValidator;

    // c
    @GetMapping("/{categoryName}/new")
    public String insertArchiveGet(HttpServletRequest request, @PathVariable String categoryName, @ModelAttribute ArchiveCreateUpdateDto archiveDto, Model model) {
        ArchiveCategory archiveCategory = archiveCategoryService.getArchiveCategoryByCategoryName(categoryName);

        model.addAttribute("archiveCreateUpdateDto", archiveDto);
        model.addAttribute("formMode", "INSERT");
        model.addAttribute("requestURI", request.getRequestURI());
        model.addAttribute("archiveCategory", archiveCategory);

        return "admin/archive/skin/" + removeHtmlSuffix(archiveCategory.getAdminFormSkin());
    }

    @PostMapping("/{categoryName}/new")
    public String insertArchivePost(HttpServletRequest request, @PathVariable String categoryName, @ModelAttribute ArchiveCreateUpdateDto archiveDto, BindingResult bindingResult, Model model) throws IOException {
        ArchiveCategory archiveCategory = archiveCategoryService.getArchiveCategoryByCategoryName(categoryName);

        archiveCreateUpdateValidator.validate(archiveDto, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("formMode", "INSERT");
            model.addAttribute("archiveCreateUpdateDto", archiveDto);
            model.addAttribute("archiveCategory", archiveCategory);

            return "admin/archive/skin/" + removeHtmlSuffix(archiveCategory.getAdminFormSkin());
        }

        bindDataToDto(request, archiveDto);
        archiveService.saveArchive(archiveDto);

        return "redirect:" + SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH +
                "/archive" +
                "/" + archiveCategory.getCategoryName() +
                "/list";
    }

    private void bindDataToDto(HttpServletRequest request, ArchiveCreateUpdateDto archiveDto) {
        // username
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("currentUser");
        if (user != null) {
            archiveDto.setUsername(user.getUsername());
        }

        // contentPlain
        archiveDto.setContentPlain(archiveDto.getContentHtml()
                .replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""));

        // 요약을 쓰지 않았으면 여기에서 부여
        if (!StringUtils.hasText(archiveDto.getContentSummary())) {
            String contentSummary = archiveDto.getContentPlain().length() >= 100 ?
                    archiveDto.getContentPlain().substring(0, 100) :
                    archiveDto.getContentPlain();
            archiveDto.setContentSummary(contentSummary);
        }

        // 고정글 체크를 하지 않았는데 고정글 기간이 있으면 다 지움
        if (!archiveDto.getIsSticky()) {
            archiveDto.setStickyStartDate("");
            archiveDto.setStickyEndDate("");
        }
    }

    // r
    @GetMapping("/{categoryName}/list")
    public String archiveListGet(HttpServletRequest request, @PathVariable String categoryName, @ModelAttribute ArchiveSearch archiveSearch, Model model) throws IllegalAccessException {
        Paging<ArchiveResponseDto> paging = archiveService.getArchiveList(archiveSearch);
        ArchiveCategory archiveCategory = archiveCategoryService.getArchiveCategoryByCategoryName(categoryName);

        model.addAttribute("paging", paging);
        model.addAttribute("archiveCategory", archiveCategory);
        model.addAttribute("urlPath", request.getRequestURI());
        model.addAttribute("queryString", paging.getPagingSearch().getQueryString());
        model.addAttribute("totalPage", paging.getTotalPages());
        model.addAttribute("requestURI", request.getRequestURI());

        return "admin/archive/skin/" + removeHtmlSuffix(archiveCategory.getAdminListSkin());
    }

    // u
    @GetMapping("/{categoryName}/update/{id}")
    public String updateUserGet(HttpServletRequest request, @PathVariable String categoryName, @PathVariable Long id, Model model) {
        ArchiveCategory archiveCategory = archiveCategoryService.getArchiveCategoryByCategoryName(categoryName);

        Archive archive = archiveService.getArchiveById(id);
        ArchiveCreateUpdateDto archiveDto = toCreateUpdateDto(archive);

        model.addAttribute("archiveCreateUpdateDto", archiveDto);
        model.addAttribute("formMode", "INSERT");
        model.addAttribute("requestURI", request.getRequestURI());
        model.addAttribute("archiveCategory", archiveCategory);

        return "admin/archive/skin/" + removeHtmlSuffix(archiveCategory.getAdminFormSkin());
    }

    private ArchiveCreateUpdateDto toCreateUpdateDto(Archive archive) {
        ArchiveCreateUpdateDto dto = new ArchiveCreateUpdateDto();
        dto.setUsername(archive.getUser().getUsername());
        dto.setTitle(archive.getTitle());
        dto.setContentHtml(archive.getContentHtml());
        dto.setContentPlain(archive.getContentPlain());
        dto.setContentSummary(archive.getContentSummary());
        dto.setArchiveCategoryId(archive.getArchiveCategory().getId());

        /*List<String> fileNameList = new ArrayList<>();
        for (ArchiveFileRelation archiveFileRelation : archive.getArchiveFileRelations()) {
            File file = archiveFileRelation.getFile();

        }
        dto.setFileNameList();*/

        dto.setHit(archive.getHit());
        dto.setRecommend(archive.getRecommend());
        dto.setIsPublish(archive.isPublish());
        dto.setIsSticky(archive.isSticky());
        dto.setStickyStartDate(archive.getStickyStartDate() == null ? null : archive.getStickyStartDate().toString());
        dto.setStickyEndDate(archive.getStickyEndDate() == null ? null : archive.getStickyEndDate().toString());

        return dto;
    }

    // d

    // utils
    private String removeHtmlSuffix(String skin) {
        return skin.endsWith(".html") ?
                skin.substring(0, skin.length() - 5) : skin;
    }
}
