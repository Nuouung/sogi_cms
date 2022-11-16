package cms.sogi_cms.cms.file.web;

import cms.sogi_cms.cms.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class FileAdminController {

    private final FileService fileService;

    @GetMapping("/file/test")
    public String fileGet() {
        return "test/file";
    }

    @PostMapping("/file/test")
    public String filePost(@RequestParam MultipartFile testFile) throws IOException {
        fileService.saveFile(testFile);

        return null;
    }
}
