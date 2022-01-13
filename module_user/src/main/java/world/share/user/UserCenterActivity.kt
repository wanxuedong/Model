package world.share.user

import com.alibaba.android.arouter.facade.annotation.Route
import world.share.lib_base.constant.RouterUrl.USER_CENTER_ACTIVITY
import world.share.lib_base.mvvm.BaseActivity
import world.share.user.databinding.UserActivityUserCenterBinding
import world.share.user.model.UserViewModel

/**
 * @author wan
 * 创建日期：2021/10/15
 * 描述：用户个人中心
 */
@Route(path = USER_CENTER_ACTIVITY)
class UserCenterActivity :
    BaseActivity<UserActivityUserCenterBinding, UserViewModel>() {

    override fun attachView(): Int {
        return R.layout.user_activity_user_center
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }
}