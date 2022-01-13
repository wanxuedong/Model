package world.share.user

import com.alibaba.android.arouter.facade.annotation.Route
import world.share.lib_base.constant.RouterUrl
import world.share.lib_base.mvvm.BaseFragment
import world.share.user.databinding.UserFragmentUserBinding
import world.share.user.model.UserViewModel

/**
 * @author wan
 * 创建日期：2021/12/1
 * 描述：我的
 */
@Route(path = RouterUrl.USER_FRAGMENT)
class UserFragment :
    BaseFragment<UserFragmentUserBinding, UserViewModel>() {
    override fun attachView(): Int {
        return R.layout.user_fragment_user
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }
}