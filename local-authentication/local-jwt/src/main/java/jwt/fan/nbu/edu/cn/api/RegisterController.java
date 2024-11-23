package jwt.fan.nbu.edu.cn.api;

import jwt.fan.nbu.edu.cn.model.RegisterResultVO;
import jwt.fan.nbu.edu.cn.model.ResponseModel;
import jwt.fan.nbu.edu.cn.model.UserModel;
import jwt.fan.nbu.edu.cn.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/23-10:25
 * @since 1.0
 */
@RestController
@RequestMapping("/api/v1/")
public class RegisterController {

    @Resource
    private UserService userService;

//    @PostMapping("/register")
//    @ResponseBody
//    public ResponseModel<RegisterResultVO> register(@RequestBody UserModel user){
//        userService.save(user);
//        return ResponseModel.success(RegisterResultVO.builder().username(user.getUsername()).token("1").build());
//    }
}
