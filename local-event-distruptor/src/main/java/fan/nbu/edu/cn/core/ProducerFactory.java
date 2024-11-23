package fan.nbu.edu.cn.core;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import fan.nbu.edu.cn.consumer.BaseConsumer;
import fan.nbu.edu.cn.event.Event;
import fan.nbu.edu.cn.producer.BaseProducer;
import fan.nbu.edu.cn.utils.ThreadUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/18-10:21
 * @since 1.0
 */
public class ProducerFactory {

    private static final LongAdder NUM = new LongAdder();
    private static final LongAdder STRATEGY_NUM = new LongAdder();

    private ProducerFactory(){}

    /**
     * 创建"发布订阅模式"的操作队列，即同一事件会被多个消费者并行消费
     * @param size  队列大小,必须是2的幂
     * @param moreProducer  是否多生产者模式
     * @param tagKey  队列的标签key
     * @param tagValue  队列的标签value
     * @param consumers
     * @return
     * @param <T>
     */
    public static <T> BaseProducer<T> getHandlerEventProducer(int size, boolean moreProducer, String tagKey, String tagValue, BaseConsumer<T> ... consumers){
        Disruptor<Event<T>> disruptor = new Disruptor<>(Event::new,
                size, Executors.defaultThreadFactory(),
                moreProducer ? ProducerType.MULTI : ProducerType.SINGLE,
                new BlockingWaitStrategy());
        disruptor.setDefaultExceptionHandler(new EventExceptionHandler<>());
        disruptor.handleEventsWith(consumers);
        return new BaseProducer<T>(disruptor,tagKey, tagValue);
    }


    /**
     * 创建"发布订阅模式"的操作队列，即同一事件会被多个消费者并行消费
     * @param queueSize
     * @param isMoreProducer
     * @param tagKey
     * @param tagValue
     * @param waitStrategy
     * @param consumers
     * @return
     * @param <T>
     */
    public static <T> BaseProducer<T> getHandlerEventProducer(int queueSize, boolean isMoreProducer, String tagKey, String tagValue,
                                                             WaitStrategy waitStrategy, BaseConsumer<T>... consumers) {
        Disruptor<Event<T>> disruptor = new Disruptor<>(Event::new,
                queueSize, Executors.defaultThreadFactory(),
                isMoreProducer ? ProducerType.MULTI : ProducerType.SINGLE,
                waitStrategy);
        disruptor.setDefaultExceptionHandler(new EventExceptionHandler<>());
        disruptor.handleEventsWith(consumers);
        return new BaseProducer<>(disruptor,tagKey, tagValue);
    }

    /**
     * 创建"发布订阅模式"的操作队列，即同一事件会被多个消费者并行消费
     *
     * @param <T>
     * @param queueSize      队列大小，必需是2的n次方
     * @param isMoreProducer 是否有多发布者
     * @param consumers
     * @return
     */
    @SafeVarargs
    public static <T> BaseProducer<T> getHandlerEventProducer(int queueSize, boolean isMoreProducer,
                                                             BaseConsumer<T>... consumers) {
        Disruptor<Event<T>> disruptor = new Disruptor<>(Event::new,
                queueSize, Executors.defaultThreadFactory(),
                isMoreProducer ? ProducerType.MULTI : ProducerType.SINGLE,
                new BlockingWaitStrategy());
        disruptor.setDefaultExceptionHandler(new EventExceptionHandler<>());
        disruptor.handleEventsWith(consumers);
        String appName = ThreadUtils.getCurrentInitBeanName();
        int value = NUM.intValue();
        NUM.increment();
        return new BaseProducer<>(disruptor,appName, String.valueOf(value));
    }


    /**
     * 创建"发布订阅模式"的操作队列，即同一事件会被多个消费者并行消费
     *
     * @param <T>
     * @param queueSize      队列大小，必需是2的n次方
     * @param isMoreProducer 是否有多发布者
     * @param waitStrategy   消费等待策略
     * @param consumers
     * @return
     */
    @SafeVarargs
    public static <T> BaseProducer<T> getHandlerEventProducer(int queueSize, boolean isMoreProducer,
                                                             WaitStrategy waitStrategy, BaseConsumer<T>... consumers) {
        Disruptor<Event<T>> disruptor = new Disruptor<>(Event::new,
                queueSize, Executors.defaultThreadFactory(),
                isMoreProducer ? ProducerType.MULTI : ProducerType.SINGLE,
                waitStrategy);
        disruptor.setDefaultExceptionHandler(new EventExceptionHandler<>());
        disruptor.handleEventsWith(consumers);
        String appName = ThreadUtils.getCurrentInitBeanName() + "_waitStrategy";
        int value = STRATEGY_NUM.intValue();
        STRATEGY_NUM.increment();
        return new BaseProducer<>(disruptor, appName, String.valueOf(value));
    }

}
