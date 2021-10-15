package world.share.lib_internet.http.exception;


/**
 * @author walle
 * 自定义异常
 */
public class DefinedException extends RuntimeException {
    private int code;
    private String msg;

    public DefinedException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 注意区分使用 getMessage()
     * @return  string
     */
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
