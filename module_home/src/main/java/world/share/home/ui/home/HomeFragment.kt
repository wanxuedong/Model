package world.share.home.ui.home

import world.share.home.R
import world.share.home.databinding.FragmentHomeBinding
import world.share.home.BR
import world.share.home.model.HomeViewModel
import world.share.lib_base.mvvm.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun attachView(): Int {
        return R.layout.fragment_home
    }

    override fun initVariableId(): Int {
        return BR.view_model
    }
}