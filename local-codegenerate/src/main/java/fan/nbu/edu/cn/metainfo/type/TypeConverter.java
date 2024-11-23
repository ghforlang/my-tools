package fan.nbu.edu.cn.metainfo.type;


import fan.nbu.edu.cn.registry.TypeRegistry;

import java.util.Objects;

/**
 * @author laoshi . hua
 * @version 1.0 2022/3/23-1:40 下午
 * @since 1.0
 */
public class TypeConverter {
    public static FieldTypeWrapper jdbcType2FieldType(JDBCTypeWrapper jdbcType){
        if(Objects.isNull(jdbcType) || Objects.isNull(TypeRegistry.getFieldTypeWrapper(jdbcType.getType()))){
            return null;
        }

        return FieldTypeWrapper.ofFieldTypeWrapper(TypeRegistry.getFieldTypeWrapper(jdbcType.getType()).getType());
    }

    /**
     * TODO
     * @param fieldType
     * @return
     */
    public JDBCTypeWrapper fieldType2JDBCType(FieldTypeWrapper fieldType){
        return JDBCTypeWrapper.ofJDBCTypeWrapper(null);
    }
}
