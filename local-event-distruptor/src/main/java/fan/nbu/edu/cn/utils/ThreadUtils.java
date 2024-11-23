package fan.nbu.edu.cn.utils;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/18-13:51
 * @since 1.0
 */
public class ThreadUtils {

    public static String getCurrentInitBeanName(){
        return Thread.currentThread().getStackTrace()[2].getClassName();
    }
}
