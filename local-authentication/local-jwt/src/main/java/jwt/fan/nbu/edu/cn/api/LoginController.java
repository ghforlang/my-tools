package jwt.fan.nbu.edu.cn.api;

import jwt.fan.nbu.edu.cn.model.LoginResVO;
import jwt.fan.nbu.edu.cn.model.ResponseModel;
import jwt.fan.nbu.edu.cn.model.User;
import jwt.fan.nbu.edu.cn.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/23-23:55
 * @since 1.0
 */
@RestController
@RequestMapping("/api/v1/")
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    @ResponseBody
    public ResponseModel<LoginResVO> login(@RequestBody User user){
        return loginService.login(user);
    }
}
