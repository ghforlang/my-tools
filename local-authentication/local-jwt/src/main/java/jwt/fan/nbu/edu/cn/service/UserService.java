package jwt.fan.nbu.edu.cn.service;

import jwt.fan.nbu.edu.cn.model.User;

/**
* @author laoshi . hua
* @since 1.0 
* @version 1.0 2024/11/23-10:09
*/public interface UserService {

    User findUserByUserName(String userName);


    User save(User user);
}
