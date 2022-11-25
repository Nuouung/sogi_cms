package cms.sogi_cms.cms.file.repository;

import cms.sogi_cms.cms.file.dto.FileSearch;
import cms.sogi_cms.cms.file.entity.File;

import java.util.List;

public interface FileRepositoryCustom {

    List<File> findList(FileSearch fileSearch);
    Long count(FileSearch fileSearch);
}
