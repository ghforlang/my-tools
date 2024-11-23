package jwt.fan.nbu.edu.cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.Filter;
import java.util.List;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/19-14:55
 * @since 1.0
 */
@SpringBootApplication
public class JwtApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context =  SpringApplication.run(JwtApplication.class,args);
        System.out.println("启动成功!");
        //打印加载的SecurityFilter
//        List<Filter> filters = context.getBean(DefaultSecurityFilterChain.class).getFilters();
//        for (Filter filter : filters) {
//            System.out.println(filter.getClass().getName());
//        }
    }
}
