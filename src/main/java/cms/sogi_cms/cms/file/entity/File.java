package cms.sogi_cms.cms.file.entity;

import cms.sogi_cms.cms.support.SogiConstant;
import cms.sogi_cms.cms.user.dto.UserCreateUpdateDto;
import cms.sogi_cms.cms.user.entity.User;
import lombok.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.management.relation.Role;
import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URLConnection;
import java.time.LocalDateTime;

@Entity
@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = SogiConstant.MAIN_SITE_PREFIX + "_FILE")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO 파일을 등록한 회원을 연결
    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    private String filePath;
    private String fileOriginalName;
    private String fileHashedName;

    private String fileExtension;
    private String fileMimeType;
//    @Enumerated(EnumType.STRING)
//    private MediaType fileMediaType;

    private Long fileSize;

    private LocalDateTime registeredDateTime;

    private Long downloadCount;

    private Long imageWidth;
    private Long imageHeight;

    // 썸네일 연결
    @OneToOne
    @JoinColumn(name = "THUMBNAIL_ID")
    private ThumbnailFile thumbnailFile;

    public void setThumbnailFile(ThumbnailFile thumbnailFile) {
        this.thumbnailFile = thumbnailFile;
    }

    public static File create(MultipartFile multipartFile, java.io.File uploadFile, String uploadDirectory, String hashedFilename) throws IOException {
        String mimeType = URLConnection.guessContentTypeFromName(multipartFile.getOriginalFilename());

        if (mimeType.contains("image")) {
            BufferedImage image = ImageIO.read(uploadFile);

            return File.builder()
                    .filePath(uploadFile.getAbsolutePath())
                    .fileOriginalName(multipartFile.getOriginalFilename())
                    .fileHashedName(hashedFilename)
                    .fileExtension(FilenameUtils.getExtension(multipartFile.getOriginalFilename()))
                    .fileMimeType(mimeType)
                    .fileSize(multipartFile.getSize())
                    .registeredDateTime(LocalDateTime.now())
                    .downloadCount(0L)
                    .imageWidth((long) image.getWidth())
                    .imageHeight((long) image.getHeight())
                    .thumbnailFile(new ThumbnailFile())
                    .build();
        } else {
            return File.builder()
                    .filePath(uploadFile.getAbsolutePath())
                    .fileOriginalName(multipartFile.getOriginalFilename())
                    .fileHashedName(hashedFilename)
                    .fileExtension(FilenameUtils.getExtension(multipartFile.getOriginalFilename()))
                    .fileMimeType(mimeType)
                    .fileSize(multipartFile.getSize())
                    .registeredDateTime(LocalDateTime.now())
                    .downloadCount(0L)
                    .imageWidth(null)
                    .imageHeight(null)
                    .thumbnailFile(null)
                    .build();
        }
    }

    public void addOneDownloadCount() {
        this.downloadCount++;
    }
}