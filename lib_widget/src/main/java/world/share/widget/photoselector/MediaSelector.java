package world.share.widget.photoselector;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.entity.LocalMedia;

import java.lang.ref.SoftReference;
import java.util.List;

import world.share.baseutils.PermissionsUtils;

/**
 * @author wan
 * 创建日期：2021/10/12
 * 描述：基于io.github.lucksiege:pictureselector库实现多媒体选择全局控制，实现1到多图片/视频的选择和生产
 * 使用三步曲
 * 一：设置回调监听
 * PhotoSelector.getInstance().setMediaCall(this);
 * 二：设置回调处理
 * PhotoSelector.getInstance().onActivityResult(requestCode, resultCode, data);
 * 三：调用打开多媒体方法，等待回调
 */
public class MediaSelector {

    /**
     * 选择图片的请求
     **/
    private final int REQUEST_PICTURE = 10000;

    /**
     * 选择视频的请求
     **/
    private final int REQUEST_VIDEO = 10001;

    private static MediaSelector instance;

    /**
     * 多媒体选择监听回调
     **/
    private SoftReference<MediaCall> mediaCall;

    public static MediaSelector getInstance() {
        if (instance == null) {
            synchronized (MediaSelector.class) {
                if (instance == null) {
                    instance = new MediaSelector();
                }
            }
        }
        return instance;
    }

    /**
     * 设置多媒体选择监听
     **/
    public void setMediaCall(MediaCall mediaCall) {
        this.mediaCall = new SoftReference<>(mediaCall);
    }

    /**
     * 打开相册的图片
     *
     * @param size 可以选择图片的数量
     **/
    public void openPictureOnAlbum(Activity activity, int size) {
        openMedia(activity, 0, PictureMimeType.ofImage(), size, new PictureSelectorEngine(), REQUEST_PICTURE);
    }

    /**
     * 打开相机拍照
     **/
    public void openPictureOnCamera(Activity activity) {
        openMedia(activity, 1, PictureMimeType.ofImage(), 0, new PictureSelectorEngine(), REQUEST_PICTURE);
    }

    /**
     * 打开相册的视频
     *
     * @param size 可以选择视频的数量
     **/
    public void openVideoOnAlbum(Activity activity, int size) {
        openMedia(activity, 0, PictureMimeType.ofVideo(), size, new PictureSelectorEngine(), REQUEST_VIDEO);
    }

    /**
     * 打开相机录制
     **/
    public void openVideoOnCamera(Activity activity) {
        openMedia(activity, 1, PictureMimeType.ofVideo(), 0, new PictureSelectorEngine(), REQUEST_VIDEO);
    }

    /**
     * 打开多媒体
     *
     * @param activity      上下文
     * @param openType      打开媒体库的方式，0 相册，1 相机
     * @param pictureConfig 选择的媒体文件类型，包括图片，视频，全部
     * @param size          最多选择文件的数量
     * @param engine        加载图片的引擎
     * @param requestCode   请求标识
     **/
    private void openMedia(Activity activity, int openType, int pictureConfig, int size, ImageEngine engine, int requestCode) {
        if (openType == 0) {
            if (PermissionsUtils.getStorgePermission(activity)) {
                PictureSelector.create(activity)
                        .openGallery(pictureConfig)
                        .maxSelectNum(size)
                        .imageEngine(engine)
                        .isCamera(false)
                        .forResult(requestCode);
            }
        } else {
            if (PermissionsUtils.getCameraPermission(activity)) {
                PictureSelector.create(activity)
                        .openCamera(pictureConfig)
                        .imageEngine(engine)
                        .forResult(requestCode);
            }
        }
    }

    /**
     * 多媒体选择回调
     **/
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && mediaCall.get() != null) {
            switch (requestCode) {
                case REQUEST_PICTURE:
                    List<LocalMedia> pictureList = PictureSelector.obtainMultipleResult(data);
                    mediaCall.get().onCallPicture(pictureList);
                    break;
                case REQUEST_VIDEO:
                    List<LocalMedia> photoList = PictureSelector.obtainMultipleResult(data);
                    mediaCall.get().onCallVideo(photoList);
                    break;
                default:
                    break;
            }
        }
    }

}
