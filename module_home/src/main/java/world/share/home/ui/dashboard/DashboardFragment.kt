package world.share.home.ui.dashboard

import world.share.home.BR
import world.share.home.R
import world.share.home.databinding.FragmentDashboardBinding
import world.share.home.model.DashboardViewModel
import world.share.lib_base.mvvm.BaseFragment

class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel>() {

    override fun attachView(): Int {
        return R.layout.fragment_dashboard
    }

    override fun initVariableId(): Int {
        return BR.view_model
    }
}