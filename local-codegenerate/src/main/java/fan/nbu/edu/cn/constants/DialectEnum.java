package fan.nbu.edu.cn.constants;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author laoshi . hua
 * @version 1.0 2022/3/18-2:55 下午
 * @since 1.0
 */
@Getter
public enum DialectEnum {
    Mysql("mysql","com.mysql.jdbc.Driver",com.mysql.jdbc.Driver.class,"5.7"),
    Postgresql("postgresql","org.postgresql.Driver",org.postgresql.Driver.class,"11.8");
    ;



    private String dialect;
    private String driverClassName;
    private Class<?> DriverClass;
    private String version;

    public static final Map<String,DialectEnum> dialectMap = new HashMap<>();

    static{
        for(DialectEnum dialectEnum : DialectEnum.values()){
            dialectMap.putIfAbsent(dialectEnum.dialect,dialectEnum);
        }
    }

    DialectEnum(String dialect, String driverClassName, Class<?> driverClass, String version) {
        this.dialect = dialect;
        this.driverClassName = driverClassName;
        DriverClass = driverClass;
        this.version = version;
    }


    public static DialectEnum getByDialect(String dialect){
        return StringUtils.isBlank(dialect) ? null : dialectMap.get(dialect);
    }
}
