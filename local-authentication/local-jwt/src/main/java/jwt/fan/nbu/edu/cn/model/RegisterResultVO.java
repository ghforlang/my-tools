package jwt.fan.nbu.edu.cn.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/22-18:31
 * @since 1.0
 */
@Getter
@Setter
@Builder
public class RegisterResultVO {
    private String username;
    private String token;
}
