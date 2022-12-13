package cms.sogi_cms.cms.role.controller;

import cms.sogi_cms.cms.role.dto.RoleAuthorityResponseDto;
import cms.sogi_cms.cms.role.dto.RoleCreateUpdateDto;
import cms.sogi_cms.cms.role.dto.RoleResponseDto;
import cms.sogi_cms.cms.role.dto.RoleSearch;
import cms.sogi_cms.cms.role.entity.Role;
import cms.sogi_cms.cms.role.entity.RoleAuthority;
import cms.sogi_cms.cms.role.service.RoleService;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/role")
@RequiredArgsConstructor
public class RoleAdminController {

    private final RoleService roleService;

    private final RoleCreateUpdateValidator roleCreateUpdateValidator;

    // c
    @GetMapping("/new")
    public String insertRoleGet(HttpServletRequest request, @ModelAttribute RoleCreateUpdateDto roleDto, Model model) {


        model.addAttribute("RoleCreateUpdateDto", roleDto);
        model.addAttribute("formMode", "INSERT");
        model.addAttribute("requestURI", request.getRequestURI());

        return "admin/role/form";
    }

    @PostMapping("/new")
    public String insertRolePost(@ModelAttribute @Valid RoleCreateUpdateDto roleDto, BindingResult bindingResult, Model model) throws IOException {
        roleCreateUpdateValidator.validate(roleDto, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("formMode", "INSERT");
            model.addAttribute("RoleCreateUpdateDto", roleDto);
            return "admin/role/form";
        }

        roleService.saveRole(roleDto);
        return "redirect:" + SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/role/list";
    }

    // r
    @GetMapping("/list")
    public String RoleListGet(HttpServletRequest request, @ModelAttribute RoleSearch roleSearch, Model model) throws IllegalAccessException {
        Paging<RoleResponseDto> paging = roleService.getRoleList(roleSearch);

        model.addAttribute("paging", paging);
        model.addAttribute("urlPath", request.getRequestURI());
        model.addAttribute("queryString", paging.getPagingSearch().getQueryString());
        model.addAttribute("totalPage", paging.getTotalPages());
        model.addAttribute("requestURI", request.getRequestURI());

        return "admin/role/list";
    }

    // u
    @GetMapping("/update/{id}")
    public String updateRoleGet(HttpServletRequest request, @PathVariable Long id, Model model) {
        Role Role = roleService.getRoleById(id);
        RoleCreateUpdateDto roleDto = toCreateUpdateDto(Role);

        model.addAttribute("RoleCreateUpdateDto", roleDto);
        model.addAttribute("formMode", "UPDATE");
        model.addAttribute("requestURI", request.getRequestURI());

        return "admin/role/form";
    }

    private RoleCreateUpdateDto toCreateUpdateDto(Role role) {
        RoleCreateUpdateDto dto = new RoleCreateUpdateDto();

        dto.setId(role.getId());
        dto.setRoleName(role.getRoleName());
        dto.setKoreanName(role.getKoreanName());
        dto.setDescription(role.getDescription());
        dto.setAdmin(role.isAdmin());
        dto.setDefaultUser(role.isDefaultUser());
        dto.setRoleAuthorityList(roleService.getRoleAuthorityResponseDtoList(role));

        return dto;
    }

    @PostMapping("/update/{id}")
    public String updateRolePost(@PathVariable Long id, @ModelAttribute RoleCreateUpdateDto roleDto, BindingResult bindingResult, Model model) throws IOException {
        roleCreateUpdateValidator.validate(roleDto, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("formMode", "UPDATE");
            model.addAttribute("roleCreateUpdateDto", roleDto);
            return "admin/role/form";
        }

        roleService.updateRole(id, roleDto);
        return "redirect:" + SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/role/list";
    }

    // d
    @PostMapping("/delete/{id}")
    public String deleteUserPost(@PathVariable Long id) {
        roleService.deleteRole(id);
        return "redirect:" + SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/role/list";
    }
}
