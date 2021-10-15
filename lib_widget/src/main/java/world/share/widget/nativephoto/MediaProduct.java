package world.share.widget.nativephoto;

import android.app.Activity;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author wan
 * 创建日期：2021/10/13
 * 描述：用于指定多媒体生产路径和名称
 */
public class MediaProduct {

    /**
     * 创建用来存储图片的文件
     *
     * @param activity   上下文
     * @param parentPath 创建的多媒体文件父级文件路径，形如file或file/test,不传默认sdcard根目录 + package
     * @param fileName   创建的多媒体文件名称，不传默认名称为:uuid + 时间戳 + 文件类型后缀
     */
    public static File createImageFile(Activity activity, String parentPath, String fileName) {
        return createFile(activity, parentPath, fileName, ".jpg");
    }

    /**
     * 创建用来存储视频的文件
     *
     * @param activity   上下文
     * @param parentPath 创建的多媒体文件父级文件路径，形如file或file/test,不传默认sdcard根目录 + package
     * @param fileName   创建的多媒体文件名称，不传默认名称为:uuid + 时间戳 + 文件类型后缀
     */
    public static File createVideoFile(Activity activity, String parentPath, String fileName) {
        return createFile(activity, parentPath, fileName, ".mp4");
    }

    /**
     * 创建用来存储多媒体的文件
     *
     * @param activity   上下文
     * @param parentPath 创建的多媒体文件父级文件路径，形如file或file/test,不传默认sdcard根目录 + package
     * @param fileName   创建的多媒体文件名称，不传默认名称为:uuid + 时间戳 + 文件类型后缀
     * @param suffix     创建的多媒体文件后缀
     */
    private static File createFile(Activity activity, String parentPath, String fileName, String suffix) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        if (TextUtils.isEmpty(fileName)) {
            fileName = uuid + "_" + timeStamp + "_";
        }
        String storagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + activity.getPackageName() + File.separator;
        if (!TextUtils.isEmpty(parentPath)) {
            storagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + activity.getPackageName() + File.separator + parentPath + File.separator;
        }
        File presenterFile = new File(storagePath);
        if (!presenterFile.exists()) {
            presenterFile.mkdirs();
        }
        File file = null;
        try {
            file = File.createTempFile(fileName, suffix, presenterFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

}
