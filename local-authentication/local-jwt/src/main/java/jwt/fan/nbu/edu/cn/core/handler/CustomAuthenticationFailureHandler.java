package jwt.fan.nbu.edu.cn.core.handler;

import jwt.fan.nbu.edu.cn.core.exception.UsernameOrPasswordNullException;
import jwt.fan.nbu.edu.cn.model.ResponseModel;
import jwt.fan.nbu.edu.cn.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/22-17:58
 * @since 1.0
 */
@Component
@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.warn("登录失败,{}",exception.getMessage());
        if (exception instanceof UsernameOrPasswordNullException) {
            // 用户名或密码为空
            ResultUtils.fail(response, ResponseModel.fail(exception.getMessage()));
        } else if (exception instanceof BadCredentialsException) {
            // 用户名或密码错误
            ResultUtils.fail(response, ResponseModel.fail("用户名或密码错误"));
        }
        ResultUtils.fail(response, ResponseModel.fail("登录失败"));
    }
}
