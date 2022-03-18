package world.share.audioplayer.model

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import world.share.audioengine.AudioPlayer
import world.share.audioengine.code.PlayState
import world.share.audioplayer.R
import world.share.lib_base.App
import world.share.lib_base.constant.RouterUrl
import world.share.lib_base.data.DataRepository
import world.share.lib_base.mvvm.command.BindingAction
import world.share.lib_base.mvvm.command.BindingCommand
import world.share.lib_base.mvvm.viewmodel.BaseViewModel
import world.share.lib_base.route.RouteCenter
import world.share.widget.toast.ToastUtil

/**
 * @author wan
 * 创建日期：2021/12/30
 * 描述：音频播放模型
 */
class AudioPlayerModel(application: App, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {

    var title = ObservableField("你不是真正的快乐")

    var current = ObservableField("00:00")

    var progress = ObservableField("00:00")

    var seekCurrent = ObservableField(0)

    var maxProgress = ObservableField(100)

    private val musicList =
//        mutableListOf<String>("https://media.w3.org/2010/05/sintel/trailer.mp4")
//        mutableListOf<String>("http://downsc.chinaz.net/files/download/sound1/201206/1638.mp3")
        mutableListOf<String>("sdcard/Download/WeiXin/陈一发儿.mp3")

    /**
     * 当前播放状态
     * **/
    var playState: MutableLiveData<PlayState> = MutableLiveData(PlayState.ON_LOAD)

    /**
     * 当前播放状态图片
     * **/
    var audioStateShow = R.mipmap.audio_play

    val quitClickCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        RouteCenter.navigate(RouterUrl.MAIN_ACTIVITY)
    })

    val switchLastSong: BindingCommand<Void> = BindingCommand(BindingAction {
        ToastUtil.show("上一首")
    })

    val playOrPauseMusic: BindingCommand<Void> = BindingCommand(BindingAction {
        when (playState.value) {
            PlayState.ON_LOAD, PlayState.ON_DESTROY -> {
                playState.value = PlayState.ON_START
                audioPlayer.setSource(musicList[0])
                audioPlayer.start()
            }
            PlayState.ON_START -> {
                playState.value = PlayState.ON_PAUSE
                audioPlayer.pause()
            }
            PlayState.ON_PAUSE -> {
                playState.value = PlayState.ON_START
                audioPlayer.resume()
            }
        }
    })

    val switchNextSong: BindingCommand<Void> = BindingCommand(BindingAction {
        ToastUtil.show("下一首")
    })

    val audioPlayer: AudioPlayer = AudioPlayer()

}