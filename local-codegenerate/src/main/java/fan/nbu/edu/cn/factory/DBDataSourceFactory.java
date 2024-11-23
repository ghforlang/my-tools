package fan.nbu.edu.cn.factory;



import fan.nbu.edu.cn.config.DBProperties;
import fan.nbu.edu.cn.constants.DialectEnum;
import fan.nbu.edu.cn.exception.DBConnectionException;
import fan.nbu.edu.cn.metainfo.db.DBConnectionInfo;
import org.apache.ibatis.datasource.pooled.PooledDataSource;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * @author laoshi . hua
 * @version 1.0 2022/3/18-3:41 下午
 * @since 1.0
 */

public class DBDataSourceFactory{

    
    private DBProperties dbProperties = DBProperties.getInstance();

    public DataSource getDataSource(DialectEnum dialect) throws DBConnectionException {
        DBConnectionInfo connectionInfo = dbProperties.getDbConnectionMap().get(dialect.getDialect());
        if(Objects.isNull(connectionInfo)){
            throw new DBConnectionException("can not find useful DBConnection info!");
        }

        PooledDataSource dataSource = new PooledDataSource();
        dataSource.setDriver(dialect.getDriverClassName());
        dataSource.setUrl(connectionInfo.getUrl());
        dataSource.setUsername(connectionInfo.getUserName());
        dataSource.setPassword(connectionInfo.getPassword());
        dataSource.setPoolPingQuery(connectionInfo.getPoolPingQuery());
        return dataSource;
    }
}
