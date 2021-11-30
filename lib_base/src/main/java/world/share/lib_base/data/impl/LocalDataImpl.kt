package world.share.lib_base.data.impl

import com.blankj.utilcode.util.GsonUtils
import com.google.gson.reflect.TypeToken
import world.share.baseutils.MvHelper
import world.share.baseutils.ToastUtil
import world.share.baseutils.threadutil.LifeRunnable
import world.share.baseutils.threadutil.ThreadX
import world.share.lib_base.bean.UserBean
import world.share.lib_base.constant.UserConstant.LOGIN_NAME
import world.share.lib_base.constant.UserConstant.USER_ID
import world.share.lib_base.constant.UserConstant.USER_JSON_DATA
import world.share.lib_base.data.LocalDataSource

/**
 * @author mac
 */
class LocalDataImpl : LocalDataSource {
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

    override fun saveUserData(userBean: UserBean) {
        MvHelper.encode(USER_ID, userBean.id)
        MvHelper.encode(LOGIN_NAME, userBean.publicName)
        MvHelper.encode(
            USER_JSON_DATA,
            GsonUtils.toJson(userBean, object : TypeToken<UserBean>() {}.type)
        )
    }
}