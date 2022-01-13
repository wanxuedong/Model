package world.share.lib_base.data.impl

import io.reactivex.Observable
import world.share.lib_base.bean.UserBean
import world.share.lib_base.data.HttpDataSource
import world.share.lib_base.internet.bean.BaseResponse
import world.share.lib_base.internet.bean.BookBean
import world.share.lib_base.internet.http.retrofit.ApiService

/**
 * @author wan
 * 创建日期：2021/10/15
 * 描述：网络请求接口
 */
class HttpDataImpl(private val apiService: ApiService) : HttpDataSource {

    override fun login(account: String, pwd: String): Observable<BaseResponse<BookBean>> {
        return apiService.login(account, pwd)
    }

    override fun userLogin(account: String, pwd: String): Observable<BaseResponse<UserBean>> {
        return apiService.pwdLogin(account, pwd)
    }
}