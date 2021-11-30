package world.share.home

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.alibaba.android.arouter.facade.annotation.Route
import world.share.baseutils.AppManager
import world.share.baseutils.ToastUtil
import world.share.home.databinding.ActivityHomeBinding
import world.share.home.model.MainViewModel
import world.share.lib_base.RouterUrl.HOME_ACTIVITY
import world.share.lib_base.mvvm.BaseActivity

/**
 * @author wan
 * 创建日期：2021/10/15
 * 描述：首页
 */
@Route(path = HOME_ACTIVITY)
class MainActivity : BaseActivity<ActivityHomeBinding, MainViewModel>() {

    private var touchTime: Long = 0L

    override fun attachView(): Int {
        return R.layout.activity_home
    }

    override fun initData() {
        super.initData()
        val navController: NavController =
            Navigation.findNavController(this, R.id.nav_host_fragment_activity_home)
        NavigationUI.setupWithNavController(binding.navView, navController)
    }

    override val isUseBlackFontWithStatusBar: Boolean
        protected get() = true

    override fun initVariableId(): Int {
        return BR.view_model
    }

    override fun onBackPressedSupport() {
        if (System.currentTimeMillis() - touchTime < 2000L) {
            AppManager.instance.appExit()
        } else {
            touchTime = System.currentTimeMillis()
            ToastUtil.show(getString(R.string.main_press_again))
        }
    }

}