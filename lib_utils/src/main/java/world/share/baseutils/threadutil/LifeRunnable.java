package world.share.baseutils.threadutil;

import android.os.Handler;

import java.util.concurrent.atomic.AtomicInteger;

import static world.share.baseutils.threadutil.ThreadX.CORE_SIZE;
import static world.share.baseutils.threadutil.ThreadX.THREAD_RUN_TAG;
import static world.share.baseutils.threadutil.ThreadX.THREAD_TASK_TAG;

/**
 * @author mac
 * 线程生命周期管理  2021/7/13
 */
public abstract class LifeRunnable implements Runnable {

    /**
     * 生命周期回调处理
     **/
    private Handler handler;

    /**
     * 线程将要处理任务数量
     **/
    private AtomicInteger taskCount;

    /**
     * 线程正在处理任务数量
     **/
    private AtomicInteger runCount;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void setTaskCount(AtomicInteger taskCount) {
        this.taskCount = taskCount;
    }

    public void setRunCount(AtomicInteger runCount) {
        this.runCount = runCount;
    }

    /**
     * 线程执行过程
     **/
    @Override
    public final void run() {
        android.util.Log.d(THREAD_TASK_TAG, "开始-----线程任务,当前任务数:" + taskCount.get());
        android.util.Log.d(THREAD_RUN_TAG, "处理中线程数量: " + runCount.incrementAndGet() + ((CORE_SIZE == runCount.get()) ? ",满符负荷运行" : ""));
        handler.post(new Runnable() {
            @Override
            public void run() {
                start();
            }
        });
        running();
        handler.post(new Runnable() {
            @Override
            public void run() {
                end();
            }
        });
        runCount.decrementAndGet();
        android.util.Log.d(THREAD_TASK_TAG, "完成-----线程任务，剩余" + taskCount.decrementAndGet() + "个任务");
        android.util.Log.d(THREAD_RUN_TAG, "处理剩余线程数量: " + runCount.get());
    }

    /**
     * 线程开始前执行，主线程
     **/
    public void start() {

    }

    /**
     * 线程执行过程，子线程
     **/
    public abstract void running();

    /**
     * 线程结束后执行，主线程
     **/
    public void end() {

    }

}
