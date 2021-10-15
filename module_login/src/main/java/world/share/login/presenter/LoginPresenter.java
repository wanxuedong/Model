package world.share.login.presenter;

import androidx.databinding.ViewDataBinding;

import world.share.lib_base.data.impl.InternetDataApi;
import world.share.lib_base.data.inject.Injection;
import world.share.lib_internet.bean.BookBean;
import world.share.lib_internet.http.exception.NetErrorBean;
import world.share.lib_internet.rxjava.BaseDisposableObserver;
import world.share.lib_internet.rxjava.RxUtils;
import world.share.login.view.LoginView;
import world.share.lib_base.mvp.presenter.BasePresenter;

/**
 * @author mac
 */
public class LoginPresenter extends BasePresenter<LoginView> {

    private InternetDataApi loginApiService;

    public LoginPresenter(LoginView view, ViewDataBinding binding) {
        super(view, binding);
        loginApiService = Injection.getInstance().getClass(InternetDataApi.class);
    }

    public void login() {
        addSubscribe(loginApiService.login()
                .compose(RxUtils.schedulersTransformer())
                .doOnSubscribe(disposable -> view.showDialog())
                .subscribeWith(new BaseDisposableObserver<BookBean>() {
                    @Override
                    public void onNext(BookBean o) {
                        view.loginSuccess();
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
