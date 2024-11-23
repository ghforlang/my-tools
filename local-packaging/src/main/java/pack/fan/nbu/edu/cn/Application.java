package pack.fan.nbu.edu.cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/13-09:48
 * @since 1.0
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication();
        springApplication.run(Application.class,args);
    }
}
