package cms.sogi_cms.cms.file.web;

import cms.sogi_cms.cms.file.dto.FileResponseDto;
import cms.sogi_cms.cms.file.dto.FileSearch;
import cms.sogi_cms.cms.file.service.FileService;
import cms.sogi_cms.cms.support.SogiConstant;
import cms.sogi_cms.cms.support.pagination.Paging;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RequestMapping(SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/file")
@RequiredArgsConstructor
public class FileAdminController {

    private final FileService fileService;

    // c
    @GetMapping("/new")
    public String fileGet() {
        return "admin/file/form";
    }

    @PostMapping("/new")
    public String filePost(@RequestParam MultipartFile testFile) throws IOException {
        fileService.saveFile(testFile);

        return "redirect:" + SogiConstant.SITE_PATH + SogiConstant.ADMIN_PATH + "/file/list";
    }

    // r
    @GetMapping("/list")
    public String fileListGet(HttpServletRequest request, @ModelAttribute FileSearch fileSearch, Model model) throws IllegalAccessException {
        Paging<FileResponseDto> paging = fileService.getFileList(fileSearch);

        model.addAttribute("paging", paging);
        model.addAttribute("urlPath", request.getRequestURI());
        model.addAttribute("queryString", paging.getPagingSearch().getQueryString());
        model.addAttribute("totalPage", paging.getTotalPages());
        model.addAttribute("requestURI", request.getRequestURI());

        return "admin/file/list";
    }

    // d
    @PostMapping("/delete/{id}")
    public String deleteFile(@PathVariable Long id) throws FileNotFoundException {
        fileService.deleteFile(id);
        return null;
    }
}
