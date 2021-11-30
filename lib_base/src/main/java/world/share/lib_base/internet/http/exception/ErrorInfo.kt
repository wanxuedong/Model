package world.share.lib_base.internet.http.exception

/**
 * @author walle
 * 网络请求状态码
 */
interface ErrorInfo {
    companion object {
        const val SUCCESS = 200
        const val HTTP_ERROR = -1
        const val HTTP_ERROR_MSG = "请求错误"
        const val UNKNOWN_HOST_ERROR = -2
        const val UNKNOWN_HOST_ERROR_MSG = "连接失败"
        const val CONNECT_ERROR = -3
        const val CONNECT_ERROR_MSG = "连接错误"
        const val SOCKET_TIMEOUT_ERROR = -4
        const val SOCKET_TIMEOUT_ERROR_MSG = "连接超时,请检查网络"
        const val JSON_ERROR = -5
        const val JSON_ERROR_MSG = "json解析错误"
        const val NET_ERROR = -6
        const val NET_ERROR_MSG = "网络连接失败,请检查网络设置"
        const val SSL_ERROR = -7
        const val SSL_ERROR_MSG = "证书验证失败"
        const val RX_MERGE_REEOR = -8
        const val RX_MERGE_REEOR_MSG = "rxjava合并请求出错"
        const val UNKNOWN_ERROR_MSG = "未知错误"

        /**
         * 后台约定数据,退出登录
         */
        const val EXIT = 999
        const val EXIT_MSG = "退出登录"

        /**token过期 */
        const val TOKEN_ERROR = 888
        const val ERROR = 422
        const val ERROR_WITH = 361 //提现未绑定手机号
        const val WITHDRAW_READ_ERROR = 362 //提现阅读数不够
        const val WITHDRAW_SHARE_ERROR = 363 // 提现分享阅读不够

        //退出登录不应该使用已有的错误码
        const val LOGON_INVALIDATION_ERROR_401 = 401
        const val LOGON_INVALIDATION_ERROR_403 = 403
        const val LOGON_INVALIDATION_ERROR_MSG = "登录失效,请重新登录"
    }
}