package world.share.lib_internet.rxjava;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import world.share.lib_internet.bean.BaseResponse;
import world.share.lib_internet.http.exception.DefinedException;
import world.share.lib_internet.http.exception.ErrorInfo;
import world.share.lib_internet.http.exception.ExceptionHandle;

/**
 * 有关Rx的工具类
 *
 * @author mac
 */
public class RxUtils {
    /**
     * 生命周期绑定
     *
     * @param lifecycle Activity
     */
    public static <T> LifecycleTransformer<T> bindToLifecycle(@NonNull Context lifecycle) {
        if (lifecycle instanceof LifecycleProvider) {
            return ((LifecycleProvider) lifecycle).bindToLifecycle();
        } else {
            throw new IllegalArgumentException("context not the LifecycleProvider type");
        }
    }

    /**
     * 生命周期绑定
     *
     * @param lifecycle Fragment
     */
    public static LifecycleTransformer bindToLifecycle(@NonNull Fragment lifecycle) {
        if (lifecycle instanceof LifecycleProvider) {
            return ((LifecycleProvider) lifecycle).bindToLifecycle();
        } else {
            throw new IllegalArgumentException("fragment not the LifecycleProvider type");
        }
    }

    /**
     * 生命周期绑定
     *
     * @param lifecycle Fragment
     */
    public static LifecycleTransformer bindToLifecycle(@NonNull LifecycleProvider lifecycle) {
        return lifecycle.bindToLifecycle();
    }

    /**
     * 线程调度器
     */
    public static <T> ObservableTransformer<BaseResponse<T>, T> schedulersTransformer() {
        return upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).flatMap(new Function<BaseResponse<T>, ObservableSource<? extends T>>() {
            @Override
            public ObservableSource<? extends T> apply(BaseResponse<T> tHttpResult) throws Exception {
                if (tHttpResult.getCode() == ErrorInfo.SUCCESS) {
                    return Observable.just(tHttpResult.getResult());
                } else {
                    return Observable.error(new DefinedException(tHttpResult.getCode(), tHttpResult.getMessage()));
                }

            }
        });
    }

    /**
     * 线程调度器
     */
    public static <T> ObservableTransformer<T, T> schedulersOther() {
        return upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).flatMap(new Function<T, ObservableSource<? extends T>>() {
            @Override
            public ObservableSource<? extends T> apply(T tHttpResult) throws Exception {
                return Observable.just(tHttpResult);

            }
        });
    }

    public static ObservableTransformer exceptionTransformer() {

        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable observable) {
                return observable
//                        .map(new HandleFuc<T>())  //这里可以取出BaseResponse中的Result
                        .onErrorResumeNext(new HttpResponseFunc());
            }
        };
    }

    private static class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {
        @Override
        public Observable<T> apply(Throwable t) {
            return Observable.error(ExceptionHandle.handleException(t));
        }
    }

    private static class HandleFuc<T> implements Function<BaseResponse<T>, T> {
        @Override
        public T apply(BaseResponse<T> response) {
            if (!response.isOk()) {
                throw new RuntimeException(!"".equals(response.getCode() + "" + response.getMessage()) ? response.getMessage() : "");
            }
            return response.getResult();
        }
    }

}
