package world.share.lib_base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import world.share.lib_base.base.BaseFragment;

/**
 * @author mac
 * 简单的fragment
 */
public abstract class SimpleFragment<V extends ViewDataBinding> extends BaseFragment{

    public V binding;

    @Override
    protected void initBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, attachView(), container, false);
    }

    @Override
    protected View bindView() {
        return binding.getRoot();
    }

    @Override
    public void initViewModel() {

    }
}
