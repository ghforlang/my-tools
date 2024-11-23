package jwt.fan.nbu.edu.cn.component;

import jwt.fan.nbu.edu.cn.core.exception.AuthBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/22-22:01
 * @since 1.0
 */
@Component
@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = String.valueOf(authentication.getPrincipal());
        String password = String.valueOf(authentication.getCredentials());

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(userDetails != null && StringUtils.isNotBlank(userDetails.getPassword())
                && userDetails.getPassword().equals(password)){
            return new UsernamePasswordAuthenticationToken(username,password,authentication.getAuthorities());
        }
        throw new AuthBusinessException("认证失败!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
