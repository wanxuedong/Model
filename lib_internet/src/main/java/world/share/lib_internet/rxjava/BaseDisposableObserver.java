package world.share.lib_internet.rxjava;


import io.reactivex.observers.DisposableObserver;
import world.share.lib_internet.http.exception.ApiException;
import world.share.lib_internet.http.exception.FactoryException;
import world.share.lib_internet.http.exception.NetErrorBean;


/**
 * 自定义DisposableObserver
 *
 * @author mac
 */
public abstract class BaseDisposableObserver<T> extends DisposableObserver<T> {

    @Override
    public final void onError(Throwable e) {
        ApiException responseThrowable = FactoryException.analysisExcetpion(e);
        NetErrorBean errorBean = new NetErrorBean(responseThrowable.getCode(), responseThrowable.getMessage());
        onError(errorBean);
    }

    public void onError(NetErrorBean errorBean) {
    }

    @Override
    public void onComplete() {
    }
}
