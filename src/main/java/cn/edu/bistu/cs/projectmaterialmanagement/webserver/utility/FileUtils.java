package cn.edu.bistu.cs.projectmaterialmanagement.webserver.utility;

import cn.edu.bistu.cs.projectmaterialmanagement.webserver.config.ConfigConstant;
import com.google.gson.Gson;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    //删除文件夹
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir
                        (new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        //   System.out.println("目录已被删除！");
        //System.out.println("目录删除失败！");
        return dir.delete();
    }

    public static boolean deleteFile(File file) {

        //   System.out.println("目录已被删除！");
        //System.out.println("目录删除失败！");
        return file.delete();
    }

    public static boolean MoveFile(String src,
                                   String dest) {
        try {
            Files.move(Path.of(src), Path.of(dest));
        } catch (Exception e) {
            return false;
        }
        return true;

    }

    /**
     * 复制目录
     *
     * @param dest 目的文件夹，如果不存在，则创建
     * @param src  源文件
     * @return initDestDir 是否在拷贝前对Dest文件夹清空
     * @throws IOException
     */

    public static boolean copyDir(String dest,
                                  String src,
                                  boolean initDestDir)
            throws IOException {
        File destFile = new File(dest);
        File srcFile = new File(src);

        if (initDestDir) {
            deleteDir(destFile);

            createDir(dest);
        } else
            createDir(dest);
        //  createDir(src);

        Path srcPath = srcFile.toPath();
        Path destPath = destFile.toPath();

        Files.walk(srcPath)
             .forEach(source -> {
                 try {
                     Files.copy(source, destPath.resolve(srcPath.relativize(source)),
                                StandardCopyOption.REPLACE_EXISTING);
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             });

        return true;

    }

    public static boolean copyFile(String dest,
                                   String src) {
        try {
            Files.copy(Path.of(src), Path.of(dest), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 列出文件清单
     *
     * @param filePath 磁盘文件路径
     * @return
     */
    public static List<String> listFiles(String filePath
    ) {
        List<String> fileArr = new ArrayList<>();
        File[] files = new File(filePath).listFiles();
        if (files != null && files.length > 0)
            for (int k = 0; k < files.length; k++) {
                if (files[k].isDirectory()) {
                    //listFiles(files[k].getPath(), fileArr);
                } else if (!files[k].isDirectory()) {
                    fileArr.add(files[k].getName());
                }
            }
        if (fileArr.size() > 0)
            return fileArr;
        return null;
    }

    public static boolean isFileExist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }


    /**
     * 创建目录
     *
     * @param destDir 目标文件夹
     * @return boolean
     */
    public static boolean createDir(String destDir) {
        File dir = new File(destDir);
        if (dir.exists()) {
            //  System.out.println("创建目录" + destDir + "失败，目标目录已经存在");
            return false;
        }
        if (!destDir.endsWith(File.separator)) {
            destDir = destDir + File.separator;
        }
        //创建目录
        // System.out.println("创建目录" + destDir + "成功！");
        // System.out.println("目录已经存在,创建目录" + destDir + "失败！");
        return dir.mkdirs();
    }

    /**
     * 返回文件路径，如果参数isTempDir为True，则返回临时文件路径
     * 返回格式为：/upload/id/或/upload/temp/id/
     *
     * @param id
     * @param isTempDir 是否是临时文件夹
     * @return
     */
    public static String getFilePath(String id,
                                     boolean isTempDir) {
        if (id != null && !id.isEmpty())
            return (isTempDir ? ConfigConstant.FilePathTemp : ConfigConstant.FilePath) + File.separator + id + File.separator;
        return (isTempDir ? ConfigConstant.FilePathTemp : ConfigConstant.FilePath) + File.separator;
    }

    /**
     * 将文件上传到uploadFileNameId文件夹
     *
     * @param uploadFileNameId
     * @param multipartFile
     * @return
     */

    public static String addAdditionalFile(String uploadFileNameId,
                                           MultipartFile multipartFile,
                                           boolean isTempDir) {

        //文件夹
        String guid = null;
        if (uploadFileNameId == null || uploadFileNameId.isEmpty())
            guid = GUID.getGUID();
        else
            guid = uploadFileNameId;


        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = multipartFile.getOriginalFilename();

            File file = new File(
                    (isTempDir ? ConfigConstant.FilePathTemp : ConfigConstant.FilePath) + File.separator + guid,
                    fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            try {
                multipartFile.transferTo(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return guid;
    }


    public static String addFile(String uploadFileNameId,
                                 MultipartFile multipartFile) {

        //文件夹
        String guid = null;
        if (uploadFileNameId == null || uploadFileNameId.isEmpty())
            guid = GUID.getGUID();
        else
            guid = uploadFileNameId;

        String suffix = multipartFile.getOriginalFilename().substring(
                multipartFile.getOriginalFilename().lastIndexOf(".") + 1
        );

        String fileName = guid + "." + suffix;
        if (multipartFile != null && !multipartFile.isEmpty()) {


            File file = new File((ConfigConstant.FilePath) + File.separator, fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            try {
                multipartFile.transferTo(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return fileName;
    }

    /**
     * 删除文件
     *
     * @param uploadFileNameId
     * @param fileName
     * @return
     */

    public static boolean deleteAdditionalFile(String uploadFileNameId,
                                               String fileName,
                                               boolean isTempDir) {
        return FileUtils.deleteFile(new File(
                (isTempDir ? ConfigConstant.FilePathTemp : ConfigConstant.FilePath) + File.separator + uploadFileNameId + File.separator + fileName));

    }


    /**
     * 根据目录获得该目录下的文件列表
     *
     * @param additionalFilesDir
     * @return
     */
    public static String getAdditionalFiles(String additionalFilesDir) {
        if (additionalFilesDir != null && additionalFilesDir.length() > 0) {
            List<String> list = FileUtils.listFiles(ConfigConstant.FilePath + File.separator + additionalFilesDir);

            if (list != null && list.size() > 0) {
                Gson gson = new Gson();
                return gson.toJson(list);
            } else {
                FileUtils.deleteDir(new File(ConfigConstant.FilePath + File.separator + additionalFilesDir));
            }
        }
        return null;
    }


}
