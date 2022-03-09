package world.share.audioplayer

import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import world.share.audioengine.code.PlayState
import world.share.audioplayer.databinding.AudioActivityAudioPlayerBinding
import world.share.audioplayer.model.AudioPlayerModel
import world.share.lib_base.constant.RouterUrl
import world.share.lib_base.mvvm.BaseActivity
import world.share.widget.toast.ToastUtil

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

    override val isStatusBarTransparent: Boolean
        get() = true

    override fun initView() {
        super.initView()
        viewModel.playState.observe(this, Observer {
            when (it) {
                PlayState.ON_LOAD -> {
                    binding.audioPlay.setImageResource(R.drawable.audio_play)
                }
                PlayState.ON_START -> {
                    binding.audioPlay.setImageResource(R.mipmap.audio_pause)
                }
                PlayState.ON_PAUSE -> {
                    binding.audioPlay.setImageResource(R.drawable.audio_play)
                }
            }
        })
    }

    override fun initEvent() {
        super.initEvent()
        viewModel.audioPlayer.setPlayerErrorListener { code, message -> ToastUtil.show("$code : $message") }
        viewModel.audioPlayer.setPlayerStateListener { onPlayerStateListener ->
            ToastUtil.show(onPlayerStateListener.describe)
        }
    }

}