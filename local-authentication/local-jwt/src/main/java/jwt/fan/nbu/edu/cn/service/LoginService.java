package jwt.fan.nbu.edu.cn.service;

import jwt.fan.nbu.edu.cn.model.LoginResVO;
import jwt.fan.nbu.edu.cn.model.ResponseModel;
import jwt.fan.nbu.edu.cn.model.User;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/23-23:58
 * @since 1.0
 */
public interface LoginService {

    ResponseModel<LoginResVO> login(User user);
}
