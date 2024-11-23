package jwt.fan.nbu.edu.cn.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/19-14:59
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
public class UserWrapper implements UserDetails, Serializable {

    private User user;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * TODO 先默认未过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * TODO 默认未锁
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * TODO 默认未过期
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * TODO 用户是否可用，先默认可用
     * @return
     */
    @Override
    public boolean isEnabled() {
        return user.getUserStatus() == 0;
    }
}
