package world.share.lib_base.mvvm.container

import android.os.Bundle
import me.yokeyword.fragmentation.SupportFragment
import world.share.lib_base.BR
import world.share.lib_base.R
import world.share.lib_base.databinding.CommonContainerBinding
import world.share.lib_base.mvvm.BaseActivity
import world.share.lib_base.route.RouteCenter

/**
 * @author wan
 * 创建日期：2021/11/24
 * 描述：Fragment容器 根据路由地址加载根Fragment
 */
class ContainerActivity : BaseActivity<CommonContainerBinding, CommonViewModel>() {
    companion object {
        const val FRAGMENT = "fragment"
        const val BUNDLE = "bundle"
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        val fragmentPath: String? = intent.getStringExtra(FRAGMENT)
        if (fragmentPath.isNullOrBlank()) {
            return
        }
        val args: Bundle? = intent.getBundleExtra(BUNDLE)
        val fragment: SupportFragment = RouteCenter.navigate(fragmentPath,args) as SupportFragment
        if (findFragment(fragment::class.java) == null) {
            loadRootFragment(R.id.fl_container, fragment)
        }
    }

    override fun attachView(): Int {
        return R.layout.common_container
    }
}