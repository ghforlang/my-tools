package jwt.fan.nbu.edu.cn.core.handler;

import jwt.fan.nbu.edu.cn.model.ResponseModel;
import jwt.fan.nbu.edu.cn.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/22-18:21
 * @since 1.0
 */
@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.warn("登录成功访问收保护的资源，但是权限不够: ", accessDeniedException);
        ResultUtils.fail(response, ResponseModel.fail("该账号权限不够"));
    }
}
