package fan.nbu.edu.cn.metainfo.type;



import fan.nbu.edu.cn.constants.TypeEnum;

import java.sql.JDBCType;

/**
 * @author laoshi . hua
 * @version 1.0 2022/3/22-6:01 下午
 * @since 1.0
 */
public class JDBCTypeWrapper implements TypeWrapper<JDBCType> {

    private JDBCType type;

    public JDBCTypeWrapper(JDBCType type) {
        this.type = type;
    }


    @Override
    public JDBCType getType() {
        return type;
    }

    @Override
    public TypeEnum getTypeCategory() {
        return TypeEnum.JDBCTYPE;
    }

    @Override
    public String getTypeName() {
        return type.getName();
    }

    public static JDBCTypeWrapper ofJDBCTypeWrapper(JDBCType type){
        return new JDBCTypeWrapper(type);
    }
}
