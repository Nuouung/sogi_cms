package cms.sogi_cms.cms.archive.controller;

import cms.sogi_cms.cms.archive.dto.*;
import cms.sogi_cms.cms.archive.entity.ArchiveCategory;
import cms.sogi_cms.cms.archive.service.ArchiveCategoryService;
import cms.sogi_cms.cms.archive.service.ArchiveService;
import cms.sogi_cms.cms.authority.dto.AuthorityCreateUpdateDto;
import cms.sogi_cms.cms.support.SogiConstant;
import cms.sogi_cms.cms.support.pagination.Paging;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/archive")
@RequiredArgsConstructor
public class ArchiveAdminController {

    private final ArchiveService archiveService;
    private final ArchiveCategoryService archiveCategoryService;

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

    // d

    // utils
    private String removeHtmlSuffix(String skin) {
        return skin.endsWith(".html") ?
                skin.substring(0, skin.length() - 5) : skin;
    }
}
