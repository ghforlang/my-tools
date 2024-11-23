package fan.nbu.edu.cn.config;

import fan.nbu.edu.cn.consumer.BaseConsumer;
import fan.nbu.edu.cn.core.ProducerFactory;
import fan.nbu.edu.cn.producer.BaseProducer;
import fan.nbu.edu.cn.prometheus.metrix.MyMeterRegistry;
import fan.nbu.edu.cn.prometheus.metrix.MyMetricValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.function.Consumer;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/18-15:28
 * @since 1.0
 */
@Configuration
public class DisruptorConfig {

    @Value("${disruptor.queueSize: 8192}")
    private int queueSize;

    @Bean
    @Order
    @ConditionalOnClass(MyMeterRegistry.class)
    public BaseProducer<Consumer<String>> baseProducer(){
        BaseProducer baseProducer = ProducerFactory.getHandlerEventProducer(queueSize,
                false, "test","test-1",
                new BaseConsumer<Consumer<String>>() {
            @Override
            public void consume(Consumer<String> consumer) {
                consumer.accept("hello world!");
            }
        });

        baseProducer.setMeterRegistry(myMeterRegistry());
        MyMetricValue<String> calcMetricValue = new MyMetricValue<>("test","test-1",baseProducer.getRingBuffer());
        // 将队列指标注册到监控中
        calcMetricValue.bindTo(myMeterRegistry());
        return baseProducer;
    }

    @Bean
    @Order(Integer.MIN_VALUE)
    public MyMeterRegistry myMeterRegistry(){
        return new MyMeterRegistry();
    }


}
