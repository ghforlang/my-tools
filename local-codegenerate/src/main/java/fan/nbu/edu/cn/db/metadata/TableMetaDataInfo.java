package fan.nbu.edu.cn.db.metadata;


import fan.nbu.edu.cn.constants.TableType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author laoshi . hua
 * @version 1.0 2022/3/18-5:37 下午
 * @since 1.0
 */
@Getter
@Setter
public class TableMetaDataInfo {
    private LocalDateTime createTime;
    private String tableName;
    private List<ColumnMetaDataInfo> columnMetaDatas;
    private String comments;
    private TableType tableType;
}
