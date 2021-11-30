package world.share.lib_base.internet.http.exception

/**
 * 错误工厂
 *
 * @author mac
 */
object FactoryException {
    /**
     * 封装错误
     *
     * @param e 错误
     * @return ApiException    自定义错误
     */
    fun analysisExcetpion(e: Throwable?): ApiException {
        return ApiException(e)
    }
}