package world.share.lib_base.mvp.presenter;

import androidx.databinding.ViewDataBinding;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import world.share.lib_internet.local.BaseLocalData;
import world.share.lib_internet.service.BaseApiService;
import world.share.lib_base.mvp.BaseView;

/**
 * @author mac
 * mvp的P层
 */
public class BasePresenter<T extends BaseView>{

    protected T view;

    public ViewDataBinding binding;
    /**
     * 管理RxJava，主要针对RxJava异步操作造成的内存泄漏
     **/
    private CompositeDisposable mCompositeDisposable;

    public BasePresenter(T view,ViewDataBinding binding){
        this.view = view;
        this.binding = binding;
        mCompositeDisposable = new CompositeDisposable();
    }

    public BasePresenter(Class<? extends BaseApiService> apiClass, Class<? extends BaseLocalData> localSource){
        mCompositeDisposable = new CompositeDisposable();
    }

    /**
     * 开始任务
     * **/
    public void addSubscribe(Disposable disposable){
        mCompositeDisposable.add(disposable);
    }

    /**
     * 取消任务
     * **/
    public void cancel() {
        //ViewModel销毁时会执行，同时取消所有异步任务
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
}
