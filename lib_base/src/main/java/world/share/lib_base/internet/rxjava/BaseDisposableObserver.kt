package world.share.lib_base.internet.rxjava

import world.share.lib_base.internet.http.exception.ApiException
import io.reactivex.observers.DisposableObserver
import world.share.lib_base.internet.http.exception.FactoryException
import world.share.lib_base.internet.http.exception.NetErrorBean

/**
 * 自定义DisposableObserver
 *
 * @author mac
 */
abstract class BaseDisposableObserver<T> constructor() : DisposableObserver<T>() {
    public override fun onError(e: Throwable) {
        val responseThrowable: ApiException? = FactoryException.analysisExcetpion(e)
        val errorBean: NetErrorBean? =
            responseThrowable?.let { NetErrorBean(it.code, responseThrowable!!.message) }
        onError(errorBean)
    }

    fun onError(errorBean: NetErrorBean?) {}
    public override fun onComplete() {}
}