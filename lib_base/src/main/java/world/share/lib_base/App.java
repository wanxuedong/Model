package world.share.lib_base;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

import world.share.baseutils.BaseUtil;
import world.share.baseutils.CallBack;
import world.share.lib_base.data.impl.InternetDataApi;
import world.share.lib_base.data.impl.LocalDataSource;
import world.share.lib_base.data.inject.DataProvider;
import world.share.lib_base.data.inject.Injection;
import world.share.lib_internet.http.retrofit.BaseRetrofit;

/**
 * @author mac
 */
public class App extends Application implements CallBack<Application>, DataProvider {

    @Override
    public void onCreate() {
        super.onCreate();
        BaseUtil.setCallBack(this);
        initRouter();
        Injection.getInstance().setDataProvider(this);
    }

    @Override
    public Application call(Application... t) {
        return this;
    }

    /**
     * 初始化路由
     **/
    private void initRouter() {
        //这两行必须写在init之前，否则这些配置在init过程中无效
        if (BuildConfig.DEBUG) {//第三方工具判断是否是Debug 或者BuildConfig.DEBUG
            //打印日志
            ARouter.openLog();
            //开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险）
            ARouter.openDebug();

        }
        ARouter.init(this);
    }

    @Override
    public void provider() {
        //可以设置单个或多个网络/本地请求模块
        new Injection.RepositoryBuilder()
                .setRetrofit(new BaseRetrofit(this))
                .addApiClass(InternetDataApi.class)
                .addLocalSource(LocalDataSource.class)
                .build();
    }

}
