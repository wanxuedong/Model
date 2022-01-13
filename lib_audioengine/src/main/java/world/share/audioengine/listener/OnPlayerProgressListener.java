package world.share.audioengine.listener;

import world.share.audioengine.bean.PlayTime;

/**
 * @author wan
 * 创建日期：2021/12/11
 * 描述：播放进度回调
 */
public interface OnPlayerProgressListener {

    void progress(PlayTime playTime);

}
