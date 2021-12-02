package world.share.lib_base.data

import io.reactivex.Observable
import world.share.lib_base.bean.UserBean
import world.share.lib_base.internet.bean.BaseResponse
import world.share.lib_base.internet.bean.BookBean
import world.share.lib_base.mvvm.viewmodel.BaseModel

/**
 * @author wan
 * @Date 2021/11/24
 * 描述：数据中心（本地+在线） 外部通过Koin依赖注入调用
 */
class DataRepository constructor(
    private val localDataSource: LocalDataSource,
    private val httpDataSource: HttpDataSource
) : BaseModel(), LocalDataSource, HttpDataSource {

    override fun getLocalData() {
        localDataSource.getLocalData()
    }

    override fun saveUserData(userBean: UserBean) {
        localDataSource.saveUserData(userBean)
    }

    override fun login(account: String, pwd: String): Observable<BaseResponse<BookBean>> {
        return httpDataSource.login(account, pwd)
    }

    override fun userLogin(account: String, pwd: String): Observable<BaseResponse<UserBean>> {
        return httpDataSource.userLogin(account, pwd)
    }

}