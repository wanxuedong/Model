package world.share.main.model

import world.share.lib_base.App
import world.share.lib_base.data.DataRepository
import world.share.lib_base.mvvm.command.BindingCommand
import world.share.lib_base.mvvm.command.BindingConsumer
import world.share.lib_base.mvvm.event.SingleLiveEvent
import world.share.lib_base.mvvm.viewmodel.BaseViewModel

/**
 * @author wan
 * 创建日期：2021/11/29
 * 描述：首页Activity
 */
class MainViewModel(application: App, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {

    val uc = UiChangeEvent()

    inner class UiChangeEvent {
        val tabChangeLiveEvent: SingleLiveEvent<Int> = SingleLiveEvent()
        val pageChangeLiveEvent: SingleLiveEvent<Int> = SingleLiveEvent()
    }

    val onTabSelectedListener: BindingCommand<Int> = BindingCommand(BindingConsumer {
        uc.tabChangeLiveEvent.postValue(it)
    })

    val onPageSelectedListener: BindingCommand<Int> = BindingCommand(BindingConsumer {
        uc.pageChangeLiveEvent.postValue(it)
    })

}