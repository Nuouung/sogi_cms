package cms.sogi_cms.cms.file.entity;

import cms.sogi_cms.cms.support.SogiConstant;
import lombok.*;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = SogiConstant.MAIN_SITE_PREFIX + "_THUMBNAIL_FILE")
public class ThumbnailFile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "thumbnailFile")
    private File file;

    private String filePath;
    private String fileHashedName;

    private String fileExtension;

    private Long fileSize;

    private Long imageWidth;
    private Long imageHeight;

    public static ThumbnailFile create(java.io.File thumbnailFile) throws IOException {
        BufferedImage image = ImageIO.read(thumbnailFile);

        return ThumbnailFile.builder()
                .filePath(thumbnailFile.getAbsolutePath())
                .fileHashedName(thumbnailFile.getName())
                .fileExtension(FilenameUtils.getExtension(thumbnailFile.getName()))
                .fileSize(thumbnailFile.length())
                .imageWidth((long) image.getWidth())
                .imageHeight((long) image.getHeight())
                .build();
    }
}