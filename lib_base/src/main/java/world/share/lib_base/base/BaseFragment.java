package world.share.lib_base.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;

import world.share.lib_base.mvp.BaseView;


/**
 * 基础fragment
 * @author mac  2021/10/14
 */
public abstract class BaseFragment extends Fragment implements BaseView, View.OnClickListener {

    private int MIN_INTERVAL = 1500;
    private long currentTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initBinding(inflater, container);
        initViewModel();
        return bindView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        iniEvent();
    }

    /**
     * 初始化ViewBinding
     **/
    protected abstract void initBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);

    /**
     * 初始化Presenter
     **/
    public abstract void initViewModel();

    public void initView() {

    }

    public void initData() {

    }

    public void iniEvent() {

    }

    @Override
    public final void onClick(View v) {
        if (System.currentTimeMillis() - currentTime > MIN_INTERVAL) {
            currentTime = System.currentTimeMillis();
            onClick(v.getId());
        }
    }

    public void onClick(int id) {

    }

    /**
     * 路由页面跳转
     **/
    public void jump(String target) {
        ARouter.getInstance().build(target).navigation();
    }

    /**
     * 普通页面跳转
     **/
    public void jump(Class<?> tClass) {
        startActivity(new Intent(getActivity(), tClass));
    }

    /**
     * 设置布局
     *
     * @return 返回布局资源
     **/
    public abstract int attachView();

    /**
     * 返回页面布局
     *
     * @return 返回页面布局view
     **/
    protected abstract View bindView();

    @Override
    public void showDialog() {

    }

    @Override
    public void disMissDialog() {

    }

}
