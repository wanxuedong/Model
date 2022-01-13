package world.share.widget.nativephoto;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import java.io.File;
import java.lang.ref.SoftReference;

/**
 * @author wan
 * 创建日期：2021/10/13
 * 描述：原生实现多媒体文件的生产和获取，每次只能选择/生产和获取一个图片或一个视频
 * 使用三步曲
 * 一：设置回调监听
 * PhotoSelector.getInstance().setMediaCall(this);
 * 二：设置回调处理
 * PhotoSelector.getInstance().onActivityResult(requestCode, resultCode, data);
 * 三：调用打开多媒体方法，等待回调
 */
public class MediaControl {

    /**
     * 相册选择图片
     **/
    private final int SELECT_PICTURE_ALBUM = 20000;

    /**
     * 拍照获取图片
     **/
    private final int SELECT_PICTURE_CAMERA = 20001;

    /**
     * 相册选择视频
     **/
    private final int SELECT_VIDEO_ALBUM = 20002;

    /**
     * 拍照获取视频
     **/
    private final int SELECT_VIDEO_CAMERA = 20003;

    /**
     * 生产的图片uri
     **/
    private Uri imageUri;

    /**
     * 多媒体选择监听回调
     **/
    private SoftReference<MediaSelect> mediaSelect;

    private static MediaControl instance;

    public static MediaControl getInstance() {
        if (instance == null) {
            synchronized (MediaControl.class) {
                if (instance == null) {
                    instance = new MediaControl();
                }
            }
        }
        return instance;
    }

    /**
     * 设置多媒体选择监听
     *
     * @param mediaSelect 监听回调
     **/
    public void setMediaSelect(MediaSelect mediaSelect) {
        this.mediaSelect = new SoftReference<>(mediaSelect);
    }

    /**
     * 使用相机拍照图片
     *
     * @param activity   上下文
     * @param parentPath 创建的多媒体文件父级文件路径，形如file或file/test,不传默认sdcard根目录
     * @param fileName   创建的多媒体文件名称，不传默认名称为:uuid + 时间戳 + 文件类型后缀
     **/
    public void selectPictureOnCamera(Activity activity, String parentPath, String fileName) {
        //打开相机的Intent
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //如果没有相机则该应用不会闪退，要是不加这句则当系统没有相机应用的时候该应用会闪退
        if (takePhotoIntent.resolveActivity(activity.getPackageManager()) != null) {
            //创建用来保存照片的文件
            File imageFile = MediaProduct.createImageFile(activity, parentPath, fileName);
            if (imageFile != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    /*7.0以上要通过FileProvider将File转化为Uri*/
                    imageUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileProvider", imageFile);
                } else {
                    /*7.0以下则直接使用Uri的fromFile方法将File转化为Uri*/
                    imageUri = Uri.fromFile(imageFile);
                }
                //将用于输出的文件Uri传递给相机
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                //打开相机
                activity.startActivityForResult(takePhotoIntent, SELECT_PICTURE_CAMERA);
            }
        }
    }

    /**
     * 使用相机录制视频
     *
     * @param activity   上下文
     * @param parentPath 创建的多媒体文件父级文件路径，形如file或file/test,不传默认sdcard根目录
     * @param fileName   创建的多媒体文件名称，不传默认名称为:uuid + 时间戳 + 文件类型后缀
     **/
    public void selectVideoOnCamera(Activity activity, String parentPath, String fileName) {
        Intent intent = new Intent();
        intent.setAction("android.media.action.VIDEO_CAPTURE");
        intent.addCategory("android.intent.category.DEFAULT");
        //保存照片到指定的路径
        //创建用来保存照片的文件
        File videoFile = MediaProduct.createVideoFile(activity, parentPath, fileName);
        Uri uri = Uri.fromFile(videoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent, SELECT_VIDEO_CAMERA);
    }

    /**
     * 打开相册获取图片
     **/
    public void selectPictureOnAlbum(Activity activity) {
        selectMediaOnAlbum(activity, 1, SELECT_PICTURE_ALBUM);
    }

    /**
     * 打开相册获取视频
     *
     * @param activity 上下文
     **/
    public void selectVideoOnAlbum(Activity activity) {
        selectMediaOnAlbum(activity, 2, SELECT_VIDEO_ALBUM);
    }

    /**
     * 打开相册获取 图片/视频
     *
     * @param activity    上下文
     * @param type        文件类型，1图片，2视频
     * @param requestCode 请求标识
     **/
    private void selectMediaOnAlbum(Activity activity, int type, int requestCode) {
        if (Build.VERSION.SDK_INT >= 30) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activity.startActivityForResult(intent, SELECT_VIDEO_ALBUM);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK);
            if (type == 1) {
                intent.setType("image/*");
            } else {
                intent.setType("video/*");
            }
            intent = Intent.createChooser(intent, null);
            activity.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 多媒体选择回调
     **/
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && mediaSelect.get() != null) {
            switch (requestCode) {
                case SELECT_PICTURE_ALBUM:
                    mediaSelect.get().onSelectPicture(data.getData(), AnalysisPathFromUri.getRealPathFromUri(activity, data.getData()));
                    break;
                case SELECT_PICTURE_CAMERA:
                    if (imageUri != null) {
                        mediaSelect.get().onSelectPicture(imageUri, imageUri.getPath());
                    }
                    break;
                case SELECT_VIDEO_ALBUM:
                    //相册获取视频
                case SELECT_VIDEO_CAMERA:
                    //拍照获取视频
                    if (data != null) {
                        mediaSelect.get().onSelectVideo(data.getData(), AnalysisPathFromUri.getRealPathFromUri(activity, data.getData()));
                    }
                    break;
                default:
                    break;
            }
        }
    }


}
