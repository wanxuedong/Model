package world.share.video.player.listener;

import world.share.video.player.bean.WlTimeInfoBean;

/**
 * @author wanxuedong
 * 不断返回总时长和当前播放进度时长
 **/
public interface WlOnTimeInfoListener {

    void onTimeInfo(WlTimeInfoBean timeInfoBean);

}

