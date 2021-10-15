package world.share.lib_internet.http.exception;


/**
 * @author walle
 * 网络请求状态码
 */
public interface ErrorInfo {

    int SUCCESS = 200;

    int HTTP_ERROR = -1;
    String HTTP_ERROR_MSG = "请求错误";

    int UNKNOWN_HOST_ERROR = -2;
    String UNKNOWN_HOST_ERROR_MSG = "连接失败";

    int CONNECT_ERROR = -3;
    String CONNECT_ERROR_MSG = "连接错误";

    int SOCKET_TIMEOUT_ERROR = -4;
    String SOCKET_TIMEOUT_ERROR_MSG = "连接超时,请检查网络";

    int JSON_ERROR = -5;
    String JSON_ERROR_MSG = "json解析错误";

    int NET_ERROR = -6;
    String NET_ERROR_MSG = "网络连接失败,请检查网络设置";

    int SSL_ERROR = -7;
    String SSL_ERROR_MSG = "证书验证失败";

    int RX_MERGE_REEOR = -8;
    String RX_MERGE_REEOR_MSG = "rxjava合并请求出错";

    String UNKNOWN_ERROR_MSG = "未知错误";

    /**
     * 后台约定数据,退出登录
     */
    int EXIT = 999;

    String EXIT_MSG = "退出登录";

    /**token过期*/
    int TOKEN_ERROR = 888;

    int ERROR = 422;

    int ERROR_WITH=361;//提现未绑定手机号

    int WITHDRAW_READ_ERROR = 362;//提现阅读数不够
    int WITHDRAW_SHARE_ERROR = 363;// 提现分享阅读不够

    //退出登录不应该使用已有的错误码
    int LOGON_INVALIDATION_ERROR_401 = 401;
    int LOGON_INVALIDATION_ERROR_403 = 403;
    String LOGON_INVALIDATION_ERROR_MSG = "登录失效,请重新登录";

}
