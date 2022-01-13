package world.share.baseutils.threadutil;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author mac
 * 线程池管理工具  2021/713
 */
public class ThreadX {

    /**
     * 线程池维护准备执行线程数量信息
     **/
    protected static final String THREAD_TASK_TAG = "Thread_Task_Info";

    /**
     * 线程池正在运行线程数量信息
     **/
    protected static final String THREAD_RUN_TAG = "Thread_Run_Info";

    /**
     * 单例
     **/
    private static ThreadX instance;

    /**
     * 核心线程数量
     **/
    protected static final int CORE_SIZE = Runtime.getRuntime().availableProcessors();

    /**
     * 线程池管理
     **/
    private java.util.concurrent.ThreadPoolExecutor executor;

    /**
     * 生命周期回调处理
     **/
    private Handler handler;

    /**
     * 线程将要处理任务数量
     **/
    private AtomicInteger taskCount = new AtomicInteger(0);

    /**
     * 线程正在处理任务数量
     **/
    private AtomicInteger runCount = new AtomicInteger(0);

    private ThreadX() {
        executor = new java.util.concurrent.ThreadPoolExecutor(CORE_SIZE,
                Integer.MAX_VALUE,
                3 * 60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new ThreadXFactory(),
                new RejectedHandler());
        handler = new Handler(Looper.getMainLooper());
    }

    public static ThreadX getInstance() {
        if (instance == null) {
            synchronized (ThreadX.class) {
                if (instance == null) {
                    instance = new ThreadX();
                }
            }
        }
        return instance;
    }

    /**
     * 添加新任务
     **/
    public void execute(LifeRunnable runnable) {
        taskCount.incrementAndGet();
        android.util.Log.d(THREAD_TASK_TAG, "新增线程任务,第" + taskCount + "个");
        runnable.setHandler(handler);
        runnable.setTaskCount(taskCount);
        runnable.setRunCount(runCount);
        executor.execute(runnable);
    }

}
