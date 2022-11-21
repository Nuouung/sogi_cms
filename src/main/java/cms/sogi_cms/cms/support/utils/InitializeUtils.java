package cms.sogi_cms.cms.support.utils;

public class InitializeUtils {
    public static boolean isDataInit(String ddlMode) {
        return ddlMode.equals("create") || ddlMode.equals("create-drop");
    }
}
