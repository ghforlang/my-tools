package fan.nbu.edu.cn.prometheus;

import fan.nbu.edu.cn.prometheus.metrix.MyMeterRegistry;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/18-14:53
 * @since 1.0
 */
public interface MyMeterBinder {
    void bindTo(MyMeterRegistry registry);
}
