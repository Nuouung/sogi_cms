package cms.sogi_cms.cms.file.repository;

import cms.sogi_cms.cms.file.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
