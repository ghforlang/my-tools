package fan.nbu.edu.cn.registry;

import fan.nbu.edu.cn.metainfo.type.FieldTypeWrapper;
import fan.nbu.edu.cn.metainfo.type.JDBCTypeWrapper;
import fan.nbu.edu.cn.metainfo.type.TypeWrapper;

import java.math.BigInteger;
import java.sql.JDBCType;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.sql.JDBCType.*;

/**
 * @author laoshi . hua
 * @version 1.0 2022/3/22-5:28 下午
 * @since 1.0
 */
public class TypeRegistry {

    private static Map<Class<?>, FieldTypeWrapper> fieldTypeRegistryMap = new HashMap<>();
    private static Map<JDBCType, TypeWrapper> jdbcTypeRegistryMap = new HashMap<>();
    private static Map<JDBCType, FieldTypeWrapper> jdbcType2FieldTypeMap = new HashMap<>();

    static{
        // 属性类型注册
        registerFieldType();
        // jdbc类型注册
        registerJdbcType();
        // jdbc类型 -> field类型
        initJDBCType2FieldTypeMap();
    }

    private static void registerFieldType(){
        fieldTypeRegistryMap.put(String.class, FieldTypeWrapper.ofFieldTypeWrapper(String.class));
        fieldTypeRegistryMap.put(Integer.class, FieldTypeWrapper.ofFieldTypeWrapper(Integer.class));
        fieldTypeRegistryMap.put(Long.class, FieldTypeWrapper.ofFieldTypeWrapper(Long.class));
        fieldTypeRegistryMap.put(Short.class, FieldTypeWrapper.ofFieldTypeWrapper(Short.class));
        fieldTypeRegistryMap.put(BigInteger.class, FieldTypeWrapper.ofFieldTypeWrapper(BigInteger.class));
        fieldTypeRegistryMap.put(Date.class, FieldTypeWrapper.ofFieldTypeWrapper(Date.class));
        fieldTypeRegistryMap.put(LocalDateTime.class, FieldTypeWrapper.ofFieldTypeWrapper(LocalDateTime.class));
        fieldTypeRegistryMap.put(Double.class,FieldTypeWrapper.ofFieldTypeWrapper(Double.class));
        fieldTypeRegistryMap.put(Float.class,FieldTypeWrapper.ofFieldTypeWrapper(Float.class));
        fieldTypeRegistryMap.put(Character.class,FieldTypeWrapper.ofFieldTypeWrapper(Character.class));
        fieldTypeRegistryMap.put(byte[].class,FieldTypeWrapper.ofFieldTypeWrapper(byte[].class));
        fieldTypeRegistryMap.put(Boolean.TYPE,FieldTypeWrapper.ofFieldTypeWrapper(Boolean.class));
        fieldTypeRegistryMap.put(Byte.TYPE,FieldTypeWrapper.ofFieldTypeWrapper(Byte.class));
        fieldTypeRegistryMap.put(Character.TYPE,FieldTypeWrapper.ofFieldTypeWrapper(Character.class));
        fieldTypeRegistryMap.put(Double.TYPE,FieldTypeWrapper.ofFieldTypeWrapper(Double.class));
        fieldTypeRegistryMap.put(Float.TYPE,FieldTypeWrapper.ofFieldTypeWrapper(Float.class));
        fieldTypeRegistryMap.put(Integer.TYPE,FieldTypeWrapper.ofFieldTypeWrapper(Integer.class));
        fieldTypeRegistryMap.put(Long.TYPE,FieldTypeWrapper.ofFieldTypeWrapper(Long.class));
        fieldTypeRegistryMap.put(Short.TYPE,FieldTypeWrapper.ofFieldTypeWrapper(Short.class));
        fieldTypeRegistryMap.put(Void.TYPE,FieldTypeWrapper.ofFieldTypeWrapper(Void.class));

    }

    private static void registerJdbcType(){
        for(JDBCType jdbcType : JDBCType.values()){
            jdbcTypeRegistryMap.put(jdbcType, JDBCTypeWrapper.ofJDBCTypeWrapper(jdbcType));
//            System.out.println("jdbcType2FieldTypeMap.put(" + jdbcType.getName() + "," + " );");
        }

    }


    private static void initJDBCType2FieldTypeMap() {
        jdbcType2FieldTypeMap.put(BIT, fieldTypeRegistryMap.get(Long.class));
        jdbcType2FieldTypeMap.put(TINYINT, fieldTypeRegistryMap.get(Integer.class));
        jdbcType2FieldTypeMap.put(SMALLINT, fieldTypeRegistryMap.get(Integer.class));
        jdbcType2FieldTypeMap.put(INTEGER, fieldTypeRegistryMap.get(Integer.class));
        jdbcType2FieldTypeMap.put(BIGINT, fieldTypeRegistryMap.get(Long.class));
        jdbcType2FieldTypeMap.put(FLOAT, fieldTypeRegistryMap.get(Float.class));
        jdbcType2FieldTypeMap.put(DOUBLE, fieldTypeRegistryMap.get(Double.class));
        jdbcType2FieldTypeMap.put(DECIMAL,fieldTypeRegistryMap.get(Float.class) );
        jdbcType2FieldTypeMap.put(CHAR, fieldTypeRegistryMap.get(Character.class));
        jdbcType2FieldTypeMap.put(VARCHAR, fieldTypeRegistryMap.get(String.class));
        jdbcType2FieldTypeMap.put(LONGVARCHAR, fieldTypeRegistryMap.get(String.class));
        jdbcType2FieldTypeMap.put(DATE, fieldTypeRegistryMap.get(LocalDateTime.class));
        jdbcType2FieldTypeMap.put(TIME, fieldTypeRegistryMap.get(LocalDateTime.class));
        jdbcType2FieldTypeMap.put(TIMESTAMP, fieldTypeRegistryMap.get(LocalDateTime.class));
        jdbcType2FieldTypeMap.put(BINARY, fieldTypeRegistryMap.get(byte[].class));
        jdbcType2FieldTypeMap.put(BOOLEAN,fieldTypeRegistryMap.get(Boolean.class) );
        jdbcType2FieldTypeMap.put(TIME_WITH_TIMEZONE, fieldTypeRegistryMap.get(LocalDateTime.class));
        jdbcType2FieldTypeMap.put(TIMESTAMP_WITH_TIMEZONE, fieldTypeRegistryMap.get(LocalDateTime.class));
        jdbcType2FieldTypeMap.put(VARBINARY, fieldTypeRegistryMap.get(Long.class));
    }


    public static FieldTypeWrapper getFieldTypeWrapper( JDBCType jdbcType){
        return jdbcType2FieldTypeMap.get(jdbcType);
    }
}
