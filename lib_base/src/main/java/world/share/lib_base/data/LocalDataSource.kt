package world.share.lib_base.data

import world.share.lib_base.bean.UserBean

/**
 * @author wan
 * 创建日期：2021/10/15
 * 描述：本地数据提供接口
 */
interface LocalDataSource {

    fun getLocalData()

    fun saveUserData(userBean: UserBean)

}