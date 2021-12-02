package world.share.home.ui.collect

import world.share.home.BR
import world.share.home.R
import world.share.home.databinding.FragmentCollectBinding
import world.share.home.model.CollectViewModel
import world.share.lib_base.mvvm.BaseFragment

/**
 * @author wan
 * 创建日期：2021/12/1
 * 描述：我的收藏
 */
class CollectFragment : BaseFragment<FragmentCollectBinding, CollectViewModel>() {

    override fun attachView(): Int {
        return R.layout.fragment_collect
    }

    override fun initVariableId(): Int {
        return BR.view_model
    }
}