package world.share.widget.photoselector;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * @author wan
 * 创建日期：2021/10/12
 * 描述：多媒体文件选择回调
 */
public interface MediaCall {

    /**
     * 图片选择监听回调
     *
     * @param pictures 已选中的图片
     **/
    void onCallPicture(List<LocalMedia> pictures);

    /**
     * 视频选择监听回调
     *
     * @param videos 已选中的视频
     **/
    void onCallVideo(List<LocalMedia> videos);

}
