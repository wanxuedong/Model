package world.share.lib_base.internet.http.exception

/**
 * @author walle
 * 自定义异常
 */
class DefinedException(
    var code: Int,
    /**
     * 注意区分使用 getMessage()
     * @return  string
     */
    var msg: String?
) : RuntimeException()