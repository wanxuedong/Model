package world.share.baseutils.threadutil;

import java.util.concurrent.RejectedExecutionHandler;

/**
 * @author mac
 * 线程任务拒绝处理  2021/7/13
 */
public class RejectedHandler implements RejectedExecutionHandler {

    /**
     * 线程任务拒绝方法
     **/
    @Override
    public void rejectedExecution(Runnable r, java.util.concurrent.ThreadPoolExecutor executor) {
        try {
            Thread.sleep(3 * 1000L);
            executor.execute(r);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
