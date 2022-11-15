package cms.sogi_cms.cms.support.utils;

import java.io.File;
import java.util.List;

/**
 * org.apache.commons.io.FilenameUtils 참고
 * 가져온 것도 있고, 만든 것도 있다!
 */
public class FileUtils {

    private static final char EXTENSION_SEPARATOR = '.';

    private static final char UNIX_SEPARATOR = '/';
    private static final char WINDOWS_SEPARATOR = '\\';

    // 운영체제가 어떤 구분자를 쓰는지 반환한다.
    private static final char SYSTEM_SEPARATOR = File.separatorChar;

    static boolean isSystemWindows() {
        return SYSTEM_SEPARATOR == WINDOWS_SEPARATOR;
    }

    public static char getSystemSeparator() {
        return SYSTEM_SEPARATOR;
    }

    public static String getExtension(String filename) {
        if (filename == null) {
            return null;
        }

        int index = indexOfExtension(filename);
        if (index == -1) {
            return "";
        }

        return filename.substring(index + 1);
    }

    public static int indexOfExtension(String filename) {
        if (filename == null) {
            return -1;
        }

        int extensionPosition = filename.lastIndexOf(EXTENSION_SEPARATOR);
        int lastSeparator = indexOfLastSeparator(filename);

        // 잘 모르겠네
        return lastSeparator > extensionPosition ? -1 : extensionPosition;
    }

    public static int indexOfLastSeparator(String filename) {
        if (filename == null) {
            return -1;
        }

        // 둘 중 하나는 -1이 나올 것. 혹은 둘 다 -1일지도.
        // ex. filename = helloVideo.mp4 <- -1, -1
        // ex. filename = Desktop\helloVideo.mp4 <- -1, 7
        int lastUnixPosition = filename.lastIndexOf(UNIX_SEPARATOR);
        int lastWindowsPosition = filename.lastIndexOf(WINDOWS_SEPARATOR);

        return Math.max(lastUnixPosition, lastWindowsPosition);
    }

    public static boolean isUploadPossible(String filename, List<String> uploadWhiteList) {
        String extension = getExtension(filename);
        for (String s : uploadWhiteList) {
            if (extension.equals(s)) {
                return true;
            }
        }

        return false;
    }
}
