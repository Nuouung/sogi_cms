package cms.sogi_cms.cms.aop.controllerAdvice;

import cms.sogi_cms.cms.archive.service.ArchiveCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ControllerAdvice
@RequiredArgsConstructor
public class BaseController {

    private final ArchiveCategoryService archiveCategoryService;

    @ModelAttribute
    public void bindCurrentUser(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        model.addAttribute("currentUser", session == null ? null : session.getAttribute("currentUser"));
    }

    @ModelAttribute
    public void bindArchiveCategory(Model model) {
        model.addAttribute("archiveCategories", archiveCategoryService.getAllArchiveCategory());
    }
}
