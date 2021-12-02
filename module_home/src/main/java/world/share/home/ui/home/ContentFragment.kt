package world.share.home.ui.home

import world.share.home.R
import world.share.home.databinding.FragmentContentBinding
import world.share.home.databinding.FragmentHomeBinding
import world.share.home.model.HomeViewModel
import world.share.lib_base.mvvm.BaseFragment

/**
 * @author wan
 * 创建日期：2021/12/02
 * 描述：首页分页内容
 */
class ContentFragment : BaseFragment<FragmentContentBinding, HomeViewModel>(){

    override fun attachView(): Int {
        return R.layout.fragment_content
    }

    override fun initVariableId(): Int {

    }
}