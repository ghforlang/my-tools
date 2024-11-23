package jwt.fan.nbu.edu.cn.core.handler;

import jwt.fan.nbu.edu.cn.model.ResponseModel;
import jwt.fan.nbu.edu.cn.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/22-18:23
 * @since 1.0
 */
@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.warn("用户未登录访问受保护的资源: ", authException);
        if (authException instanceof InsufficientAuthenticationException) {
            ResultUtils.fail(response, HttpStatus.UNAUTHORIZED.value(), ResponseModel.fail("无访问权限,请先登录"));
            return;
        }

        ResultUtils.fail(response, HttpStatus.UNAUTHORIZED.value(), ResponseModel.fail(authException.getMessage()));
    }
}
