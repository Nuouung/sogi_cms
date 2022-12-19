package cms.sogi_cms.cms.authority.controller;

import cms.sogi_cms.cms.authority.dto.AuthorityCreateUpdateDto;
import cms.sogi_cms.cms.authority.dto.AuthorityResponseDto;
import cms.sogi_cms.cms.authority.dto.AuthoritySearch;
import cms.sogi_cms.cms.authority.entity.Authority;
import cms.sogi_cms.cms.authority.service.AuthorityService;
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
@RequestMapping(SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/authority")
@RequiredArgsConstructor
public class AuthorityAdminController {

    private final AuthorityService authorityService;

    private final AuthorityCreateUpdateValidator authorityCreateUpdateValidator;

    // c
    @GetMapping("/new")
    public String insertAuthorityGet(HttpServletRequest request, @ModelAttribute AuthorityCreateUpdateDto authorityDto, Model model) {
        model.addAttribute("authorityCreateUpdateDto", authorityDto);
        model.addAttribute("formMode", "INSERT");
        model.addAttribute("requestURI", request.getRequestURI());

        return "admin/authority/form";
    }

    @PostMapping("/new")
    public String insertAuthorityPost(@ModelAttribute @Valid AuthorityCreateUpdateDto authorityDto, BindingResult bindingResult, Model model) throws IOException {
        authorityCreateUpdateValidator.validate(authorityDto, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("formMode", "INSERT");
            model.addAttribute("authorityCreateUpdateDto", authorityDto);
            return "admin/authority/form";
        }

        authorityService.saveAuthority(authorityDto);
        return "redirect:" + SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/authority/list";
    }

    // r
    @GetMapping("/list")
    public String authorityListGet(HttpServletRequest request, @ModelAttribute AuthoritySearch authoritySearch, Model model) throws IllegalAccessException {
        Paging<AuthorityResponseDto> paging = authorityService.getAuthorityList(authoritySearch);

        model.addAttribute("paging", paging);
        model.addAttribute("urlPath", request.getRequestURI());
        model.addAttribute("queryString", paging.getPagingSearch().getQueryString());
        model.addAttribute("totalPage", paging.getTotalPages());
        model.addAttribute("requestURI", request.getRequestURI());

        return "admin/authority/list";
    }

    // u
    @GetMapping("/update/{id}")
    public String updateAuthorityGet(HttpServletRequest request, @PathVariable Long id, Model model) {
        Authority authority = authorityService.getAuthorityById(id);
        AuthorityCreateUpdateDto authorityDto = toCreateUpdateDto(authority);

        model.addAttribute("authorityCreateUpdateDto", authorityDto);
        model.addAttribute("formMode", "UPDATE");
        model.addAttribute("requestURI", request.getRequestURI());

        return "admin/authority/form";
    }

    private AuthorityCreateUpdateDto toCreateUpdateDto(Authority authority) {
        AuthorityCreateUpdateDto dto = new AuthorityCreateUpdateDto();

        dto.setId(dto.getId());
        dto.setAuthorityName(authority.getAuthorityName());
        dto.setAuthorityKoreanName(authority.getAuthorityKoreanName());
        dto.setDescription(authority.getDescription());
        dto.setHttpMethod(authority.getHttpMethod());
        dto.setUrlPath(authority.getUrlPath());
        dto.setPriority(authority.getPriority());

        return dto;
    }

    @PostMapping("/update/{id}")
    public String updateAuthorityPost(@PathVariable Long id, @ModelAttribute AuthorityCreateUpdateDto authorityDto, BindingResult bindingResult, Model model) throws IOException {
        authorityCreateUpdateValidator.validate(authorityDto, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("formMode", "UPDATE");
            model.addAttribute("authorityCreateUpdateDto", authorityDto);
            return "admin/authority/form";
        }

        authorityService.updateAuthority(id, authorityDto);
        return "redirect:" + SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/authority/list";
    }

    // d
    @PostMapping("/delete/{id}")
    public String deleteAuthorityPost(@PathVariable Long id) {
        authorityService.deleteAuthority(id);
        return "redirect:" + SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/authority/list";
    }
}
