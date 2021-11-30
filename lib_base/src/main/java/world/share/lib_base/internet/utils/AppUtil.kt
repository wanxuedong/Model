package world.share.lib_base.utils

import android.app.Application
import world.share.lib_base.utils.AppUtil

/**
 * @author wan
 * 创建日期：2021/11/24
 * 描述：application全局调用
 */
object AppUtil {
    var app: Application? = null
        private set

    fun init(application: Application?) {
        app = application
    }

}