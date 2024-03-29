package cms.sogi_cms.cms.user.web;

import cms.sogi_cms.cms.role.dto.RoleResponseDto;
import cms.sogi_cms.cms.role.entity.Role;
import cms.sogi_cms.cms.role.service.RoleService;
import cms.sogi_cms.cms.support.SogiConstant;
import cms.sogi_cms.cms.support.pagination.Paging;
import cms.sogi_cms.cms.user.dto.UserCreateUpdateDto;
import cms.sogi_cms.cms.user.dto.UserPasswordDto;
import cms.sogi_cms.cms.user.dto.UserResponseDto;
import cms.sogi_cms.cms.user.dto.UserSearch;
import cms.sogi_cms.cms.user.entity.User;
import cms.sogi_cms.cms.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
@RequestMapping(SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/user")
@RequiredArgsConstructor
public class UserAdminController {

    private final UserService userService;
    private final RoleService roleService;

    private final UserCreateValidator userCreateValidator;
    private final UserUpdateValidator userUpdateValidator;
    private final UserPasswordValidator userPasswordValidator;

    // c
    @GetMapping("/new")
    public String insertUserGet(HttpServletRequest request, @ModelAttribute UserCreateUpdateDto userDto, Model model) {
        model.addAttribute("userCreateUpdateDto", userDto);
        model.addAttribute("formMode", "INSERT");
        model.addAttribute("requestURI", request.getRequestURI());
        model.addAttribute("roles", getRoleResponseDtoList());

        return "admin/user/form";
    }

    @PostMapping("/new")
    public String insertUserPost(@ModelAttribute @Valid UserCreateUpdateDto userDto, BindingResult bindingResult, Model model) throws IOException {
        userCreateValidator.validate(userDto, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("formMode", "INSERT");
            model.addAttribute("userCreateUpdateDto", userDto);
            return "admin/user/form";
        }

        userService.saveUser(userDto);
        return "redirect:" + SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/user/list";
    }

//    @GetMapping
//    public void userExcelGet() {
//
//    }

    // r
    @GetMapping("/list")
    public String userListGet(HttpServletRequest request, @ModelAttribute UserSearch userSearch, Model model) throws IllegalAccessException {
        userSearch.setIsDeleted(false);
        userSearch.setIsActive(true);
        Paging<UserResponseDto> paging = userService.getUserList(userSearch);

        model.addAttribute("paging", paging);
        model.addAttribute("urlPath", request.getRequestURI());
        model.addAttribute("queryString", paging.getPagingSearch().getQueryString());
        model.addAttribute("totalPage", paging.getTotalPages());
        model.addAttribute("requestURI", request.getRequestURI());

        return "admin/user/list";
    }

    @GetMapping("/nonAdmin/list")
    public String nonAdminUserListGet(HttpServletRequest request, @ModelAttribute UserSearch userSearch, Model model) throws IllegalAccessException {
        userSearch.setIsDeleted(false);
        userSearch.setIsActive(true);
        Paging<UserResponseDto> paging = userService.getNonAminUserList(userSearch);

        model.addAttribute("paging", paging);
        model.addAttribute("urlPath", request.getRequestURI());
        model.addAttribute("queryString", paging.getPagingSearch().getQueryString());
        model.addAttribute("totalPage", paging.getTotalPages());
        model.addAttribute("requestURI", request.getRequestURI());

        return "admin/user/list";
    }

    @GetMapping("/admin/list")
    public String adminUserListGet(HttpServletRequest request, @ModelAttribute UserSearch userSearch, Model model) throws IllegalAccessException {
        userSearch.setIsDeleted(false);
        userSearch.setIsActive(true);
        Paging<UserResponseDto> paging = userService.getAdminUserList(userSearch);

        model.addAttribute("paging", paging);
        model.addAttribute("urlPath", request.getRequestURI());
        model.addAttribute("queryString", paging.getPagingSearch().getQueryString());
        model.addAttribute("totalPage", paging.getTotalPages());
        model.addAttribute("requestURI", request.getRequestURI());

        return "admin/user/list";
    }

    @GetMapping("/inactive/list")
    public String inactiveUserListGet(HttpServletRequest request, @ModelAttribute UserSearch userSearch, Model model) throws IllegalAccessException {
        userSearch.setIsDeleted(false);
        userSearch.setIsActive(false);
        Paging<UserResponseDto> paging = userService.getUserList(userSearch);

        model.addAttribute("paging", paging);
        model.addAttribute("urlPath", request.getRequestURI());
        model.addAttribute("queryString", paging.getPagingSearch().getQueryString());
        model.addAttribute("totalPage", paging.getTotalPages());
        model.addAttribute("requestURI", request.getRequestURI());

        return "admin/user/list";
    }

    @GetMapping("/deleted/list")
    public String deletedUserListGet(HttpServletRequest request, @ModelAttribute UserSearch userSearch, Model model) throws IllegalAccessException {
        userSearch.setIsDeleted(true);
        Paging<UserResponseDto> paging = userService.getUserList(userSearch);

        model.addAttribute("paging", paging);
        model.addAttribute("urlPath", request.getRequestURI());
        model.addAttribute("queryString", paging.getPagingSearch().getQueryString());
        model.addAttribute("totalPage", paging.getTotalPages());
        model.addAttribute("requestURI", request.getRequestURI());

        return "admin/user/list";
    }

    @GetMapping("/{id}")
    public String userDetailGet(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new UsernameNotFoundException("회원을 조회할 수 없습니다"));
        UserResponseDto userDto = userService.toResponseDto(user);

        model.addAttribute("userDto", userDto);

        return "admin/user/detail";
    }
//
//    @GetMapping
//    public String deletedUserListGet() {
//        return null;
//    }
//
    // u
    @GetMapping("/update/{id}")
    public String updateUserGet(HttpServletRequest request, @PathVariable Long id, Model model) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new UsernameNotFoundException("회원을 조회할 수 없습니다"));
        UserResponseDto responseDto = userService.toResponseDto(user);

        UserCreateUpdateDto userDto = responseDtoToCreateUpdateDto(responseDto);
        model.addAttribute("userCreateUpdateDto", userDto);
        model.addAttribute("formMode", "UPDATE");
        model.addAttribute("requestURI", request.getRequestURI());
        model.addAttribute("roles", getRoleResponseDtoList());
        model.addAttribute("file", responseDto.getFile());

        return "admin/user/form";
    }

    private List<RoleResponseDto> getRoleResponseDtoList() {
        List<Role> roleList = roleService.getAllRoles();
        List<RoleResponseDto> roleResponseDtoList = new ArrayList<>();
        for (Role role : roleList) {
            RoleResponseDto roleResponseDto = roleService.toRoleResponseDto(role);
            roleResponseDtoList.add(roleResponseDto);
        }

        return roleResponseDtoList;
    }

    private UserCreateUpdateDto responseDtoToCreateUpdateDto(UserResponseDto responseDto) {
        UserCreateUpdateDto updateDto = new UserCreateUpdateDto();

        updateDto.setId(responseDto.getId());
        updateDto.setUsername(responseDto.getUsername());
        updateDto.setLastname(responseDto.getLastname());
        updateDto.setFirstname(responseDto.getFirstname());
        updateDto.setLastname(responseDto.getLastname());
        updateDto.setEmail(responseDto.getEmail());
        updateDto.setIsMailing(responseDto.getIsMailing());
        updateDto.setPhoneNumberFront(responseDto.getPhoneNumber().split("-")[0]);
        updateDto.setPhoneNumberMiddle(responseDto.getPhoneNumber().split("-")[1]);
        updateDto.setPhoneNumberLast(responseDto.getPhoneNumber().split("-")[2]);
        updateDto.setGender(responseDto.getGender());
        updateDto.setBirthdayYear(responseDto.getBirthday().split("-")[0]);
        updateDto.setBirthdayMonth(responseDto.getBirthday().split("-")[1]);
        updateDto.setBirthdayDay(responseDto.getBirthday().split("-")[2]);
        updateDto.setIsBirthdaySolar(responseDto.getIsBirthdaySolar());
        updateDto.setRoadNameAddress(responseDto.getRoadNameAddress());
        updateDto.setLotNumberAddress(responseDto.getLotNumberAddress());
        updateDto.setDetailAddress(responseDto.getDetailAddress());
        updateDto.setZipCode(responseDto.getZipCode());
        updateDto.setExtraAddress(responseDto.getExtraAddress());
        updateDto.setRoleName(responseDto.getRoleName());
        updateDto.setIsActive(responseDto.isActive());
        updateDto.setIsDeleted(responseDto.isDeleted());

        return updateDto;
    }

    @PostMapping("/update/{id}")
    public String updateUserPost(@PathVariable Long id, @ModelAttribute UserCreateUpdateDto userDto, BindingResult bindingResult, Model model) throws IOException {
        userUpdateValidator.validate(userDto, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("formMode", "UPDATE");
            model.addAttribute("userCreateUpdateDto", userDto);
            return "admin/user/form";
        }

        userService.updateUser(id, userDto);
        return "redirect:" + SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/user/list";
    }

    @GetMapping("/update/password/{id}")
    public String updateUserPasswordGet(HttpServletRequest request, @PathVariable Long id, @ModelAttribute UserPasswordDto userDto, Model model) {
        model.addAttribute("userPasswordDto", userDto);
        model.addAttribute("id", id);
        model.addAttribute("requestURI", request.getRequestURI());
        return "admin/user/passwordForm";
    }

    @PostMapping("/update/password/{id}")
    public String updateUserPasswordPost(@PathVariable Long id, @ModelAttribute @Valid UserPasswordDto userDto, BindingResult bindingResult, Model model) {
        userPasswordValidator.validate(userDto, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("userPasswordDto", userDto);
            return "admin/user/passwordForm";
        }

        userService.updatePassword(id, userDto.getPassword());
        return "redirect:" + SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/user/list";
    }

    // d
    @PostMapping("/delete/{id}")
    public String deleteUserPost(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:" + SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/user/list";
    }
}
