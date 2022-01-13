package world.share.lib_base.data

import io.reactivex.Observable
import world.share.lib_base.bean.UserBean
import world.share.lib_base.internet.bean.BaseResponse
import world.share.lib_base.internet.bean.BookBean

/**
 * @author Alwyn
 * @Date 2020/7/22
 * @Description
 */
interface HttpDataSource {

    fun login(account: String, pwd: String): Observable<BaseResponse<BookBean>>

    fun userLogin(account: String, pwd: String): Observable<BaseResponse<UserBean>>

}