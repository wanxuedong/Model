package world.share.lib_base.internet.rxjava

import android.net.ParseException
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.kingja.loadsir.core.LoadService
import io.reactivex.observers.DisposableObserver
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import org.koin.core.component.KoinComponent
import retrofit2.HttpException
import world.share.lib_base.internet.bean.BaseResponse
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @author Alwyn
 * @Date 2020/10/10
 * @Description RxJava 处理Api异常
 * 不自动处理状态页的不传构造即可
 */
abstract class ApiSubscriberHelper<T>(private val loadService: LoadService<BaseResponse<*>?>? = null) :
    DisposableObserver<T>(), KoinComponent {

    override fun onNext(t: T) {
        if (t is BaseResponse<*>) {
            loadService?.showWithConvertor(t)
        }
        onSuccess(t)
    }

    override fun onComplete() {}

    override fun onError(throwable: Throwable) {
        loadService?.showWithConvertor(null)
        if (throwable is ConnectException || throwable is ConnectTimeoutException || throwable is UnknownHostException) {
            onError("连接失败，请检查网络后再试")
        } else if (throwable is RuntimeException) {
            onError(throwable.message)
        } else if (throwable is SocketTimeoutException) {
            onError("连接超时，请重试")
        } else if (throwable is IllegalStateException) {
            onError(throwable.message)
        } else if (throwable is HttpException) {
            onError("网络异常，请重试")
        } else if (throwable is JsonParseException
            || throwable is JSONException
            || throwable is JsonSyntaxException
            || throwable is ParseException
        ) {
            onError("数据解析异常，请稍候再试")
        } else {
            onError(throwable.message)
        }
    }

    protected abstract fun onSuccess(t: T)
    protected abstract fun onError(msg: String?)
}