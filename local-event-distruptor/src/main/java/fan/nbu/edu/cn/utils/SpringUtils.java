package fan.nbu.edu.cn.utils;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/16-20:27
 * @since 1.0
 */

@Component
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static  <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    public static  <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}
