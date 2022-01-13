package world.share.lib_base.internet.http.exception

/**
 * @author mac
 */
class ResponseThrowable(throwable: Throwable?, var code: Int) : Exception(throwable) {
    override var message: String? = null

}