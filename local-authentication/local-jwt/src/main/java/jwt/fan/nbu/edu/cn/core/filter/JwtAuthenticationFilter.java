package jwt.fan.nbu.edu.cn.core.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jwt.fan.nbu.edu.cn.model.UserModel;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/19-15:12
 * @since 1.0
 * @descriptin jwt认证过滤器;
 */
@Component
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    @Resource
    private UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(){
        super(new AntPathRequestMatcher("/api/v1/register", "POST"));
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserModel creds = new ObjectMapper().readValue(request.getInputStream(), UserModel.class);
            UserDetails userDetails = userDetailsService.loadUserByUsername(creds.getUsername());
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDetails.getUsername(),
                            userDetails.getPassword(),
                            userDetails.getAuthorities())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        String username =  String.valueOf(authResult.getPrincipal());
//        String password = String.valueOf(authResult.getCredentials());
//        if (username == null || password == null) {
//            throw new UsernameOrPasswordNullException("用户名或密码为空");
//        }
//
////        UserModel userModel = new UserModel();
////        userModel.setUsername(username);
////        userModel.setPassword(password);
////        userModel.setAuthorities((Set)authResult.getAuthorities());
////        userService.save(userModel);
//
//        String token = jwtUtils.generateToken(username);
//        RegisterResultVO loginResVO = RegisterResultVO.builder()
//                .username(username)
//                .token(token)
//                .build();
//        response.addHeader("Authorization", "Bearer_" + token);
//        ResultUtils.success(response, ResponseModel.success(loginResVO));
//    }
}
