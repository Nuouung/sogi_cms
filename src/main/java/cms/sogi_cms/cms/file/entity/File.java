package cms.sogi_cms.cms.file.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class File {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 등록 user 연결

    private String filePath;
    private String fileOriginalName;
    private String fileHashedName;

    private String fileExtension;
    private String fileMimeType;
    @Enumerated(EnumType.STRING)
    private MediaType fileMediaType;

    private Long fileSize;

    private LocalDateTime registeredDateTime;

    private Boolean isUploadSuccess;
    private String uploadErrorMessage;

    private Long downloadCount;

    private Long imageWidth;
    private Long imageHeight;

    // 썸네일 연결
    @OneToOne
    @JoinColumn(name = "THUMBNAIL_ID")
    private ThumbnailFile thumbnailFile;
}
