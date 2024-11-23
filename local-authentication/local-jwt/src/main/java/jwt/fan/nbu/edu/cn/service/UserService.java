package jwt.fan.nbu.edu.cn.service;

import jwt.fan.nbu.edu.cn.model.UserModel;

/**
* @author laoshi . hua
* @since 1.0 
* @version 1.0 2024/11/23-10:09
*/public interface UserService {

    UserModel findUserByUserName(String userName);


    UserModel save(UserModel user);
}
