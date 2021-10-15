package world.share.lib_base.mvp.presenter;

import androidx.databinding.ViewDataBinding;

import world.share.lib_base.data.inject.Injection;
import world.share.lib_internet.http.exception.NetErrorBean;
import world.share.lib_internet.rxjava.BaseDisposableObserver;
import world.share.lib_internet.rxjava.RxUtils;
import world.share.lib_base.mvp.BaseView;
import world.share.lib_base.http.MvpApiService;

public class PresenterImpl extends BasePresenter<BaseView> {

    private MvpApiService mvpApiService;

    public PresenterImpl(BaseView view, ViewDataBinding binding) {
        super(view, binding);
        mvpApiService = Injection.getInstance().getClass(MvpApiService.class);
    }

    public void login(){
        addSubscribe(mvpApiService.test()
                .compose(RxUtils.schedulersOther())
                .doOnSubscribe(disposable -> view.showDialog())
                .subscribeWith(new BaseDisposableObserver<Object>() {
                    @Override
                    public void onNext(Object o) {
                    }

                    @Override
                    public void onError(NetErrorBean errorBean) {
                        super.onError(errorBean);
                        view.disMissDialog();
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        view.disMissDialog();
                    }
                }));
    }

}
