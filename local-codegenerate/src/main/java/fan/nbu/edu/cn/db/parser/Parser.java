package fan.nbu.edu.cn.db.parser;

/**
 * @author laoshi . hua
 * @version 1.0 2022/3/22-3:50 下午
 * @since 1.0
 */
public interface Parser<T> {

    T parse(T t);
}
