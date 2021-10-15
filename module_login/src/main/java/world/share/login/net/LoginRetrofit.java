package world.share.login.net;

import android.app.Application;

import world.share.lib_internet.http.retrofit.BaseRetrofit;

public class LoginRetrofit extends BaseRetrofit {
    public LoginRetrofit(Application application) {
        super(application);
    }
}
