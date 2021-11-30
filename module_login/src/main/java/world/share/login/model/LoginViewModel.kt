package world.share.login.model

import androidx.databinding.ObservableField
import world.share.baseutils.AppManager
import world.share.lib_base.App
import world.share.lib_base.RouterUrl
import world.share.lib_base.data.DataRepository
import world.share.lib_base.mvvm.command.BindingAction
import world.share.lib_base.mvvm.command.BindingCommand
import world.share.lib_base.mvvm.command.BindingConsumer
import world.share.lib_base.mvvm.viewmodel.BaseViewModel
import world.share.lib_base.route.RouteCenter

/**
 * @author wan
 * 创建日期：2021/11/24
 * 描述：登陆数据
 */
open class LoginViewModel(application: App, model: DataRepository) :
    BaseViewModel<DataRepository>(application, model) {

    var account = ObservableField("")
    var pwd = ObservableField("")

    val onAccountChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        account.set(it)
    })

    val onPwdChangeCommand: BindingCommand<String> = BindingCommand(BindingConsumer {
        pwd.set(it)
    })

    var btnLoginClick: BindingCommand<Any> = BindingCommand(BindingAction {
        loginByPwd()
    })

    val registerClickCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        startContainerActivity(RouterUrl.LOGIN_ACTIVITY)
    })

    val touristClickCommand:BindingCommand<Void> = BindingCommand(BindingAction {
        RouteCenter.navigate(RouterUrl.HOME_ACTIVITY)
        AppManager.instance.finishAllActivity()
    })

    private fun loginByPwd() {
    }
}