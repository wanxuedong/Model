package world.share.lib_internet.http.exception;

import androidx.annotation.Nullable;

/**
 * @author mac
 */
public class ResponseThrowable extends Exception {
    public int code;
    public String message;

    public int getCode() {
        return code;
    }

    @Nullable
    @Override
    public String getMessage() {
        return message;
    }

    public ResponseThrowable(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}
