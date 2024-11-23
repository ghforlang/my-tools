package jwt.fan.nbu.edu.cn.core.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/22-22:07
 * @since 1.0
 */
public class AuthBusinessException extends AuthenticationException {
    public AuthBusinessException(String msg, Throwable t) {
        super(msg, t);
    }

    public AuthBusinessException(String msg) {
        super(msg);
    }
}
