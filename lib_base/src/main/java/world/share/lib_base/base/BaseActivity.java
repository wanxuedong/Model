package world.share.lib_base.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;

import world.share.baseutils.statusbar.StatusBarUtil;
import world.share.lib_base.mvp.BaseView;
import world.share.lib_base.R;


/**
 * 基础Activity
 * @author mac  2021/10/14
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView, View.OnClickListener {

    private AlertDialog loadingDialog;

    /**
     * 连续点击间隔
     **/
    private int MIN_INTERVAL = 1500;
    private long currentTime;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar();
        initBinding();
        initViewModel();
        initView();
        initData();
        initEvent();
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
        startActivity(new Intent(this, tClass));
    }

    /**
     * 初始化ViewBinding
     **/
    protected void initBinding() {
    }

    ;

    /**
     * 初始化Presenter
     **/
    public void initViewModel() {
    }

    /**
     * 获取页面布局
     *
     * @return 页面布局id
     **/
    public abstract int attachView();

    public void initView() {

    }

    public void initData() {
    }

    public void initEvent() {

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
     * 网络请求的时候显示正在加载的对话框
     */
    private DialogInterface.OnKeyListener onKeyListener =
            (dialog, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0;

    @Override
    public void showDialog() {
        try {
            if (null == loadingDialog) {
                loadingDialog = new AlertDialog.Builder(this).setView(new ProgressBar(this))
                        .setCancelable(false)
                        .setOnKeyListener(onKeyListener)
                        .create();
                loadingDialog.setCanceledOnTouchOutside(false);
                Window window = loadingDialog.getWindow();
                if (null != window) {
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
            }
            if (!loadingDialog.isShowing()) {
                loadingDialog.show();
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void disMissDialog() {
        try {
            if (null != loadingDialog) {
                if (loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                loadingDialog = null;
            }
        } catch (Exception e) {

        }
    }

    /**
     * 是否设置成透明状态栏，即就是全屏模式
     */
    protected boolean isUseFullScreenMode() {
        return false;
    }

    /**
     * 更改状态栏颜色，只有非全屏模式下有效
     */
    protected int getStatusBarColor() {
        return R.color.white;
    }

    /**
     * 是否改变状态栏文字颜色为黑色，默认为黑色
     */
    protected boolean isUseBlackFontWithStatusBar() {
        return false;
    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isUseFullScreenMode()) {
                StatusBarUtil.transparencyBar(this);
            } else {
                StatusBarUtil.setStatusBarColor(this, getStatusBarColor());
            }
            if (isUseBlackFontWithStatusBar()) {
                StatusBarUtil.setLightStatusBar(this, true, isUseFullScreenMode());
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
