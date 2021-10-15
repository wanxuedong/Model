package world.share.lib_base.http;

import io.reactivex.Observable;
import retrofit2.http.GET;
import world.share.lib_internet.bean.BaseResponse;
import world.share.lib_internet.http.INTERNET_CONSTANT;

public interface MvpApiService {

    /**
     * 测试
     */
    @GET(INTERNET_CONSTANT.login)
    Observable<BaseResponse<String>> test();

}
