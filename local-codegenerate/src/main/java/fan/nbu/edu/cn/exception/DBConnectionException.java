package fan.nbu.edu.cn.exception;

/**
 * @author laoshi . hua
 * @version 1.0 2022/3/18-5:05 下午
 * @since 1.0
 */
public class DBConnectionException extends RuntimeException{
    public DBConnectionException() {
        super();
    }

    public DBConnectionException(String message) {
        super(message);
    }

    public DBConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBConnectionException(Throwable cause) {
        super(cause);
    }

    protected DBConnectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
