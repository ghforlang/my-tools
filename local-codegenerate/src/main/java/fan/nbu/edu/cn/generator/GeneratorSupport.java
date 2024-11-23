package fan.nbu.edu.cn.generator;

import lombok.extern.slf4j.Slf4j;

/**
 * @author laoshi . hua
 * @version 1.0 2022/4/2-4:47 PM
 * @since 1.0
 */

@Slf4j
public class GeneratorSupport{

    
    private JavaCodeGenerator javaCodeGenerator;

    public void afterPropertiesSet() throws Exception {
        System.out.println("begin to generate pojo !");
        javaCodeGenerator.generator("sucess!");
        System.out.println("generate pojo success!");
    }
}
