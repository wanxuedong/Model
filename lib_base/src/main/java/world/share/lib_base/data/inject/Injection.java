package world.share.lib_base.data.inject;


import java.util.HashMap;

import world.share.lib_internet.http.retrofit.BaseRetrofit;
import world.share.lib_internet.local.BaseLocalData;
import world.share.lib_internet.service.BaseApiService;

/**
 * @author mac
 * 所有网络数据和本地数据请求处理处
 */
public class Injection {

    private static Injection instance;
    /**
     * 保存全部的网络请求类
     **/
    private static HashMap<String, Object> builderHashMap = new HashMap<>();
    /**
     * 网络回调
     **/
    private DataProvider dataProvider;

    /**
     * 设置网络和本地数据提供者,可以多次设置，都会保存
     **/
    public void setDataProvider(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    /**
     * 设置网络模块回调
     **/

    public static Injection getInstance() {
        synchronized (Injection.class) {
            if (instance == null) {
                instance = new Injection();
            }
        }
        return instance;
    }

    /**
     * 获取已经准备好的网络/本地数据处理类
     **/
    public <T> T getClass(Class<T> tClass) {
        if (builderHashMap.get(tClass.getName()) == null && dataProvider != null) {
            dataProvider.provider();
        }
        return (T) builderHashMap.get(tClass.getName());
    }

    /**
     * 网络/本地数据处理对象
     **/
    public static class RepositoryBuilder<T> {

        /**
         * 网络请求体
         **/
        public BaseRetrofit retrofit;

        /**
         * 网络数据请求处理
         **/
        private Class<T> apiNative;
        public T apiClass;
        /**
         * 本地数据请求处理
         **/
        public Class<? extends BaseLocalData> localSource;

        public RepositoryBuilder setRetrofit(BaseRetrofit retrofit) {
            this.retrofit = retrofit;
            return this;
        }

        public RepositoryBuilder addApiClass(Class<T> apiClass) {
            //网络数据源
            this.apiNative = apiClass;
            return this;
        }

        public RepositoryBuilder addLocalSource(Class<? extends BaseLocalData> localSource) {
            this.localSource = localSource;
            return this;
        }

        public void build() {
            if (retrofit != null && builderHashMap.get(apiNative.getName()) == null) {
                this.apiClass = retrofit.create(apiNative);
                builderHashMap.put(apiNative.getName(), this.apiClass);
            }
            if (localSource != null && builderHashMap.get(localSource.getName()) == null) {
                builderHashMap.put(localSource.getName(), localSource);
            }
        }

    }

}
