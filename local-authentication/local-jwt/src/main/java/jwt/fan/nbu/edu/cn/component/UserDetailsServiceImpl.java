package jwt.fan.nbu.edu.cn.component;

import jwt.fan.nbu.edu.cn.model.UserModel;
import jwt.fan.nbu.edu.cn.service.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/23-10:15
 * @since 1.0
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userService.findUserByUserName(username);
        String[] adminRoles = {"ADMIN","USER"};
        String[] userRoles = {"USER"};
        UserDetails userDetails = null;
        if("admin".equals(user.getUsername())){
            userDetails =  User.withUsername(user.getUsername())
                    .password(user.getPassword())
                    .roles(adminRoles)
                    .build();
            return userDetails;
        }

        userDetails =  User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(userRoles)
                .build();

        return userDetails;
    }
}
