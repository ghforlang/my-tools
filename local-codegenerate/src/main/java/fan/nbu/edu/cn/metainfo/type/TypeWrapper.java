package fan.nbu.edu.cn.metainfo.type;


import fan.nbu.edu.cn.constants.TypeEnum;

/**
 * @author laoshi . hua
 * @version 1.0 2022/3/22-5:31 下午
 * @since 1.0
 */
public interface TypeWrapper<T> {

    T getType();

    TypeEnum getTypeCategory();

    String getTypeName();
}
