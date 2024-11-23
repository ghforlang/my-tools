package fan.nbu.edu.cn.metainfo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author laoshi . hua
 * @version 1.0 2022/3/18-2:44 下午
 * @since 1.0
 */
@Getter
@Setter
public class ResultSQL {
    private String tableName;
    private String dbName;
    private SQLScript script;
}
