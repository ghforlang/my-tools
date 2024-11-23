package fan.nbu.edu.cn.core;


import com.lmax.disruptor.EventFactory;
import fan.nbu.edu.cn.event.Event;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/18-10:32
 * @since 1.0
 */
public class CustomeEventFactory implements EventFactory<Event<?>> {

    @Override
    public Event<?> newInstance() {
        return new Event<>();
    }
}
