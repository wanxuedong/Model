package world.share.login.ui

import android.graphics.Color
import com.alibaba.android.arouter.facade.annotation.Route
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import world.share.lib_base.constant.RouterUrl.LOGIN_ACTIVITY
import world.share.lib_base.mvvm.BaseActivity
import world.share.login.BR
import world.share.login.R
import world.share.login.databinding.LoginActivityLoginBinding
import world.share.login.model.LoginViewModel
import world.share.widget.EditTextMonitor

/**
 * @author wan
 * 创建日期：2021/9/10
 * 描述：登录页面
 */
@Route(path = LOGIN_ACTIVITY)
class LoginActivity : BaseActivity<LoginActivityLoginBinding, LoginViewModel>() {

    override fun attachView(): Int {
        return R.layout.login_activity_login
    }

    override fun initData() {
        super.initData()
        val accountSubject = PublishSubject.create<String>()
        val pwdSubject = PublishSubject.create<String>()
        binding.etAccount.addTextChangedListener(EditTextMonitor(accountSubject))
        binding.etPwd.addTextChangedListener(EditTextMonitor(pwdSubject))
        viewModel.addSubscribe(
            Observable.combineLatest(
                accountSubject,
                pwdSubject,
                { account: String, pwd: String ->
                    account.isNotBlank() && pwd.isNotBlank()
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    binding.btnLogin.setTextColor(
                        if (it) Color.parseColor("#ffffff") else Color.parseColor("#858585")
                    )
                    binding.btnLogin.setBackgroundResource(if (it) R.drawable.shape_round_blue else R.drawable.gray_btn_corner_10dp)
                })
    }

    override val isStatusBarTransparent: Boolean
        get() = true

    override fun initVariableId(): Int {
        return BR.viewModel
    }

}