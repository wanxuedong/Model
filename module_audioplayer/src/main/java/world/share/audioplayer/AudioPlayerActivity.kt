package world.share.audioplayer

import android.widget.SeekBar
import com.alibaba.android.arouter.facade.annotation.Route
import world.share.audioengine.code.PlayState
import world.share.audioengine.listener.OnPlayerInfoListener
import world.share.audioplayer.databinding.AudioActivityAudioPlayerBinding
import world.share.audioplayer.model.AudioPlayerModel
import world.share.baseutils.PermissionsUtils
import world.share.lib_base.constant.RouterUrl
import world.share.lib_base.mvvm.BaseActivity
import world.share.widget.toast.ToastUtil
import java.util.*

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
        PermissionsUtils.getStorgePermission(this)
    }

    override fun initEvent() {
        super.initEvent()
        viewModel.audioPlayer.setPlayerErrorListener { code, message -> ToastUtil.show("$code : $message") }
        viewModel.audioPlayer.setPlayerStateListener { state ->
            runOnUiThread {
                when (state) {
                    PlayState.ON_START, PlayState.ON_RESUME, PlayState.LOADING_OVER -> {
                        binding.audioPlay.setImageResource(R.mipmap.audio_play)
                    }
                    PlayState.ON_LOAD, PlayState.ON_PAUSE, PlayState.ON_LOADING -> {
                        binding.audioPlay.setImageResource(R.drawable.audio_pause)
                    }
                    PlayState.ON_STOP, PlayState.ON_DESTROY -> {
                        binding.audioPlay.setImageResource(R.drawable.audio_pause)
                        viewModel.playState.value = PlayState.ON_DESTROY
                    }
                }
            }
        }
        viewModel.audioPlayer.setPlayerProgressListener { playTime ->
            viewModel.seekCurrent.set(playTime.currentTime)
            viewModel.maxProgress.set(playTime.totalTime)
            viewModel.progress.set(playTime.toString())
        }
        binding.progressIcon.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.progress?.let { viewModel.audioPlayer.seek(it) }
            }
        })
    }

}