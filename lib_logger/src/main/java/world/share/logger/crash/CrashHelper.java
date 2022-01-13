package world.share.logger.crash;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.SystemClock;

import androidx.annotation.Nullable;

import java.io.PrintWriter;
import java.io.StringWriter;

import world.share.logger.LogToFile;
import world.share.logger.LogUtil;

import static world.share.logger.LogConfig.CRASH_LOG_NAME;


/**
 * 崩溃日志抓取,使用前需要调用init初始化
 * 需要存储权限，最终存储路径在本地文件，当前包名下的crash文件中
 *
 * @author mac
 */

public class CrashHelper implements Thread.UncaughtExceptionHandler {

    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private static CrashHelper instance = new CrashHelper();
    private static Context context;
    // 用来存储设备信息和异常信息
    private StringBuffer sb = new StringBuffer();

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHelper() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHelper getInstance() {
        return instance;
    }

    /**
     * 初始化
     */
    public void init(Context app) {
        context = app;
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(@Nullable Thread thread, @Nullable Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            if (thread != null && ex != null) {
                mDefaultHandler.uncaughtException(thread, ex);
            }
        } else {
            SystemClock.sleep(3000);
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @return true:如果处理了该异常信息; 否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        try {
            // 收集设备参数信息
            collectDeviceInfo(context);
            // 保存日志文件
            saveCrashInfoFile(ex);
            // SystemClock.sleep(1000);
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * 收集设备参数信息
     */
    private void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                sb.append("versionName：" + pi.versionName + "\n");
                sb.append("versionCode：" + pi.versionCode + "\n");
                sb.append("主板：" + Build.BOARD + "\n");
                sb.append("系统启动程序版本号：" + Build.BOOTLOADER + "\n");
                sb.append("系统定制商：" + Build.BRAND + "\n");
                sb.append("cpu指令集：" + Build.CPU_ABI + "\n");
                sb.append("cpu指令集2：" + Build.CPU_ABI2 + "\n");
                sb.append("设置参数：" + Build.DEVICE + "\n");
                sb.append("显示屏参数：" + Build.DISPLAY + "\n");
                sb.append("无线电固件版本：" + Build.getRadioVersion() + "\n");
                sb.append("硬件识别码：" + Build.FINGERPRINT + "\n");
                sb.append("硬件名称：" + Build.HARDWARE + "\n");
                sb.append("HOST:" + Build.HOST + "\n");
                sb.append("修订版本列表：" + Build.ID + "\n");
                sb.append("硬件制造商：" + Build.MANUFACTURER + "\n");
                sb.append("版本：" + Build.MODEL + "\n");
                sb.append("硬件序列号：" + Build.SERIAL + "\n");
                sb.append("手机制造商：" + Build.PRODUCT + "\n");
                sb.append("描述Build的标签：" + Build.TAGS + "\n");
                sb.append("TIME:" + Build.TIME + "\n");
                sb.append("builder类型：" + Build.TYPE + "\n");
                sb.append("USER:" + Build.USER + "\n");
            }
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.e("an error occurred when collect package info", e.getMessage());
        }
    }

    /**
     * 保存错误信息到文件中
     */
    private void saveCrashInfoFile(Throwable ex) {
        try {
            sb.append("=========================================\n");
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            sb.append(sw.toString());
            sb.append("=========================================\n");
            LogToFile.write("crash: ", sb.toString(), CRASH_LOG_NAME);
        } catch (Exception e) {
            sb.append("an error occurred while writing file...\r\n" + e.getMessage());
            writeFile(sb.toString());
        }
    }

    private void writeFile(String sb) {
        LogToFile.write("crash Exception: ", sb, CRASH_LOG_NAME);
    }

}

