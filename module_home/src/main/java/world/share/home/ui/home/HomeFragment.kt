package world.share.home.ui.home

import android.os.Bundle
import world.share.home.BR
import world.share.home.R
import world.share.home.databinding.FragmentHomeBinding
import world.share.home.model.HomeViewModel
import world.share.lib_base.mvvm.BaseFragment
import world.share.lib_base.widget.boommenu.BoomMenuFactory

/**
 * @author wan
 * 创建日期：2021/12/1
 * 描述：首页
 */
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun attachView(): Int {
        return R.layout.fragment_home
    }

    override fun initVariableId(): Int {
        return BR.view_model
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        //初始化悬浮弹出框
        activity?.let { BoomMenuFactory.createHomeButton(binding.boomMenu, it) }
    }

}