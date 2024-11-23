package jwt.fan.nbu.edu.cn.core.handler;

import jwt.fan.nbu.edu.cn.component.JwtUtils;
import jwt.fan.nbu.edu.cn.model.LoginResVO;
import jwt.fan.nbu.edu.cn.model.RegisterResultVO;
import jwt.fan.nbu.edu.cn.model.ResponseModel;
import jwt.fan.nbu.edu.cn.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/22-18:26
 * @since 1.0
 * @description 自定义认证成功处理器 适用于 form表单登录方式
 */
//@Component
//@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 通过用户名生成 Token
        String username = userDetails.getUsername();
        String token = jwtUtils.generateToken(username);

        // 返回 Token
        LoginResVO loginRspVO = LoginResVO.builder()
                .token(token)
                .username(username)
                .build();

        ResultUtils.success(response, ResponseModel.success(loginRspVO));
    }
}
