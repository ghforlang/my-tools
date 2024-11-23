package jwt.fan.nbu.edu.cn.api;

import jwt.fan.nbu.edu.cn.model.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/22-18:41
 * @since 1.0
 */
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class TestController {

    @PostMapping("/list")
    public ResponseModel<?> testList(){
        log.info("用户查询成功...");
        return ResponseModel.success(null);
    }

    @PostMapping("/admin/list")
    public ResponseModel<?> testAdminList(){
        log.info("管理员查询成功...");
        return ResponseModel.success(null);
    }

    @PostMapping("/admin/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseModel<?> testAdminUpdate() {
        log.info("管理员更新成功...");
        return ResponseModel.success(null);
    }
}
