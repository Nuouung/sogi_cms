package cms.sogi_cms.cms.archive.repository;

import cms.sogi_cms.cms.archive.entity.ArchiveCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArchiveCategoryRepository extends JpaRepository<ArchiveCategory, Long>, ArchiveCategoryRepositoryCustom {

    Optional<ArchiveCategory> getArchiveCategoryByCategoryName(String categoryName);
}
