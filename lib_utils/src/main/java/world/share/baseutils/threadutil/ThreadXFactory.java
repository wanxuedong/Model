package world.share.baseutils.threadutil;

import java.util.Date;
import java.util.concurrent.ThreadFactory;

/**
 * @author mac
 * 线程池工厂管理类  2021/713
 */
public class ThreadXFactory implements ThreadFactory {


    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName("自定义线程: " + new Date(System.currentTimeMillis()));
        return thread;
    }


}
