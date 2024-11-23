package fan.nbu.edu.cn.prometheus.metrix;

import fan.nbu.edu.cn.prometheus.EhcacheFilter;
import fan.nbu.edu.cn.prometheus.MyMeterBinder;
import io.micrometer.core.instrument.Clock;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.common.TextFormat;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/16-20:20
 * @since 1.0
 * @Description : TODO 用于指标监控
 */
public class MyMeterRegistry extends PrometheusMeterRegistry {

    private List<MyMeterBinder> meterBinderList;
    private List<Consumer<MyMeterRegistry>> consumerList;

    public MyMeterRegistry() {
        this(PrometheusConfig.DEFAULT);
    }

    public MyMeterRegistry(PrometheusConfig config){
        super(config,new CollectorRegistry(), Clock.SYSTEM);
        this.meterBinderList = new ArrayList<>();
        this.consumerList = new ArrayList<>();
        init();
    }

    public MyMeterRegistry(PrometheusConfig config, CollectorRegistry registry, Clock clock){
        super(config,registry,clock);
        this.meterBinderList = new ArrayList<>();
        this.consumerList = new ArrayList<>();
        init();
    }

    public void scrape(Writer writer) throws IOException {
        this.consumerList.forEach((consumer) -> consumer.accept(this));
        TextFormat.write004(writer,this.getPrometheusRegistry().metricFamilySamples());
    }

    private void init(){
        this.config().meterFilter(new EhcacheFilter());
        this.meterBinderList.forEach(meterBinder -> meterBinder.bindTo(this));
    }

    public LongAdder count(String name, String tagKey, String tagValue){
        System.out.println("name:"+name+" tagKey:"+tagKey+" tagValue:"+tagValue);
        return new LongAdder();
    }
}
