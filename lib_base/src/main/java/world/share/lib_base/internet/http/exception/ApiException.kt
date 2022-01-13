package world.share.lib_base.internet.http.exception

import java.lang.Exception

/**
 * @author walle
 * 接口异常判断处理
 */
class ApiException : Exception {
    var code = 0
    override var message: String? = null

    constructor(cause: Throwable?) : super(cause) {}
    constructor(cause: Throwable?, code: Int, message: String?) : super(message, cause) {
        this.code = code
        this.message = message
    }

    @JvmName("setMessage1")
    fun setMessage(message: String?) {
        this.message = message
    }
}