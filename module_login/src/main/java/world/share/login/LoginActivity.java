package world.share.login;


import static world.share.lib_base.RouterUrl.HOME_ACTIVITY;
import static world.share.lib_base.RouterUrl.LOGIN_ACTIVITY;
import static world.share.lib_base.RouterUrl.USER_CENTER_ACTIVITY;

import com.alibaba.android.arouter.facade.annotation.Route;

import world.share.baseutils.ToastUtil;
import world.share.lib_base.mvp.ui.MvpActivity;
import world.share.login.databinding.ActivityLoginBinding;
import world.share.login.presenter.LoginPresenter;
import world.share.login.view.LoginView;

/**
 * 登录页面
 * 2021/9/10
 **/
@Route(path = LOGIN_ACTIVITY)
public class LoginActivity extends MvpActivity<LoginPresenter, ActivityLoginBinding> implements LoginView {

    @Override
    public void initViewModel() {
        presenter = new LoginPresenter(this, binding);
    }

    @Override
    public int attachView() {
        return R.layout.activity_login;
    }

    @Override
    public void initEvent() {
        super.initEvent();
        binding.click.setOnClickListener(this);
        binding.jump.setOnClickListener(this);
    }

    @Override
    public void onClick(int id) {
        super.onClick(id);
        if (id == R.id.click) {
            presenter.login();
        } else if (id == R.id.jump) {
            jump(HOME_ACTIVITY);
        }
    }

    @Override
    public void loginSuccess() {
        ToastUtil.show("登录成功!");
    }

    @Override
    protected boolean isUseBlackFontWithStatusBar() {
        return true;
    }

}