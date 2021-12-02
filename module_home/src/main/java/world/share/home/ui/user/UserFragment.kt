package world.share.home.ui.user

import world.share.home.BR
import world.share.home.R
import world.share.home.databinding.FragmentUserBinding
import world.share.home.model.UserViewModel
import world.share.lib_base.mvvm.BaseFragment

/**
 * @author wan
 * 创建日期：2021/12/1
 * 描述：我的
 */
class UserFragment :
    BaseFragment<FragmentUserBinding, UserViewModel>() {
    override fun attachView(): Int {
        return R.layout.fragment_user
    }

    override fun initVariableId(): Int {
        return BR.view_model
    }
}