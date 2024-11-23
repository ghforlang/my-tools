package fan.nbu.edu.cn.db;



import com.google.common.collect.ImmutableMap;
import fan.nbu.edu.cn.config.DBProperties;
import fan.nbu.edu.cn.db.metadata.ColumnMetaDataInfo;
import fan.nbu.edu.cn.db.metadata.TableMetaDataInfo;
import lombok.Getter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author laoshi . hua
 * @version 1.0 2022/3/20-11:27 上午
 * @since 1.0
 */
@Getter
public class DBTableHandler {

    
    private DataSource dataSource;
    
    private DBProperties dbProperties;

    private static final String DB_SCHEMA = "TTT";
    private static final String TABLE_SCHEMA_QUERY_SQL = "SELECT TABLE_NAME,CREATE_TIME,TABLE_ROWS,TABLE_COMMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA = ";
    private static final String COLUMN_SCHEMA_QUERY_SQL = "SELECT TABLE_NAME,COLUMN_NAME,COLUMN_DEFAULT,IS_NULLABLE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH,COLUMN_TYPE,COLUMN_COMMENT FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = '%s' AND TABLE_NAME = '%s'";
    private static final String COLUMN_QUERY_SQL = "SELECT * FROM %s WHERE id = (SELECT MAX(id) from %s);";


    private static final Set<String> tableNameCache = new HashSet<>();
    private static final Map<String, TableMetaDataInfo> tableMetaInfoCache = new HashMap<>();

    public Set<String> allTableNames(){
        return allTableNames(false);
    }


    public Set<String> allTableNames(boolean refresh){
        if((!CollectionUtils.isEmpty(tableNameCache)) && (!refresh)){
            return tableNameCache;
        }

        Map<String,TableMetaDataInfo> metaDataInfoInitMap = initMetaDataInfoMap();
        if(CollectionUtils.isEmpty(tableNameCache) && !MapUtils.isEmpty(metaDataInfoInitMap)){
            tableNameCache.addAll(metaDataInfoInitMap.keySet());
        }
        return metaDataInfoInitMap.keySet();
    }

    public Map<String,TableMetaDataInfo> initMetaDataInfoMap(){
        Map<String,TableMetaDataInfo> tableMetaDataInfoMap = new HashMap<>();
        Statement statement = null;
        try {
            statement = dataSource.getConnection().createStatement();
            // 查询infomation_schema.Tables 获取table metainfo
            ResultSet tables = statement.executeQuery(TABLE_SCHEMA_QUERY_SQL + "'" + DB_SCHEMA + "'") ;
            while(tables.next()){
                TableMetaDataInfo tableMetaDataInfo = new TableMetaDataInfo();
                tableMetaDataInfo.setTableName(tables.getString(1));
                if(StringUtils.isNotBlank(tables.getString(2))){
                tableMetaDataInfo.setCreateTime(LocalDateTime.parse(tables.getString(2), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")));
                }
//                tableMetaDataInfo.setComments(tables.getString(4));
//                if(dbProperties.getSrcTarTableNamePairs().keySet().contains(tableMetaDataInfo.getTableName())){
//                    tableMetaDataInfo.setTableType(TableType.SOURCE);
//                }
//                if(dbProperties.getTarSrcTableNamePairs().keySet().contains(tableMetaDataInfo.getTableName())){
//                    tableMetaDataInfo.setTableType(TableType.TARGET);
//                }
                tableMetaDataInfoMap.put(tables.getString(1),tableMetaDataInfo);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tableMetaDataInfoMap;
    }

    public Map<String,TableMetaDataInfo> tableMetaDataInfoMap(){
        return buildTableMetaDataMap(false);
    }

    public Map<String,TableMetaDataInfo> buildTableMetaDataMap(boolean refresh){
        if((!MapUtils.isEmpty(tableMetaInfoCache)) && !refresh){
            return tableMetaInfoCache;
        }

        Map<String,TableMetaDataInfo> tableMetaDataInfoMap = initMetaDataInfoMap();
        tableMetaDataInfoMap.forEach((tableName,tableMetaInfo) ->{
            fillTableColumnInfos(tableMetaInfo);
        });

        if(MapUtils.isEmpty(tableMetaInfoCache) && !MapUtils.isEmpty(tableMetaDataInfoMap)){
            tableMetaInfoCache.putAll(tableMetaDataInfoMap);
        }
        return  ImmutableMap.copyOf(tableMetaDataInfoMap);
    }

    public List<TableMetaDataInfo> listTables(){
        return new ArrayList<>(buildTableMetaDataMap(true).values());
    }


    private void fillTableColumnInfos(TableMetaDataInfo tableMetaDataInfo){
        // 查询infomation_schema.COLUMNS 获取COLUMNS metainfo
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = dataSource.getConnection().createStatement();
            resultSet = statement.executeQuery(String.format(COLUMN_SCHEMA_QUERY_SQL,DB_SCHEMA,tableMetaDataInfo.getTableName()));
            List<ColumnMetaDataInfo> columnMetaDataInfos = new ArrayList<>();
            while(resultSet.next()){
                ColumnMetaDataInfo columnMetaDataInfo = new ColumnMetaDataInfo();
                columnMetaDataInfo.setTableName(resultSet.getString(1));
                columnMetaDataInfo.setColumnName(resultSet.getString(2));
                columnMetaDataInfo.setColumnDefault(resultSet.getString(3));
                columnMetaDataInfo.setComments(resultSet.getString(8));
                columnMetaDataInfos.add(columnMetaDataInfo);
            }

            // 填充其他信息
            ResultSet resultColumnSet = statement.executeQuery(String.format(COLUMN_QUERY_SQL,tableMetaDataInfo.getTableName(),tableMetaDataInfo.getTableName()));
            ResultSetMetaData resultSetMetaData = resultColumnSet.getMetaData();
            Map<String,ColumnMetaDataInfo> tempMap = columnMetaDataInfos.stream().collect(Collectors.toMap( metaDataInfo -> metaDataInfo.getColumnName(),metaDataInfo -> metaDataInfo,(k1,k2) -> k1));
            for (int i = 1; i <= resultSetMetaData.getColumnCount() ; i++) {
                ColumnMetaDataInfo columnMetaDataInfo = tempMap.get(resultSetMetaData.getColumnName(i));
                columnMetaDataInfo.setNullable(dealWithNullable(resultSetMetaData.isNullable(i)));
                columnMetaDataInfo.setOrder(i);
                columnMetaDataInfo.setColumnJdbcType(JDBCType.valueOf(resultSetMetaData.getColumnType(i)));
            }
            tableMetaDataInfo.setColumnMetaDatas(columnMetaDataInfos);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

   private  String convertType(String sqlType) {
        if (sqlType.startsWith("varchar")){
            return "java.lang.String";
        }
        if (sqlType.startsWith("bigint")){
            return "java.lang.Long";
        }
        if (sqlType.startsWith("int")){
            return "java.lang.Integer";
        }
        if (sqlType.startsWith("smallint")) {
            return "java.lang.Short";
        }
        if (sqlType.startsWith("tinyint")) {
            return "java.lang.Byte";
        }
        if(sqlType.startsWith("double")) {
            return "java.lang.Double";
        }
        if(sqlType.startsWith("timestamp") || sqlType.startsWith("TIMESTAMP")){
            return "java.time.LocalDateTime";
        }
        return null;
    }


    private boolean dealWithNullable(int nullableValue){
       switch (nullableValue){
           case 0: return false;
           case 1: return true;
           case 2: return true;
           default: return true;
       }
    }
}
