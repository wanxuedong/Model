package world.share.widget.toast;


import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

/**
 * 封装Toast
 * 实现功能
 * 1：子线程可以调用
 * 2：多种风格Toast
 * 3：重复调用处理
 *
 * @author mac
 */
public class ToastUtil {

    /**
     * 上次点击时间
     **/
    private static long lastTime;

    /**
     * 想用使用context，必须要先调用checkInit
     **/
    private static Application application;

    /**
     * 点击处理间隔时长
     **/
    private static final int INTERVAlTIME = 2000;

    /**
     * 子线程转主线程处理
     **/
    private static Handler handler;

    /***
     * 工具类初始化方法，但不需要主动调用，在Application设置监听回调即可
     * **/
    public static void init(Application app) {
        application = app;
        if (handler != null) {
            return;
        }
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                realShow((ToastBuild) msg.obj);
            }
        };
    }


    /**
     * 普通Toast
     *
     * @param content Toast内容
     */
    public static void show(String content) {
        show(new ToastBuild(content).setType(1));
    }

    /**
     * material风格Toast
     *
     * @param manager 上下文
     * @param builder material Toast风格参数
     */
    // TODO: 2021/12/2 manager的引用可能导致内存泄漏，待处理
    public static void materialShow(FragmentManager manager, ToastBuild builder) {
        show(builder.setType(2).setManager(manager));
    }


    /**
     * Toast重复点击和子线程调用处理
     *
     * @param builder material Toast风格参数
     */
    private static void show(ToastBuild builder) {
        if (application == null) {
            throw new RuntimeException("please init first");
        }
        synchronized (ToastUtil.class) {
            if (TextUtils.isEmpty(builder.getMessage())) {
                return;
            }
            if (System.currentTimeMillis() - lastTime > INTERVAlTIME) {
                lastTime = System.currentTimeMillis();
                /*
                 * 判断是否在主线程调用，如果不是，切换到主线程
                 **/
                if (Looper.getMainLooper() == Looper.myLooper()) {
                    realShow(builder);
                } else {
                    lastTime = 0;
                    Message message = handler.obtainMessage();
                    message.obj = builder;
                    handler.sendMessage(message);
                }
            }
        }
    }

    /**
     * Toast最终调用的地方
     *
     * @param builder material Toast风格参数
     **/
    private static void realShow(ToastBuild builder) {
        if (builder.type == 1) {
            Toast.makeText(application, builder.getMessage(), Toast.LENGTH_SHORT).show();
        } else if (builder.type == 2) {
            ToastFragment toastFragment = new ToastFragment();
            toastFragment.setToastClick(builder.getToastClick());
            toastFragment.message = builder.getMessage();
            toastFragment.setGravity(builder.getGravity());
            toastFragment.setShowTime(builder.getShowTime());
            toastFragment.setMargin(builder.getMargin());
            toastFragment.setShowClose(builder.isAlwaysShow());
            toastFragment.show(builder.manager, "toast_notice");
        }
    }

}
