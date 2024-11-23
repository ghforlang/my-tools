package jwt.fan.nbu.edu.cn.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/23-23:24
 * @since 1.0
 */
@Getter
@Setter
public class User implements Serializable {
    private Long id;
    private String username;
    private String password;
    /**
     * 0 正常
     */
    private Integer userStatus = 0;
    private Set<String> authorities;
}
