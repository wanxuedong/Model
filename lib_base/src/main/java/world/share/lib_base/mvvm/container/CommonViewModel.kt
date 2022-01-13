package world.share.lib_base.mvvm.container

import world.share.lib_base.App
import world.share.lib_base.data.DataRepository
import world.share.lib_base.mvvm.viewmodel.BaseViewModel

/**
 * @author wan
 * 创建日期：2021/11/24
 * 描述：容器viewModel
 */
class CommonViewModel(application: App, model: DataRepository) :
    BaseViewModel<DataRepository>(application,model) {
}