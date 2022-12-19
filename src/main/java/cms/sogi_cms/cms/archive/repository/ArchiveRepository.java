package cms.sogi_cms.cms.archive.repository;

import cms.sogi_cms.cms.archive.entity.Archive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchiveRepository extends JpaRepository<Archive, Long>, ArchiveRepositoryCustom {
}
