package fan.nbu.edu.cn.metainfo.db;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author laoshi . hua
 * @version 1.0 2022/3/18-3:50 下午
 * @since 1.0
 */
@Getter
@Setter
@Builder
public class DBConnectionInfo {
    private String url;
    private String userName;
    private String password;
    private String poolPingQuery;
}
