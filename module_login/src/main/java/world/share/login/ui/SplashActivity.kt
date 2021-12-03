package world.share.login.ui

import android.text.TextUtils
import world.share.baseutils.MvHelper
import world.share.lib_base.constant.RouterUrl.MAIN_ACTIVITY
import world.share.lib_base.constant.RouterUrl.LOGIN_ACTIVITY
import world.share.lib_base.constant.UserConstant.USER_NAME
import world.share.lib_base.mvvm.BaseActivity
import world.share.login.BR
import world.share.login.R
import world.share.login.databinding.LoginActivitySplashBinding
import world.share.login.model.LoginViewModel

/**
 * @author wan
 * 创建日期：2021/10/15
 * 描述：启动页
 */
class SplashActivity : BaseActivity<LoginActivitySplashBinding, LoginViewModel>() {

    override fun attachView(): Int {
        return R.layout.login_activity_splash
    }

    override fun initData() {
        super.initData()
        if (TextUtils.isEmpty(MvHelper.decodeString(USER_NAME))) {
            jump(LOGIN_ACTIVITY)
            finish()
            return
        }
        MvHelper.encode(USER_NAME, "")
        jump(MAIN_ACTIVITY)
        finish()
    }

    override val isUseFullScreenMode: Boolean
        get() = true

    override fun initVariableId(): Int {
        return BR.viewModel
    }

}