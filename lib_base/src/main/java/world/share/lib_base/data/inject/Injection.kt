package world.share.lib_base.data.inject

import world.share.lib_base.data.LocalDataSource
import world.share.lib_base.internet.http.retrofit.RetrofitClient
import java.util.*

/**
 * @author mac
 * 所有网络数据和本地数据请求处理处
 */
class Injection {

    companion object {
        /**
         * 设置网络模块回调
         */
        @JvmStatic
        var instance: Injection? = null
            get() {
                synchronized(Injection::class.java) {
                    if (field == null) {
                        field = Injection()
                    }
                }
                return field
            }
            private set

        /**
         * 保存全部的网络请求类
         */
        private val builderHashMap = HashMap<String, Any?>()
        /**
         * 网络回调
         */
        private var dataProvider: DataProvider? = null

        /**
         * 设置网络和本地数据提供者,可以多次设置，都会保存
         */
        fun setDataProvider(dataProvider: DataProvider?) {
            this.dataProvider = dataProvider
        }

        /**
         * 获取已经准备好的网络/本地数据处理类
         */
        fun <T> getClass(tClass: Class<T>): T? {
            if (builderHashMap[tClass.name] == null && dataProvider != null) {
                dataProvider!!.provider()
            }
            return builderHashMap[tClass.name] as T?
        }

        /**
         * 网络/本地数据处理对象
         */
        class RepositoryBuilder<T> {
            /**
             * 网络请求体
             */
            var retrofitClient: RetrofitClient? = null

            /**
             * 网络数据请求处理
             */
            private var apiNative: Class<T>? = null
            var apiClass: T? = null

            /**
             * 本地数据请求处理
             */
            var localSource: Class<out LocalDataSource>? = null
            fun setRetrofit(retrofitClient: RetrofitClient?): RepositoryBuilder<*> {
                this.retrofitClient = retrofitClient
                return this
            }

            fun addApiClass(apiClass: Class<T>?): RepositoryBuilder<*> {
                //网络数据源
                apiNative = apiClass
                return this
            }

            fun addLocalSource(localSource: Class<out LocalDataSource>?): RepositoryBuilder<*> {
                this.localSource = localSource
                return this
            }

            fun build() {
                if (retrofitClient != null && builderHashMap[apiNative!!.name] == null) {
                    apiClass = retrofitClient!!.create(apiNative)
                    builderHashMap[apiNative!!.name] = apiClass
                }
                if (localSource != null && builderHashMap[localSource!!.name] == null) {
                    builderHashMap[localSource!!.name] = localSource
                }
            }
        }
    }
}