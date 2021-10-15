package world.share.home.ui;

import static world.share.lib_base.RouterUrl.USER_CENTER_ACTIVITY;

import com.alibaba.android.arouter.facade.annotation.Route;

import world.share.home.R;
import world.share.home.databinding.ActivityUserCenterBinding;
import world.share.lib_base.SimpleActivity;

/**
 * @author wan
 * 创建日期：2021/10/15
 * 描述：用户个人中心
 */
@Route(path = USER_CENTER_ACTIVITY)
public class UserCenterActivity extends SimpleActivity<ActivityUserCenterBinding> {

    @Override
    public int attachView() {
        return R.layout.activity_user_center;
    }

}