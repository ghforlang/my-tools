package fan.nbu.edu.cn.config;

import fan.nbu.edu.cn.constants.DialectEnum;
import fan.nbu.edu.cn.exception.DBConnectionException;
import fan.nbu.edu.cn.factory.DBDataSourceFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/8-10:48
 * @since 1.0
 */
public class DBConfig {

    
    private DBProperties dbProperties = DBProperties.getInstance();
    
    private DBDataSourceFactory dbDataSourceFactory;


    public DataSource dataSource() throws DBConnectionException {
        return defineDataSource();
    }

    
    public org.apache.ibatis.session.Configuration configuration() throws DBConnectionException {
        Environment environment = new Environment("default", new JdbcTransactionFactory(), dataSource());
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setEnvironment(environment);
        return configuration;
    }

    
    public SqlSessionFactory sqlSessionFactory() throws DBConnectionException {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration());
        return sqlSessionFactory;
    }


    
    public Connection sqlConnection(){
        try {
            return defineDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("fecth connection failed!");
        }
        return null;
    }
    private DataSource defineDataSource() throws DBConnectionException {
        if(StringUtils.isBlank(dbProperties.getDialectToUse())){
            throw new DBConnectionException("choose one dialect at least!");
        }
        DialectEnum dialect = DialectEnum.getByDialect(dbProperties.getDialectToUse());
        return dbDataSourceFactory.getDataSource(dialect);
    }
}
