package jwt.fan.nbu.edu.cn.core;

import jwt.fan.nbu.edu.cn.component.JwtAuthenticationProvider;
import jwt.fan.nbu.edu.cn.component.JwtUtils;
import jwt.fan.nbu.edu.cn.core.filter.JwtAuthenticationFilter;
import jwt.fan.nbu.edu.cn.core.filter.JwtAuthorizationFilter;
import jwt.fan.nbu.edu.cn.core.handler.CustomAccessDeniedHandler;
import jwt.fan.nbu.edu.cn.core.handler.CustomAuthenticationEntryPoint;
import jwt.fan.nbu.edu.cn.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/19-15:08
 * @since 1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private JwtUtils  jwtUtils;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private JwtAuthenticationSecurityConfig jwtAuthenticationSecurityConfig;
    @Resource
    private CustomAuthenticationEntryPoint authEntryPoint;
    @Resource
    private CustomAccessDeniedHandler deniedHandler;
    @Resource
    private JwtAuthenticationProvider jwtAuthenticationProvider;
    @Resource
    private PasswordEncoder passwordEncoder;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(jwtAuthenticationProvider);
        auth.userDetailsService(userName -> userDetailsService.loadUserByUsername(userName)).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()// 禁用 csrf
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 前后端分离，无需创建会话// 禁用表单登录
//                .apply(jwtAuthenticationSecurityConfig)// 设置用户登录认证相关配置
                .and()
                .authorizeRequests()
                .antMatchers("/admin/**").authenticated() // 认证所有以 /admin 为前缀的 URL 资源
                .antMatchers("/api/v1/login").permitAll()
                .and()
                .antMatcher("/api/v1/register").anonymous()
                .and()
                .httpBasic().authenticationEntryPoint(authEntryPoint) // 处理用户未登录访问受保护的资源的情况
                .and()
                .exceptionHandling().accessDeniedHandler(deniedHandler) // 处理登录成功后访问受保护的资源，但是权限不够的情况
                .and()
                .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);// 将 Token 校验过滤器添加到用户认证过滤器之前
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


//    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
//        JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
//        filter.setAuthenticationManager(authenticationManagerBean());
//        filter.setFilterProcessesUrl("/api/v1/register");
//        return filter;
//    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtils);
    }



}
