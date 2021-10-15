package world.share.widget;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * 通用对话框
 * 2021/9/29 @author wan
 **/
public abstract class BaseDialogFragment extends DialogFragment {

    /**
     * 弹框实例
     **/
    private Dialog dialog;

    /**
     * Window
     **/
    private Window window;

    /**
     * 是否展示黑色背景
     **/
    private boolean showBack = true;

    /**
     * 是否响应弹框外的点击事件
     **/
    private boolean outClick = false;

    /**
     * 弹出后会点击屏幕或物理返回键
     * true 如果cancelOutSide为true，点击周边dialog消失，如果cancelOutSide为false，点击返回键dialog小时
     * false dialog不消失
     **/
    private boolean cancelable = false;

    /**
     * 弹出后会点击屏幕或物理返回键
     * true dialog消失
     * false dialog不消失
     **/
    private boolean cancelOutSide = false;

    /**
     * 设置弹框对齐方式
     **/
    private int gravity = Gravity.CENTER;

    /**
     * 高度大小
     **/
    private int height = ViewGroup.LayoutParams.WRAP_CONTENT;

    /**
     * 宽度大小
     **/
    private int width = ViewGroup.LayoutParams.WRAP_CONTENT;

    /**
     * 对话框视图
     **/
    private View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadStyle();
    }

    public boolean isShowBack() {
        return showBack;
    }

    public boolean isOutClick() {
        return outClick;
    }

    @Override
    public boolean isCancelable() {
        return cancelable;
    }

    public boolean isCancelOutSide() {
        return cancelOutSide;
    }

    public int getGravity() {
        return gravity;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadConfig();
    }

    /**
     * 读取对话框风格
     **/
    private void loadStyle() {
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.TransParent_Dialog);
    }

    /**
     * 读取对话框配置
     **/
    private void loadConfig() {
        dialog = getDialog();
        dialog.setCancelable(isCancelable());
        dialog.setCanceledOnTouchOutside(isCancelOutSide());
        window = dialog.getWindow();
        if (!isShowBack()) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            lp.dimAmount = 0;
            window.setAttributes(lp);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        if (isOutClick()) {
            //即使该window可获得焦点情况下，仍把该window之外的任何event发送到该window之后的其他window
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
            //如果点击事件发生在window之外，就会收到一个特殊的MotionEvent，为ACTION_OUTSIDE
            window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
        }
        window.setLayout(getWidth(), getHeight());
        window.setGravity(getGravity());
    }

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getDialogView(), container, false);
            initView(rootView);
            initData();
            initEvent();
        } else {
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(rootView);
            }
        }
        return rootView;
    }

    /**
     * 获取弹框布局
     **/
    protected abstract int getDialogView();

    protected void initView(View rootView){};

    private void initData() {

    }

    private void initEvent() {

    }

}
