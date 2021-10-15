package world.share.lib_base.mvp.ui;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import world.share.lib_base.base.BaseActivity;
import world.share.lib_base.mvp.presenter.BasePresenter;


/**
 * @author mac
 * 标准mvp的Activity
 */
public abstract class MvpActivity<P extends BasePresenter, V extends ViewDataBinding> extends BaseActivity {

    public P presenter;
    public V binding;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, attachView());
    }


}
