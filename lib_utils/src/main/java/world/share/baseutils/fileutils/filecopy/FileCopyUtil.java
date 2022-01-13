package world.share.baseutils.fileutils.filecopy;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author wan
 * 创建日期：2021/10/12
 * 描述：文件复制工具
 */
public class FileCopyUtil {

    /**
     * 复制文件夹
     *
     * @param src          目标文件夹
     * @param dest         目的地文件夹
     * @param copyProgress 拷贝进度监听
     *                     指定目标文件夹和目的地文件夹，会将目标文件夹下的文件全部复制到目的地文件夹下
     */
    public static boolean copyDirectory(File src, File dest, CopyProgress copyProgress) {
        if (!src.isDirectory()) {
            return false;
        }
        if (!dest.isDirectory() && !dest.mkdirs()) {
            return false;
        }

        File[] files = src.listFiles();
        for (File file : files) {
            File destFile = new File(dest, file.getName());
            if (file.isFile()) {
                if (!copyFile(file, destFile, copyProgress)) {
                    return false;
                }
            } else if (file.isDirectory()) {
                if (!copyDirectory(file, destFile, copyProgress)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 复制文件
     *
     * @param src          目标文件
     * @param des          目的地文件
     * @param copyProgress 拷贝进度监听
     *                     指定目标文件和目的地文件，会将目标文件下的内容复制到目的地文件内
     */
    public static boolean copyFile(File src, File des, CopyProgress copyProgress) {
        if (!src.exists()) {
            Log.e("cppyFile", "file not exist:" + src.getAbsolutePath());
            return false;
        }
        if (!des.getParentFile().isDirectory() && !des.getParentFile().mkdirs()) {
            Log.e("cppyFile", "mkdir failed:" + des.getParent());
            return false;
        }
        long fileSize = getFileSize(src);
        long progress = 0;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(src));
            bos = new BufferedOutputStream(new FileOutputStream(des));
            byte[] buffer = new byte[4 * 1024];
            int count;
            while ((count = bis.read(buffer, 0, buffer.length)) != -1) {
                if (count > 0) {
                    bos.write(buffer, 0, count);
                    progress += count;
                }
                if (copyProgress != null) {
                    copyProgress.progress(fileSize, progress, String.format("%.2f", (progress + 0.0f) / fileSize));
                }
            }
            bos.flush();
            return true;
        } catch (Exception e) {
            Log.e("copyFile", "exception:", e);
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 获取指定文件大小
     *
     * @return 返回文件大小
     */
    private static long getFileSize(File file) {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                size = fis.available();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }

}
