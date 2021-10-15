package world.share.baseutils;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;

/**
 * 常用工具类
 *
 * @author mac
 */
public final class ContextUtils extends BaseUtil{

    @SuppressLint("StaticFieldLeak")

    private ContextUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        checkInit();
        if (context != null) {
            return context;
        }
        throw new NullPointerException("should be initialized in application");
    }
}