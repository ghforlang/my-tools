package fan.nbu.edu.cn.consumer;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import fan.nbu.edu.cn.event.Event;
import fan.nbu.edu.cn.prometheus.metrix.MyMeterRegistry;
import fan.nbu.edu.cn.utils.SpringUtils;

import java.util.Objects;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/18-10:17
 * @since 1.0
 */
public abstract class BaseConsumer<T> implements EventHandler<Event<T>>, WorkHandler<Event<T>> {

    private MyMeterRegistry meterRegistry;


    @Override
    public void onEvent(Event<T> tEvent, long l, boolean b) throws Exception {
        this.onEvent(tEvent);
    }

    @Override
    public void onEvent(Event<T> tEvent) throws Exception {
        if(Objects.isNull(meterRegistry)){
            meterRegistry = SpringUtils.getBean(MyMeterRegistry.class);
        }

        this.consume(tEvent.getData());
    }

    public abstract void consume(T data);
}
