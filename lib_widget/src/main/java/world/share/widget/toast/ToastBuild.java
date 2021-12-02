package world.share.widget.toast;

import androidx.fragment.app.FragmentManager;

/**
 * @author wan
 * 创建日期：2021/12/02
 * 描述：吐司基本信息
 */
public class ToastBuild {

    /**
     * 吐司类型
     * 1 普通吐司
     * 2 material类型吐司
     **/
    public int type = 1;

    /**
     * 展示内容
     **/
    private String message;

    /**
     * 吐司距离底部或顶部的距离
     **/
    private int showTime = 3000;

    /**
     * 吐司展示时长
     **/
    private float margin = 80F;

    /**
     * 吐司点击事件回调
     **/
    private ToastClick toastClick;

    /**
     * 吐司位置
     **/
    private int gravity = -1;

    /**
     * Toast是否一直展示
     * true 展示不消失，此时关闭按钮会显示
     * false 展示后一会消失
     **/
    private boolean alwaysShow = false;

    /**
     * 上下文，material风格需要
     **/
    public FragmentManager manager;

    public ToastBuild(String message) {
        this.message = message;
    }

    public ToastBuild setType(int type) {
        this.type = type;
        return this;
    }

    public ToastBuild setManager(FragmentManager manager) {
        this.manager = manager;
        return this;
    }

    public ToastBuild setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ToastBuild setShowTime(int showTime) {
        this.showTime = showTime;
        return this;
    }

    public int getShowTime() {
        return showTime;
    }

    public ToastBuild setMargin(float margin) {
        this.margin = margin;
        return this;
    }

    public float getMargin() {
        return margin;
    }

    public ToastBuild setToastClick(ToastClick toastClick) {
        this.toastClick = toastClick;
        return this;
    }

    public ToastClick getToastClick() {
        return toastClick;
    }

    public ToastBuild setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public int getGravity() {
        return gravity;
    }

    public ToastBuild setAlwaysShow(boolean alwaysShow) {
        this.alwaysShow = alwaysShow;
        return this;
    }

    public boolean isAlwaysShow() {
        return alwaysShow;
    }
}
