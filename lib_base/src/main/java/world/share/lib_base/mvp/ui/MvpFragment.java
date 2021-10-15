package world.share.lib_base.mvp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import world.share.lib_base.mvp.BaseView;
import world.share.lib_base.mvp.presenter.BasePresenter;
import world.share.lib_base.base.BaseFragment;

/**
 * @author mac
 * 标准mvp的fragment
 */
public abstract class MvpFragment<P extends BasePresenter, B extends ViewDataBinding> extends BaseFragment implements BaseView, View.OnClickListener {

    public P presenter;
    public B binding;

    @Override
    protected void initBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, attachView(), container, false);
    }

    @Override
    protected View bindView() {
        return binding.getRoot();
    }

}
