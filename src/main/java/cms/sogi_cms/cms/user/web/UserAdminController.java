package cms.sogi_cms.cms.user.web;

import cms.sogi_cms.cms.support.SogiConstant;
import cms.sogi_cms.cms.user.dto.UserCreateUpdateDto;
import cms.sogi_cms.cms.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/user")
@RequiredArgsConstructor
public class UserAdminController {

    private final UserService userService;

    // c
    @GetMapping("/insert")
    public String insertUserGet(@ModelAttribute UserCreateUpdateDto userDto, Model model) {
        model.addAttribute("userDto", userDto);
        model.addAttribute("formMode", "INSERT");

        return "admin/user/form";
    }

    @PostMapping("/insert")
    public String insertUserPost(@ModelAttribute @Valid UserCreateUpdateDto userDto, Model model) {
        return null;
    }

//    @GetMapping
//    public void userExcelGet() {
//
//    }
//
//    // r
//    @GetMapping
//    public String userListGet() {
//        return null;
//    }
//
//    @GetMapping
//    public String userDetailGet() {
//        return null;
//    }
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
