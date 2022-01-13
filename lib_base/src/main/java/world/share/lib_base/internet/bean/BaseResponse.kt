package world.share.lib_base.internet.bean

/**
 * @author mac
 */
class BaseResponse<T> {
    var code = 0
    var message: String
    var result: T? = null
        private set

    constructor(code: Int, message: String) {
        this.code = code
        this.message = message
    }

    constructor(message: String) {
        this.message = message
    }

    fun setResult(result: T) {
        this.result = result
    }

    val isOk: Boolean
        get() = code == 200

}