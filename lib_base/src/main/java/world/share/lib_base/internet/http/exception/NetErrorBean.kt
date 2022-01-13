package world.share.lib_base.internet.http.exception

/**
 * @author mac
 */
class NetErrorBean(var code: Int, private var message: String?) {
    fun getMessage(): String {
        return if (message == null) "" else message!!
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    override fun toString(): String {
        return "NetErrorBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}'
    }
}