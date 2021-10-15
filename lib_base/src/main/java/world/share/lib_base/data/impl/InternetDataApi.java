package world.share.lib_base.data.impl;

import io.reactivex.Observable;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import world.share.lib_internet.bean.BaseResponse;
import world.share.lib_internet.bean.BookBean;
import world.share.lib_internet.http.INTERNET_CONSTANT;
import world.share.lib_internet.service.BaseApiService;

/**
 * @author wan
 * 创建日期：2021/10/15
 * 描述：网络请求接口
 */
public interface InternetDataApi{

    /**
     * 登录
     */
    @GET(INTERNET_CONSTANT.login)
    Observable<BaseResponse<BookBean>> login();

}
