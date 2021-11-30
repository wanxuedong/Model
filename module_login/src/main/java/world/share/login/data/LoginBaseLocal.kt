package world.share.login.data

import world.share.baseutils.ToastUtil
import world.share.baseutils.threadutil.LifeRunnable
import world.share.baseutils.threadutil.ThreadX
import world.share.lib_base.data.LocalDataSource

/**
 * @author mac
 */
class LoginBaseLocal : LocalDataSource {
    override fun getLocalData() {
        ThreadX.getInstance().execute(object : LifeRunnable() {
            override fun start() {
                super.start()
                ToastUtil.show("正在登录...")
            }

            override fun running() {
                try {
                    Thread.sleep(3000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }

            override fun end() {
                super.end()
                ToastUtil.show("登录结束")
            }
        })
    }
}