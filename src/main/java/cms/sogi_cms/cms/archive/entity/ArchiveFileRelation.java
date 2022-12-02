package cms.sogi_cms.cms.archive.entity;

import cms.sogi_cms.cms.file.entity.File;
import cms.sogi_cms.cms.support.SogiConstant;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = SogiConstant.MAIN_SITE_PREFIX + "_ARCHIVE_FILE_RELATION")
public class ArchiveFileRelation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Archive archive;

    @ManyToOne(fetch = FetchType.LAZY)
    private File file;
}
