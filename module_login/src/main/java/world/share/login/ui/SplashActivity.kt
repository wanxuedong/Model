package world.share.login.ui

import android.text.TextUtils
import com.alibaba.android.arouter.facade.annotation.Route
import world.share.baseutils.MvHelper
import world.share.lib_base.RouterUrl.HOME_ACTIVITY
import world.share.lib_base.RouterUrl.LOGIN_ACTIVITY
import world.share.lib_base.RouterUrl.SPLASH_ACTIVITY
import world.share.lib_base.mvvm.BaseActivity
import world.share.login.BR
import world.share.login.R
import world.share.login.data.MvConstant
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
        if (TextUtils.isEmpty(MvHelper.decodeString(MvConstant.USER_NAME))) {
            jump(LOGIN_ACTIVITY)
            finish()
            return
        }
        MvHelper.encode(MvConstant.USER_NAME, "")
        jump(HOME_ACTIVITY)
        finish()
    }

    override val isUseFullScreenMode: Boolean
        get() = true

    override fun initVariableId(): Int {
        return BR.view_model
    }

}