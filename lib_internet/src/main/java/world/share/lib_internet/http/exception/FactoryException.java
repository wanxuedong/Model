package world.share.lib_internet.http.exception;


/**
 * 错误工厂
 *
 * @author mac
 */
public class FactoryException {

    /**
     * 封装错误
     *
     * @param e 错误
     * @return ApiException    自定义错误
     */
    public static ApiException analysisExcetpion(Throwable e) {
        ApiException apiException = new ApiException(e);
        return apiException;
    }
}
