package world.share.lib_base;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import world.share.lib_base.base.BaseActivity;


/**
 * @author wan
 * 简单的Activity
 */
public abstract class SimpleActivity<V extends ViewDataBinding> extends BaseActivity {

    public V binding;

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, attachView());
    }

}
