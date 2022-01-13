package world.share.audioplayer.model

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import world.share.lib_base.App
import world.share.lib_base.data.DataRepository
import world.share.lib_base.mvvm.viewmodel.BaseViewModel

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

    var seekCurrent = ObservableInt(50)

}