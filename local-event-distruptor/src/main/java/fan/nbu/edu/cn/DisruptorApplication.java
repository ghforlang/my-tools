package fan.nbu.edu.cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/16-20:01
 * @since 1.0
 */
@SpringBootApplication(scanBasePackages = {"fan.nbu.edu.cn"})
public class DisruptorApplication {

    public static void main(String[] args) {
        new SpringApplication().run(DisruptorApplication.class,args);
    }
}
