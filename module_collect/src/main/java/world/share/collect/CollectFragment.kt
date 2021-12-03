package world.share.collect

import com.alibaba.android.arouter.facade.annotation.Route
import world.share.collect.databinding.CollectFragmentCollectBinding
import world.share.collect.model.CollectViewModel
import world.share.lib_base.constant.RouterUrl
import world.share.lib_base.mvvm.BaseFragment

/**
 * @author wan
 * 创建日期：2021/12/1
 * 描述：我的收藏
 */
@Route(path = RouterUrl.COLLECT_FRAGMENT)
class CollectFragment : BaseFragment<CollectFragmentCollectBinding, CollectViewModel>() {

    override fun attachView(): Int {
        return R.layout.collect_fragment_collect
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }
}