package cms.sogi_cms.cms.archive.controller;

import cms.sogi_cms.cms.archive.dto.ArchiveCategoryCreateUpdateDto;
import cms.sogi_cms.cms.archive.dto.ArchiveCategoryResponseDto;
import cms.sogi_cms.cms.archive.dto.ArchiveCategorySearch;
import cms.sogi_cms.cms.archive.entity.ArchiveCategory;
import cms.sogi_cms.cms.archive.service.ArchiveCategoryService;
import cms.sogi_cms.cms.authority.dto.AuthorityCreateUpdateDto;
import cms.sogi_cms.cms.authority.entity.Authority;
import cms.sogi_cms.cms.support.SogiConstant;
import cms.sogi_cms.cms.support.pagination.Paging;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping(SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/archiveCategory")
@RequiredArgsConstructor
public class ArchiveCategoryAdminController {

    private final ArchiveCategoryService archiveCategoryService;

    private final ArchiveCategoryCreateUpdateValidator archiveCategoryCreateUpdateValidator;

    // c
    @GetMapping("/new")
    public String insertArchiveCategoryGet(HttpServletRequest request, @ModelAttribute ArchiveCategoryCreateUpdateDto archiveCategoryCreateUpdateDto,  Model model) {
        model.addAttribute("archiveCategoryCreateUpdateDto", archiveCategoryCreateUpdateDto);
        model.addAttribute("formMode", "INSERT");
        model.addAttribute("requestURI", request.getRequestURI());

        return "admin/archive/category/form";
    }

    @PostMapping("/new")
    public String insertArchiveCategoryPost(@ModelAttribute @Valid ArchiveCategoryCreateUpdateDto archiveCategoryDto, BindingResult bindingResult, Model model) throws IOException {
        archiveCategoryCreateUpdateValidator.validate(archiveCategoryDto, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("formMode", "INSERT");
            model.addAttribute("archiveCategoryCreateUpdateDto", archiveCategoryDto);
            return "admin/archive/category/form";
        }

        archiveCategoryService.saveArchiveCategory(archiveCategoryDto);
        return "redirect:" + SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/archive/category/list";
    }

    // r
    @GetMapping("/list")
    public String archiveCategoryListGet(HttpServletRequest request, @ModelAttribute ArchiveCategorySearch archiveCategorySearch, Model model) throws IllegalAccessException {
        Paging<ArchiveCategoryResponseDto> paging = archiveCategoryService.getArchiveCategoryList(archiveCategorySearch);

        model.addAttribute("paging", paging);
        model.addAttribute("urlPath", request.getRequestURI());
        model.addAttribute("queryString", paging.getPagingSearch().getQueryString());
        model.addAttribute("totalPage", paging.getTotalPages());
        model.addAttribute("requestURI", request.getRequestURI());

        return "admin/archive/category/list";
    }

    // u
    @GetMapping("/update/{id}")
    public String updateArchiveCategoryGet(HttpServletRequest request, @PathVariable Long id, Model model) {
        ArchiveCategory archiveCategory = archiveCategoryService.getArchiveCategoryById(id);
        ArchiveCategoryCreateUpdateDto archiveCategoryDto = toCreateUpdateDto(archiveCategory);

        model.addAttribute("archiveCategoryCreateUpdateDto", archiveCategoryDto);
        model.addAttribute("formMode", "UPDATE");
        model.addAttribute("requestURI", request.getRequestURI());

        return "admin/archive/category/form";
    }

    private ArchiveCategoryCreateUpdateDto toCreateUpdateDto(ArchiveCategory archiveCategory) {
        ArchiveCategoryCreateUpdateDto dto = new ArchiveCategoryCreateUpdateDto();

        dto.setId(archiveCategory.getId());
        dto.setCategoryName(archiveCategory.getCategoryName());
        dto.setCategoryKoreanName(archiveCategory.getCategoryKoreanName());
        dto.setAdminFormSkin(archiveCategory.getAdminFormSkin());
        dto.setAdminListSkin(archiveCategory.getAdminListSkin());
        dto.setUserListSkin(archiveCategory.getUserListSkin());
        dto.setUserDetailSkin(archiveCategory.getUserDetailSkin());
        dto.setPageSize(archiveCategory.getPageSize());
        dto.setSortDirection(archiveCategory.getSortDirection());

        return dto;
    }

    @PostMapping("/update/{id}")
    public String updateArchiveCategoryPost(@PathVariable Long id, @ModelAttribute ArchiveCategoryCreateUpdateDto archiveCategoryDto, BindingResult bindingResult, Model model) throws IOException {
        archiveCategoryCreateUpdateValidator.validate(archiveCategoryDto, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("formMode", "UPDATE");
            model.addAttribute("archiveCategoryCreateUpdateDto", archiveCategoryDto);
            return "admin/archive/category/form";
        }

        archiveCategoryService.updateArchiveCategory(id, archiveCategoryDto);
        return "redirect:" + SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/archive/category/list";
    }

    // d
    @PostMapping("/delete/{id}")
    public String deleteArchiveCategoryPost(@PathVariable Long id) {
        archiveCategoryService.deleteArchiveCategory(id);
        return "redirect:" + SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/archive/category/list";
    }
}
