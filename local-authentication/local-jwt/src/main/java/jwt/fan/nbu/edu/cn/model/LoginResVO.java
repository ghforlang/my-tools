package jwt.fan.nbu.edu.cn.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/23-23:06
 * @since 1.0
 */
@Getter
@Setter
@Builder
public class LoginResVO implements Serializable {
    private String username;
    private String token;
}
