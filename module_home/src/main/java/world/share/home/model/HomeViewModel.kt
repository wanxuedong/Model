package world.share.home.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import world.share.lib_base.App
import world.share.lib_base.constant.RouterUrl.AUDIO_PLAYER_ACTIVITY
import world.share.lib_base.data.DataRepository
import world.share.lib_base.mvvm.command.BindingAction
import world.share.lib_base.mvvm.command.BindingCommand
import world.share.lib_base.mvvm.viewmodel.BaseViewModel

class HomeViewModel(application: App, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {
    private val mText: MutableLiveData<String?>
    val text: LiveData<String?>
        get() = mText


    /**
     * 标题栏右图标点击事件 VM层重写setToolbarRightClick()
     */
    var musicOnClick = BindingCommand<Void>(BindingAction {
        startActivity(AUDIO_PLAYER_ACTIVITY)
    })

    init {
        mText = MutableLiveData()
        mText.value = "This is home fragment"
    }
}