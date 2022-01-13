package world.share.audioplayer

import com.alibaba.android.arouter.facade.annotation.Route
import world.share.audioplayer.databinding.AudioActivityAudioPlayerBinding
import world.share.audioplayer.model.AudioPlayerModel
import world.share.lib_base.constant.RouterUrl
import world.share.lib_base.mvvm.BaseActivity

/**
 * @author wan
 * 创建日期：2021/12/30
 * 描述：音频播放页
 */
@Route(path = RouterUrl.AUDIO_PLAYER_ACTIVITY)
class AudioPlayerActivity :
    BaseActivity<AudioActivityAudioPlayerBinding, AudioPlayerModel>() {
    override fun attachView(): Int {
        return R.layout.audio_activity_audio_player;
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override val isUseFullScreenMode: Boolean
        get() = true

}