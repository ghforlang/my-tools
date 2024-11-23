package pack.fan.nbu.edu.cn.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/13-09:49
 * @since 1.0
 */
@RestController
@RequestMapping("/api/v1/")
public class ApiController {


    @GetMapping("hello")
    public String hello(){
        return "hello world!";
    }
}
