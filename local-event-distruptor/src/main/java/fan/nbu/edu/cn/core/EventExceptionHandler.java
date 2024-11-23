package fan.nbu.edu.cn.core;

import com.lmax.disruptor.ExceptionHandler;
import fan.nbu.edu.cn.event.Event;
import lombok.extern.slf4j.Slf4j;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/18-10:27
 * @since 1.0
 */
@Slf4j
public class EventExceptionHandler<T>  implements ExceptionHandler<Event<T>> {
    @Override
    public void handleEventException(Throwable throwable, long l, Event<T> tEvent) {
        log.error("Exception handler threw exception with event [{}]",tEvent.getData());
    }

    @Override
    public void handleOnStartException(Throwable throwable) {
        log.warn("Exception handler threw exception with event [{}]",throwable);
    }

    @Override
    public void handleOnShutdownException(Throwable throwable) {
        log.warn("Exception handler threw exception with event [{}]",throwable);
    }
}
