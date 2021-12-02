package world.share.login.model

import androidx.databinding.ObservableField
import androidx.fragment.app.FragmentActivity
import world.share.baseutils.AppManager
import world.share.lib_base.App
import world.share.lib_base.RouterUrl
import world.share.lib_base.bean.UserBean
import world.share.lib_base.data.DataRepository
import world.share.lib_base.internet.bean.BaseResponse
import world.share.lib_base.internet.rxjava.ApiSubscriberHelper
import world.share.lib_base.internet.rxjava.RxThreadHelper
import world.share.lib_base.mvvm.command.BindingAction
import world.share.lib_base.mvvm.command.BindingCommand
import world.share.lib_base.mvvm.command.BindingConsumer
import world.share.lib_base.mvvm.viewmodel.BaseViewModel
import world.share.lib_base.route.RouteCenter
import world.share.widget.toast.ToastBuild
import world.share.widget.toast.ToastClick
import world.share.widget.toast.ToastUtil

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
        RouteCenter.navigate(RouterUrl.REGISTER_ACTIVITY)
        AppManager.instance.finishAllActivity()
    })

    val touristClickCommand: BindingCommand<Void> = BindingCommand(BindingAction {
        RouteCenter.navigate(RouterUrl.HOME_ACTIVITY)
        AppManager.instance.finishAllActivity()
    })

    private fun loginByPwd() {
        if (account.get().isNullOrBlank() || pwd.get().isNullOrBlank()) {
            ToastUtil.materialShow(
                (AppManager.instance.currentActivity() as FragmentActivity?)?.supportFragmentManager,
                ToastBuild(
                    "账号或密码不能为空!"
                ).setToastClick(object : ToastClick() {
                    override fun confirm() {
                        super.confirm()
                        ToastUtil.materialShow(
                            (AppManager.instance.currentActivity() as FragmentActivity?)?.supportFragmentManager,
                            ToastBuild("别点了,快输入吧!")
                        )
                    }
                })
            )
            return
        }
        model.apply {
            userLogin(account.get()!!, pwd.get()!!)
                .compose(RxThreadHelper.rxSchedulerHelper(this@LoginViewModel))
                .doOnSubscribe { }
                .subscribe(object : ApiSubscriberHelper<BaseResponse<UserBean>>() {
                    override fun onSuccess(t: BaseResponse<UserBean>) {
                        if (t.code == 0) {
                            t.result?.let {
                                saveUserData(it)
                            }
                            RouteCenter.navigate(RouterUrl.HOME_ACTIVITY)
                            AppManager.instance.finishAllActivity()
                        }
                    }

                    override fun onError(msg: String?) {
                        ToastUtil.show(msg)
                    }

                })
        }
    }
}