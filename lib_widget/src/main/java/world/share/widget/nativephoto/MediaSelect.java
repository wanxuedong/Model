package world.share.widget.nativephoto;

import android.net.Uri;

/**
 * @author wan
 * 创建日期：2021/10/13
 * 描述：多媒体文件选择回调
 */
public interface MediaSelect {

    /**
     * 图片选择监听回调
     *
     * @param uri  包含图片信息的uri
     * @param path 已获取的图片路径
     **/
    void onSelectPicture(Uri uri, String path);

    /**
     * 视频选择监听回调
     *
     * @param uri  包含图片信息的uri
     * @param path 已选中的视频路径
     **/
    void onSelectVideo(Uri uri, String path);

}
