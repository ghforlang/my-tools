package fan.nbu.edu.cn.prometheus;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.config.MeterFilterReply;

import java.util.HashSet;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/18-15:09
 * @since 1.0
 */
public class EhcacheFilter implements MeterFilter {
    private HashSet<String> ignoreMetrics = new HashSet() {
        {
            this.add("cache.local.offheap.size");
            this.add("cache.local.heap.size");
            this.add("cache.local.disk.size");
        }
    };

    public EhcacheFilter() {
    }

    public MeterFilterReply accept(Meter.Id id) {
        return this.ignoreMetrics.contains(id.getName()) ? MeterFilterReply.DENY : MeterFilterReply.NEUTRAL;
    }
}
