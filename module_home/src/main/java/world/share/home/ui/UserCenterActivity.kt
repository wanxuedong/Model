package world.share.home.ui

import com.alibaba.android.arouter.facade.annotation.Route
import world.share.home.BR
import world.share.home.databinding.ActivityUserCenterBinding
import world.share.home.model.HomeViewModel
import world.share.lib_base.RouterUrl.USER_CENTER_ACTIVITY
import world.share.lib_base.mvvm.BaseActivity

/**
 * @author wan
 * 创建日期：2021/10/15
 * 描述：用户个人中心
 */
@Route(path = USER_CENTER_ACTIVITY)
class UserCenterActivity :
    BaseActivity<ActivityUserCenterBinding, HomeViewModel>() {

    override fun attachView(): Int {
        return world.share.home.R.layout.activity_user_center
    }

    override fun initVariableId(): Int {
        return BR.view_model
    }
}