package fan.nbu.edu.cn.config;

import fan.nbu.edu.cn.constants.DialectEnum;
import fan.nbu.edu.cn.metainfo.db.DBConnectionInfo;
import lombok.Getter;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/8-10:49
 * @since 1.0
 */
@Getter
public class DBProperties{

    public static final DBProperties getInstance(){
        return DBPropertiesHolder.INSTANCE;
    }

    static class  DBPropertiesHolder{
        private static final DBProperties INSTANCE = new DBProperties();
    }

    private final Map<String, DBConnectionInfo> dbConnectionMap = new HashMap<>();
//    /**
//     * 源表->目标表map，结构 源表 -> List<源表，目标表>
//     */
//    private final Map<String, List<ImmutablePair<String,String>>> srcTarTableNamePairs = new HashMap<>();
    /**
     * 目标表->源表map，结构 目标表 -> List<目标表,源表>
     */
    private final Map<String,List<ImmutablePair<String,String>>> tarSrcTableNamePairs = new HashMap();

    // @Value("${dialect}")
    private String dialectToUse;

    private List<String> sourceTargetTablePairs;

    // @Value("${mysql.url}")
    private String mysqlUrl;
    // @Value("${mysql.username}")
    private String mysqlUserName;
    // @Value("${mysql.password}")
    private String mysqlPassword;
    // @Value("${mysql.poolPingQuery}")
    private String mysqlPoolPingQuery;


    // @Value("${postgresql.url}")
    private String postgresqlUrl;
    // @Value("${postgresql.username}")
    private String postgresqlUserName;
    // @Value("${postgresql.password}")
    private String postgresqlPassword;
    // @Value("${postgresql.poolPingQuery}")
    private String postgresqlPoolPingQuery;

//    @Value("${db2pojo.pojo.dir}")
    private String pojoDir;


    public DBProperties() {
        if(StringUtils.isNoneBlank(mysqlUrl,mysqlPassword,mysqlUserName,mysqlPoolPingQuery)){
            DBConnectionInfo connectionInfo = DBConnectionInfo.builder()
                    .url(mysqlUrl)
                    .userName(mysqlUserName)
                    .password(mysqlPassword)
                    .poolPingQuery(mysqlPoolPingQuery)
                    .build();
            dbConnectionMap.putIfAbsent(DialectEnum.Mysql.getDialect(),connectionInfo);
        }
        if(StringUtils.isNoneBlank(postgresqlUrl,postgresqlPassword,postgresqlUserName,postgresqlPoolPingQuery)){
            DBConnectionInfo connectionInfo = DBConnectionInfo.builder()
                    .url(postgresqlUrl)
                    .userName(postgresqlUserName)
                    .password(postgresqlPassword)
                    .poolPingQuery(postgresqlPoolPingQuery)
                    .build();
            dbConnectionMap.putIfAbsent(DialectEnum.Postgresql.getDialect(),connectionInfo);
        }

//        if(CollectionUtils.isNotEmpty(sourceTargetTablePairs)){
//            for (String sourceTargetTablePair : sourceTargetTablePairs) {
//                String[] tempPairs = StringUtils.split(sourceTargetTablePair,"#");
//                if(tempPairs.length == 2){
//                    if(StringUtils.isNotBlank(tempPairs[1])){
//                        String[] tarTables = StringUtils.split(tempPairs[1],"|");
//                        srcTarTableNamePairs.putIfAbsent(tempPairs[0],new ArrayList<>());
//                        for(int i=0;i<tarTables.length;i++){
//                            srcTarTableNamePairs.get(tempPairs[0]).add(new ImmutablePair<>(tempPairs[0],tarTables[i]));
//                            tarSrcTableNamePairs.putIfAbsent(tarTables[i],new ArrayList<>());
//                            tarSrcTableNamePairs.get(tarTables[i]).add(new ImmutablePair<>(tarTables[i],tempPairs[0]));
//                        }
//                    }
//                }
//            }
//            System.out.println("srcTarTableNamePairs:" + JSON.toJSONString(srcTarTableNamePairs));
//            System.out.println("tarSrcTableNamePairs:" + JSON.toJSONString(tarSrcTableNamePairs));
//        }
    }

    public DBConnectionInfo getDBConnectionInfo() {
        return dbConnectionMap.get(dialectToUse);
    }
}
