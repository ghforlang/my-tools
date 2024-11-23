package jwt.fan.nbu.edu.cn.core.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/22-18:02
 * @since 1.0
 */
public class UsernameOrPasswordNullException extends AuthenticationException {

    public UsernameOrPasswordNullException(String msg, Throwable t) {
        super(msg, t);
    }

    public UsernameOrPasswordNullException(String msg) {
        super(msg);
    }
}
