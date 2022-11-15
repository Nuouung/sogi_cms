package cms.sogi_cms.cms.file.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ThumbnailFile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "thumbnailFile")
    private File file;

    private String filePath;
    private String fileHashedName;

    private String fileExtension;

    private Long fileSize;

    private Boolean isUploadSuccess;
    private String uploadErrorMessage;

    private Long imageWidth;
    private Long imageHeight;
}