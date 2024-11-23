package jwt.fan.nbu.edu.cn.service.impl;

import jwt.fan.nbu.edu.cn.model.User;
import jwt.fan.nbu.edu.cn.model.UserWrapper;
import jwt.fan.nbu.edu.cn.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/23-23:30
 * @since 1.0
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUserName(username);
        //TODO 其他校验逻辑略
        if(user == null){
            throw new UsernameNotFoundException("用户不存在");
        }

        String[] adminRoles = {"ADMIN","USER"};
        String[] userRoles = {"USER"};
        UserDetails userDetails = null;
        if("admin".equals(user.getUsername())){
            user.setAuthorities(new HashSet<>(Arrays.asList(adminRoles)));
            return userDetails;
        }
        if("user".equals(user.getUsername())){
            user.setAuthorities(new HashSet<>(Arrays.asList(userRoles)));
        }
        return new UserWrapper(user);
    }
}
