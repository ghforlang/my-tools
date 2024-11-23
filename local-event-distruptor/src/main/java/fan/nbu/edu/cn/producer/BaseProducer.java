package fan.nbu.edu.cn.producer;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import fan.nbu.edu.cn.event.Event;
import fan.nbu.edu.cn.prometheus.metrix.MyMeterRegistry;
import fan.nbu.edu.cn.prometheus.metrix.MyMetricValue;
import fan.nbu.edu.cn.utils.SpringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/16-20:11
 * @since 1.0
 */
public class BaseProducer<T> {

    /**
     * 定义disruptor
     */
    protected Disruptor<Event<T>> disruptor;

    /**
     * 缓冲区
     */
    protected RingBuffer<Event<T>> ringBuffer;

    /**
     * 标签key
     */
    protected  String tagKey;

    /**
     * 标签value
     */
    protected  String tagValue;

    public void setMeterRegistry(MyMeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    /**
     * 指标监控注册
     */
    protected MyMeterRegistry meterRegistry;

    public BaseProducer(Disruptor<Event<T>> disruptor, String tagKey, String tagValue) {
        this.disruptor = disruptor;
        this.ringBuffer = disruptor.getRingBuffer();
        this.tagKey = tagKey;
        this.tagValue = tagValue;
         this.disruptor.start();
    }

    /**
     * 添加数据到队列
     *
     * @param data 数据
     */
    public  void add(T data){
        if (data != null) {
//            this.meterRegistry.count("input_disruptor", this.tagKey, this.tagValue).increment();
            long sequence = this.ringBuffer.next();
            try {
                Event<T> event = this.ringBuffer.get(sequence);
                event.setData(data);
                event.setTagKey(this.tagKey);
                event.setTagValue(this.tagValue);
            } finally {
                this.ringBuffer.publish(sequence);
            }
        }
    }

    public  void addAll(List<T> data){
        if(!CollectionUtils.isEmpty(data)){
            for (T datum : data) {
                this.add(datum);
            }
        }
    }


    public long cursor(){
        return this.disruptor.getRingBuffer().getCursor();
    }

    public void shutdown(){
        this.disruptor.shutdown();
    }

    public  Disruptor<Event<T>> getDisruptor(){
        return this.disruptor;
    }

    public RingBuffer<Event<T>> getRingBuffer(){
        return this.ringBuffer;
    }

}
