package world.share.widget.toast;

import androidx.fragment.app.FragmentActivity;

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
     * 上下文，material风格需要
     **/
    public FragmentActivity activity;

    public ToastBuild(String message) {
        this.message = message;
    }

    public ToastBuild setType(int type) {
        this.type = type;
        return this;
    }

    public ToastBuild setActivity(FragmentActivity activity) {
        this.activity = activity;
        return this;
    }

    /**
     * 展示内容
     **/
    public String message;

    /**
     * 吐司展示时长
     **/
    public int showTime = 3000;

    /**
     * 吐司点击事件回调
     **/
    public ToastClick toastClick;

    /**
     * 吐司位置
     **/
    public int gravity = -1;

}
