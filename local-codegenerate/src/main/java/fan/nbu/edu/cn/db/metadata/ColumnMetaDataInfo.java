package fan.nbu.edu.cn.db.metadata;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;
import java.sql.JDBCType;

/**
 * @author laoshi . hua
 * @version 1.0 2022/3/18-5:38 下午
 * @since 1.0
 */
@Getter
@Setter
public class ColumnMetaDataInfo implements Comparable<ColumnMetaDataInfo> {
    private String tableName;
    private String columnName;
    private String columnDefault;
    private boolean nullable;
    private JDBCType columnJdbcType;
    private String comments;
    private Integer order;
    private Type columnType;

    @Override
    public int compareTo(ColumnMetaDataInfo o) {
        return this.order - o.order;
    }
}
