package world.share.home.model

import world.share.lib_base.App
import world.share.lib_base.data.DataRepository
import world.share.lib_base.mvvm.viewmodel.BaseViewModel

/**
 * @author wan
 * 创建日期：2021/11/29
 * 描述：首页Activity
 */
class MainViewModel(application: App, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {
}