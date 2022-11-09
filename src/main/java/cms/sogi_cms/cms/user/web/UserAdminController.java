package cms.sogi_cms.cms.user.web;

import cms.sogi_cms.cms.support.SogiConstant;
import cms.sogi_cms.cms.support.pagination.Paging;
import cms.sogi_cms.cms.user.dto.UserCreateUpdateDto;
import cms.sogi_cms.cms.user.dto.UserResponseDto;
import cms.sogi_cms.cms.user.dto.UserSearch;
import cms.sogi_cms.cms.user.entity.User;
import cms.sogi_cms.cms.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping(SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/user")
@RequiredArgsConstructor
public class UserAdminController {

    private final UserService userService;

    private final UserCreateUpdateValidator userCreateUpdateValidator;

    // c
    @GetMapping("/new")
    public String insertUserGet(@ModelAttribute UserCreateUpdateDto userDto, Model model) {
        model.addAttribute("userCreateUpdateDto", userDto);
        model.addAttribute("formMode", "INSERT");

        return "admin/user/form";
    }

    @PostMapping("/new")
    public String insertUserPost(@ModelAttribute @Valid UserCreateUpdateDto userDto, BindingResult bindingResult, Model model) {
        userCreateUpdateValidator.validate(userDto, bindingResult);
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

//    // r
    @GetMapping("/list")
    public String userListGet(HttpServletRequest request, @ModelAttribute UserSearch userSearch, Model model) {
        Paging<UserResponseDto> paging = userService.getUserList(userSearch);

        model.addAttribute("paging", paging);
        model.addAttribute("urlPath", request.getRequestURI());
        model.addAttribute("queryString", paging.getPagingSearch().queryString());
        model.addAttribute("totalPage", paging.getTotalPages());

        return "admin/user/list";
    }

    @GetMapping("/{id}")
    public String userDetailGet(@PathVariable Long id, Model model) {
        User foundUser = userService.getUserById(id);
        model.addAttribute("user", foundUser);

        return "admin/user/detail";
    }
//
//    @GetMapping
//    public String deletedUserListGet() {
//        return null;
//    }
//
//    // u
//    @GetMapping
//    public String updateUserGet() {
//        return null;
//    }
//
//    @PostMapping
//    public String updateUserPost() {
//        return null;
//    }
//
//    @GetMapping
//    public String updateUserPasswordGet() {
//        return null;
//    }
//
//    @PostMapping
//    public String updateUserPasswordPost() {
//        return null;
//    }
//
//    // d
//    @PostMapping
//    public String deleteUserPost() {
//        return null;
//    }
}
