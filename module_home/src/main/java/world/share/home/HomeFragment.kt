package world.share.home

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayoutMediator
import world.share.home.adapter.HomeAdapter
import world.share.home.adapter.HomeTabLayoutAdapter
import world.share.home.databinding.HomeFragmentHomeBinding
import world.share.home.model.HomeViewModel
import world.share.lib_base.constant.RouterUrl
import world.share.lib_base.mvvm.BaseFragment
import world.share.lib_base.widget.boommenu.BoomMenuFactory

/**
 * @author wan
 * 创建日期：2021/12/1
 * 描述：首页
 */
@Route(path = RouterUrl.HOME_FRAGMENT)
class HomeFragment : BaseFragment<HomeFragmentHomeBinding, HomeViewModel>() {

    private var list = arrayOfNulls<String>(4)
    private var titles = mutableListOf<String>()
    private var fragments = mutableListOf<Fragment>()
    private lateinit var homeTabLayoutAdapter: HomeTabLayoutAdapter

    override fun attachView(): Int {
        return R.layout.home_fragment_home
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        super.initData()
        //初始化悬浮弹出框
        activity?.let { BoomMenuFactory.createHomeButton(binding.boomMenu, it) }
        list = resources.getStringArray(R.array.home_tabs)
        for (index in list) {
            index?.let {
                titles.add(it)
                fragments.add(ContentFragment.getInstance(it))
                var tabMenu = binding.homeTabLayout.newTab()
                tabMenu.text = index
                binding.homeTabLayout.addTab(tabMenu)
            }
        }
        binding.homeViewpager2.apply {
            adapter = HomeAdapter(childFragmentManager, fragments, lifecycle)
            // 优化体验设置该属性后第一次将自动加载所有fragment 在子fragment内部添加懒加载机制
            offscreenPageLimit = fragments.size
        }
        TabLayoutMediator(binding.homeTabLayout, binding.homeViewpager2) { tab, position ->
            tab.text = list[position]
        }.attach()
    }


}