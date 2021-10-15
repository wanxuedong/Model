package world.share.home;

import static world.share.lib_base.RouterUrl.HOME_ACTIVITY;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.alibaba.android.arouter.facade.annotation.Route;

import world.share.home.databinding.ActivityHomeBinding;
import world.share.lib_base.SimpleActivity;

/**
 * @author wan
 * 创建日期：2021/10/15
 * 描述：首页
 */
@Route(path = HOME_ACTIVITY)
public class HomeActivity extends SimpleActivity<ActivityHomeBinding> {

    @Override
    public int attachView() {
        return R.layout.activity_home;
    }

    public void initData() {
        super.initData();
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
        ).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    protected boolean isUseBlackFontWithStatusBar() {
        return true;
    }
}