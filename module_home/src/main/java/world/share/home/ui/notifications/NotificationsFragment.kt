package world.share.home.ui.notifications

import world.share.home.BR
import world.share.home.R
import world.share.home.databinding.FragmentNotificationsBinding
import world.share.home.model.NotificationsViewModel
import world.share.lib_base.mvvm.BaseFragment

class NotificationsFragment :
    BaseFragment<FragmentNotificationsBinding, NotificationsViewModel>() {
    override fun attachView(): Int {
        return R.layout.fragment_notifications
    }

    override fun initVariableId(): Int {
        return BR.view_model
    }
}