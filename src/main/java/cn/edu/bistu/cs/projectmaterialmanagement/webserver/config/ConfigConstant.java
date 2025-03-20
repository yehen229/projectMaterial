package cn.edu.bistu.cs.projectmaterialmanagement.webserver.config;

public class ConfigConstant {
    //Windows平台下路径
    //题目中的文件附件路径
   // private final static String FILE_PATH_WINDOWS = "D:\\AAAproject\\vue\\temp";
   // private final static String FILE_PATH_TEMP_WINDOWS = "D:\\AAAproject\\vue\\temp";

    private final static String FILE_PATH_WINDOWS = "D:\\研究生\\研究生\\项目\\project\\temp";
//    private final static String FILE_PATH_WINDOWS = "D:\\AAAproject\\Java\\MaterialManage1\\temp";
    private final static String FILE_PATH_TEMP_WINDOWS = "D:\\研究生\\研究生\\项目\\project\\temp";
//    private final static String FILE_PATH_TEMP_WINDOWS = "D:\\AAAproject\\Java\\MaterialManage1\\temp";


    //题目中的图片等路径，用于Markdown中
    private final static String FILE_FRONT_PATH_WINDOWS = "C:\\Users\\hbs\\own\\Project\\MaterialManagefiles\\frontend\\public\\static\\";

    //Linux平台下路径
    private final static String FILE_PATH_LINUX = "/www/wwwroot/webserver/temp";
    private final static String FILE_PATH_TEMP_LINUX = "/www/wwwroot/webserver/temp";
    private final static String FILE_FRONT_PATH_LINUX = "/var/www/MaterialManagefiles/html/static/";
    public static String FilePath = "";
    public static String FilePathTemp = "";
    public static String FileFrontPath = "";
    public static String PythonCmd = "python";

    public static int Python_Timeout_Second = 180;//单位：秒
    public static boolean windows = false;

    static {
        String os = System.getProperty("os.name").toLowerCase();
        windows = os.contains("windows");

        if (windows) {
            FilePath = FILE_PATH_WINDOWS;
            FilePathTemp = FILE_PATH_TEMP_WINDOWS;
            FileFrontPath = FILE_FRONT_PATH_WINDOWS;
        } else {
            FilePath = FILE_PATH_LINUX;
            FilePathTemp = FILE_PATH_TEMP_LINUX;
            FileFrontPath = FILE_FRONT_PATH_LINUX;
            PythonCmd = "python3";
        }
    }


    private void isWindows() {
        String os = System.getProperty("os.name");
        windows = os.contains("windows");

        if (windows) {
            FilePath = FILE_PATH_WINDOWS;
            FileFrontPath = FILE_FRONT_PATH_WINDOWS;
        } else {
            FilePath = FILE_PATH_LINUX;
            FileFrontPath = FILE_FRONT_PATH_LINUX;
        }
    }

    /*

    private void isLinux() {
        String os = System.getProperty("os.name");
        linux = !os.contains("linux");
    }*/


}
