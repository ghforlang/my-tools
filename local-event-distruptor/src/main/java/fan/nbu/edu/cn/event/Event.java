package fan.nbu.edu.cn.event;


import lombok.Getter;
import lombok.Setter;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/16-20:03
 * @since 1.0
 */
@Getter
@Setter
public class Event<T> {

    /**
     * 事件内容
     */
    private T data;

    /**
     * Prometheus 度量指标标签键
     */
    private String tagKey;

    /**
     * Prometheus 度量指标标签值
     */
    private String tagValue;

    /**
     *  用于内存回收，防止OOM
     */
    public void clear(){
        this.data = null;
    }
}
