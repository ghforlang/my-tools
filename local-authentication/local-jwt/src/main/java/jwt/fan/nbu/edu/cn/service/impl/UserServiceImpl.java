package jwt.fan.nbu.edu.cn.service.impl;

import jwt.fan.nbu.edu.cn.model.User;
import jwt.fan.nbu.edu.cn.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/19-15:18
 * @since 1.0
 */
@Component("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private PasswordEncoder passwordEncoder;

    // 没有做持久化，这里使用map模拟
    private static final Map<String, User> userMap = new HashMap<>();



    public User findUserByUserName(String userName) {
        if(StringUtils.isEmpty(userName)){
            return null;
        }
        User user = new User();
        if(Objects.isNull(userMap.get(userName))){
            if("admin".equals(userName)){
                user.setId(1L);
                user.setUsername("admin");
                user.setPassword(passwordEncoder.encode("admin123456"));
                userMap.putIfAbsent(userName,user);
            }else{
                user.setId(2L);
                user.setUsername(userName);
                user.setPassword(passwordEncoder.encode(userName + "123456"));
                userMap.putIfAbsent(userName,user);
            }
        }

        return userMap.get(userName);
    }

    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMap.putIfAbsent(user.getUsername(),user);
        return user;
    }

}
