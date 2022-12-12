package cms.sogi_cms.cms.file.web;

import cms.sogi_cms.cms.file.web.FileAdminController;
import cms.sogi_cms.cms.support.utils.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@Controller
public class FileBaseController {

    @GetMapping("/image")
    @ResponseBody
    public Resource hello(@RequestParam String filePath) throws MalformedURLException {
        return new UrlResource("file:" + filePath);
    }
}
