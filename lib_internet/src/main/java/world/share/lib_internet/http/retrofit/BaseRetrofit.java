package world.share.lib_internet.http.retrofit;


import static world.share.lib_internet.http.INTERNET_CONSTANT.BASE_URL;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.trello.rxlifecycle2.android.BuildConfig;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import world.share.lib_internet.http.HttpsUtils;
import world.share.lib_internet.http.conver.MyGsonConverterFactory;
import world.share.lib_internet.http.cookie.CookieJarImpl;
import world.share.lib_internet.http.cookie.store.PersistentCookieStore;
import world.share.lib_internet.http.interceptor.BaseInterceptor;
import world.share.lib_internet.http.interceptor.CacheInterceptor;
import world.share.lib_internet.http.interceptor.logging.Level;
import world.share.lib_internet.http.interceptor.logging.LoggingInterceptor;

/**
 * @author mac
 * @date 2021/9/10
 * RetrofitClient封装单例类, 实现网络请求
 */
public class BaseRetrofit {
    /**
     * 超时时间
     **/
    private static final int DEFAULT_TIMEOUT = 20;
    /**
     * 缓存时间
     **/
    private static final int CACHE_TIMEOUT = 10 * 1024 * 1024;

    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;

    private Cache cache = null;
    private File httpCacheDirectory;

    /**
     * 域名
     **/
    public static String URL = BASE_URL;

    public void setURL(String URL) {
        this.URL = URL;
    }

    public BaseRetrofit(Application application) {
        this(application,URL, null);
    }

    private BaseRetrofit(Application application,String url, Map<String, String> headers) {

        if (TextUtils.isEmpty(url)) {
            url = URL;
        }

        if (httpCacheDirectory == null) {
            httpCacheDirectory = new File(application.getCacheDir(), "goldze_cache");
        }

        try {
            if (cache == null) {
                cache = new Cache(httpCacheDirectory, CACHE_TIMEOUT);
            }
        } catch (Exception e) {
        }
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        okHttpClient = new OkHttpClient.Builder()
                .cookieJar(new CookieJarImpl(new PersistentCookieStore(application)))
                .cache(cache)
                .addInterceptor(new BaseInterceptor(headers))
                .addInterceptor(new CacheInterceptor(application))
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .addInterceptor(new LoggingInterceptor
                        .Builder()//构建者模式
                        .loggable(BuildConfig.DEBUG) //是否开启日志打印
                        .setLevel(Level.BASIC) //打印的等级
                        .log(Platform.INFO) // 打印类型
                        .request("Request") // request的Tag
                        .response("Response")// Response的Tag
                        .addHeader("log-header", "I am the log request header.") // 添加打印头, 注意 key 和 value 都不能是中文
                        .build()
                )
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(MyGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();

    }

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the {@code service} interface.
     */
    public <T> T create(Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }
}
