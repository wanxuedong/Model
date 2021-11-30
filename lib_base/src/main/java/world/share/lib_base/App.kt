package world.share.lib_base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.czl.lib_base.di.allModule
import com.tencent.mmkv.MMKV
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import world.share.baseutils.AppManager
import world.share.baseutils.BaseUtil
import world.share.baseutils.CallBack

/**
 * @author mac
 * 全局Application
 */
open class App : MultiDexApplication(), CallBack<Application?> {
    override fun onCreate() {
        super.onCreate()
        //工具类Application回调初始化
        BaseUtil.setCallBack(this)
        //MMkv初始化
        MMKV.initialize(this)
        //阿里路由初始化
        initRouter()
        //对象注解初始化
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(allModule)
        }
        //Activity全局管理
        AppManager.instance.setAppManager(this)
    }

    @Override
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun call(vararg t: Application?): Application? {
        return get<App>()
    }

    /**
     * 初始化阿里路由
     */
    private fun initRouter() {
        //这两行必须写在init之前，否则这些配置在init过程中无效
        if (BuildConfig.DEBUG) { //第三方工具判断是否是Debug 或者BuildConfig.DEBUG
            //打印日志
            ARouter.openLog()
            //开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险）
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

    /**
     * 资源释放
     * **/
    override fun onTerminate() {
        super.onTerminate()
        ARouter.getInstance().destroy()
    }

    /**
     * 低内存环境下清空不必要的数据
     * **/
    override fun onLowMemory() {
        super.onLowMemory()
        Glide.get(this).clearMemory()
    }

    /**
     * 不同级别低内存环境下处理
     * **/
    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory()
        }
        Glide.get(this).trimMemory(level)
    }

}