package world.share.logger;

import android.os.Environment;

import java.io.File;

/***
 * 文件存储序列化对象的工具类
 *
 * @author mac**/

public class LogFileUtil {

    private static String present = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Serializable" + File.separator;

    /**
     * 检查本地文件是否存在
     **/
    private static boolean checkFile(String filePath) {
        File dirFirstFile = new File(present);
        if (!dirFirstFile.exists()) {
            return false;
        }
        File file = new File(present, filePath);
        if (!file.exists()) {
            return false;
        }
        return true;
    }


    /**
     * 删除文件夹
     **/
    public static void delFolder(String folderPath) {
        try {
            //删除完里面所有内容
            delAllFile(folderPath);
            String filePath = folderPath;
            File myFilePath = new File(filePath);
            //删除空文件夹
            myFilePath.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除指定文件夹下的所有文件
     **/
    private static boolean delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return false;
        }
        if (!file.isDirectory()) {
            return false;
        }
        String[] tempList = file.list();
        File temp;
        if (tempList == null || tempList.length == 0) {
            return false;
        }
        for (String str : tempList) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + str);
            } else {
                temp = new File(path + File.separator + str);
            }
            if (temp.isFile()) {
                boolean isDelete = temp.delete();
                if (!isDelete) {
                    LogUtil.e("FileUtil", "Method delAllFile isDelete File fail!");
                }
            }
            if (temp.isDirectory()) {
                //先删除文件夹里面的文件
                delAllFile(path + "/" + str);
                //再删除空文件夹
                delFolder(path + "/" + str);
            }
        }
        return true;
    }
}

