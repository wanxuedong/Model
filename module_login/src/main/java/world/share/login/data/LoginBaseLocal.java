package world.share.login.data;

import world.share.baseutils.ToastUtil;
import world.share.baseutils.threadutil.LifeRunnable;
import world.share.baseutils.threadutil.ThreadX;
import world.share.lib_internet.local.BaseLocalData;

/**
 * @author mac
 */
public class LoginBaseLocal implements BaseLocalData {

    public void login() {
        ThreadX.getInstance().execute(new LifeRunnable() {
            @Override
            public void start() {
                super.start();
                ToastUtil.show("正在登录...");
            }

            @Override
            public void running() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void end() {
                super.end();
                ToastUtil.show("登录结束");
            }

        });
    }

}
