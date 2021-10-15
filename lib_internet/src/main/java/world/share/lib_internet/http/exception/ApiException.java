package world.share.lib_internet.http.exception;


/**
 * @author walle
 * 接口异常判断处理
 */
public class ApiException extends Exception {
    private int code;
    private String message;

    public ApiException(Throwable cause) {
        super(cause);
    }


    public ApiException(Throwable cause, int code, String message) {
        super(message,cause);
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
