package fan.nbu.edu.cn.exception;

/**
 * @author laoshi . hua
 * @version 1.0 2022/3/22-3:57 下午
 * @since 1.0
 */
public class IllegalNameException extends RuntimeException{
    public IllegalNameException() {
        super();
    }

    public IllegalNameException(String message) {
        super(message);
    }

    public IllegalNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalNameException(Throwable cause) {
        super(cause);
    }

    protected IllegalNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
