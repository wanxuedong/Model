package world.share.main

import com.alibaba.android.arouter.facade.annotation.Route
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.czl.lib_base.adapter.ViewPagerFmAdapter
import me.yokeyword.fragmentation.SupportFragment
import world.share.baseutils.AppManager
import world.share.lib_base.constant.RouterUrl.COLLECT_FRAGMENT
import world.share.lib_base.constant.RouterUrl.MAIN_ACTIVITY
import world.share.lib_base.constant.RouterUrl.HOME_FRAGMENT
import world.share.lib_base.constant.RouterUrl.USER_FRAGMENT
import world.share.lib_base.mvvm.BaseActivity
import world.share.lib_base.route.RouteCenter
import world.share.main.databinding.MainActivityHomeBinding
import world.share.main.model.MainViewModel
import world.share.widget.toast.ToastUtil

/**
 * @author wan
 * 创建日期：2021/10/15
 * 描述：首页
 */
@Route(path = MAIN_ACTIVITY)
class MainActivity : BaseActivity<MainActivityHomeBinding, MainViewModel>() {

    private var touchTime: Long = 0L

    override fun attachView(): Int {
        return R.layout.main_activity_home
    }

    override fun initData() {
        super.initData()
        initBottomBar()
        initViewPager()
    }

    override fun initEvent() {
        super.initEvent()
        viewModel.uc.tabChangeLiveEvent.observe(this, {
            binding.homeViewpager.setCurrentItem(it, false)
        })
        viewModel.uc.pageChangeLiveEvent.observe(this, {
            binding.bottomNavigation.selectTab(it)
        })
    }

    private fun initBottomBar() {
        binding.bottomNavigation.apply {
            setMode(BottomNavigationBar.MODE_FIXED)
            setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
            addItem(
                BottomNavigationItem(
                    R.drawable.main_tab_main_on,
                    getString(R.string.title_home)
                ).setActiveColorResource(R.color.blueColor)
                    .setInactiveIconResource(R.drawable.main_tab_main_off)
            )
            addItem(
                BottomNavigationItem(
                    R.drawable.main_tab_collect_on,
                    getString(R.string.title_dashboard)
                ).setActiveColorResource(R.color.blueColor)
                    .setInactiveIconResource(R.drawable.main_tab_collect_off)
            )
            addItem(
                BottomNavigationItem(
                    R.drawable.main_tab_me_on,
                    getString(R.string.title_notifications)
                )
                    .setActiveColorResource(R.color.blueColor)
                    .setInactiveIconResource(R.drawable.main_tab_me_off)
            )
            setFirstSelectedPosition(0)
            initialise()
        }
    }

    private fun initViewPager() {
        // 设置不可滑动
        binding.homeViewpager.isUserInputEnabled = false
        val homeFragment = RouteCenter.navigate(HOME_FRAGMENT) as SupportFragment
        val collectFragment = RouteCenter.navigate(COLLECT_FRAGMENT) as SupportFragment
        val userFragment = RouteCenter.navigate(USER_FRAGMENT) as SupportFragment
        val fragments = arrayListOf(homeFragment, collectFragment, userFragment)
        binding.homeViewpager.apply {
            adapter = ViewPagerFmAdapter(supportFragmentManager, lifecycle, fragments)
            offscreenPageLimit = fragments.size
        }
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun onBackPressedSupport() {
        if (System.currentTimeMillis() - touchTime < 2000L) {
            AppManager.instance.appExit()
        } else {
            touchTime = System.currentTimeMillis()
            ToastUtil.show(getString(R.string.main_press_again))
        }
    }

    override val statusBarColor: Int
        get() = R.color.darkBlueColor

}