package world.share.audioengine.listener;

import world.share.audioengine.code.PlayState;

/**
 * @author wan
 * 创建日期：2021/12/10
 * 描述：播放状态回调
 */
public interface OnPlayerStateListener {

    /**
     * 播放状态回调
     *
     * @param state 当前播放器状态
     **/
    void play(PlayState state);

}
