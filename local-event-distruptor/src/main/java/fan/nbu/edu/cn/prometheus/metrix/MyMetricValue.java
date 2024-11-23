package fan.nbu.edu.cn.prometheus.metrix;

import com.lmax.disruptor.RingBuffer;
import fan.nbu.edu.cn.event.Event;
import fan.nbu.edu.cn.prometheus.MyMeterBinder;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.BaseUnits;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/18-15:11
 * @since 1.0
 */
public class MyMetricValue<T> implements MyMeterBinder {

    /**
     * 当前disruptor的标签
     */
    private final String tagKey;
    private final String tagValue;

    private final RingBuffer<Event<T>> ringBuffer;


    public MyMetricValue(String tagKey, String tagValue, RingBuffer<Event<T>> ringBuffer) {
        this.tagKey = tagKey;
        this.tagValue = tagValue;
        this.ringBuffer = ringBuffer;
    }


    @Override
    public void bindTo(MyMeterRegistry registry) {
        Gauge.builder("disruptor.buffer.size", this.ringBuffer, RingBuffer::getBufferSize)
                .tags(tagKey, tagValue)
                .description("Buffer size of Disruptor")
                .baseUnit(BaseUnits.BYTES)
                .register(registry);
        Gauge.builder("disruptor.remaining.capacity", this.ringBuffer, RingBuffer::remainingCapacity)
                .tags(tagKey, tagValue)
                .description("Remaining capacity of Disruptor")
                .baseUnit(BaseUnits.BYTES)
                .register(registry);
    }
}
