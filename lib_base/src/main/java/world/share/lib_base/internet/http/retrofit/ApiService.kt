package world.share.lib_base.internet.http.retrofit

import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import world.share.lib_base.internet.bean.BaseResponse
import world.share.lib_base.internet.bean.BookBean
import world.share.lib_base.internet.http.RequestUrl

/**
 * @author wan
 * 创建日期：2021/10/15
 * 描述：网络请求接口
 */
interface ApiService {
    /**
     * 登录
     */
    @POST(RequestUrl.login)
    @FormUrlEncoded
    fun login(@Field("username") username: String,
              @Field("password") password: String): Observable<BaseResponse<BookBean>>
}