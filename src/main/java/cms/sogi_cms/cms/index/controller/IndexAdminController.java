package cms.sogi_cms.cms.index.controller;

import cms.sogi_cms.cms.support.SogiConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH)
@RequiredArgsConstructor
public class IndexAdminController {

    @GetMapping("/main")
    public String adminIndexGet() {
        return "admin/index/index";
    }
}
