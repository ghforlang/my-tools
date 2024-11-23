package jwt.fan.nbu.edu.cn.service.impl;

import jwt.fan.nbu.edu.cn.component.JwtUtils;
import jwt.fan.nbu.edu.cn.model.LoginResVO;
import jwt.fan.nbu.edu.cn.model.ResponseModel;
import jwt.fan.nbu.edu.cn.model.User;
import jwt.fan.nbu.edu.cn.model.UserWrapper;
import jwt.fan.nbu.edu.cn.service.LoginService;
import jwt.fan.nbu.edu.cn.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/23-23:59
 * @since 1.0
 */
@Component
public class LoginServiceImpl implements LoginService {


    @Resource
    private UserService userService;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private JwtUtils jwtUtils;

    @Override
    public ResponseModel<LoginResVO> login(User user) {
        // 1、TODO用户信息合法性校验 略
        // 2、用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authentication)){
            throw new AuthenticationServiceException("登录失败!");
        }

        UserWrapper userWrapper = (UserWrapper)authentication.getPrincipal();
        // 3、TODO 生成token
        String token = jwtUtils.generateToken(userWrapper.getUsername());
        // 4、存入缓存，略
        return ResponseModel.success(LoginResVO.builder().token(token).username(userWrapper.getUsername()).build());
    }
}
