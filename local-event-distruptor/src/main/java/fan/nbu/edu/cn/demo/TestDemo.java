package fan.nbu.edu.cn.demo;

import fan.nbu.edu.cn.consumer.BaseConsumer;
import fan.nbu.edu.cn.core.ProducerFactory;
import fan.nbu.edu.cn.producer.BaseProducer;

/**
 * @author laoshi . hua
 * @version 1.0 2024/11/18-13:52
 * @since 1.0
 */
public class TestDemo {

    public static void main(String[] args) {
        testProducerOneConsumer();
        testProducerMoreConsumer();
    }

    private static void testProducerOneConsumer(){
        BaseProducer<Integer> producer = ProducerFactory.getHandlerEventProducer(8192, false, new BaseConsumer<Integer>() {
            @Override
            public void consume(Integer data) {
                System.out.println("消费数据：" + data);
            }
        });

        for (int i = 0; i < 100; i++) {
            producer.add(i);
        }
    }

    private static void testProducerMoreConsumer(){
        BaseProducer<Integer> producer = ProducerFactory.getHandlerEventProducer(8192, true,
                new BaseConsumer<Integer>() {
                    @Override
                    public void consume(Integer data) {
                        System.out.println("consumer1消费数据：" + data);
                    }
                },
                new BaseConsumer<Integer>(){

                    @Override
                    public void consume(Integer data) {
                        System.out.println("consumer2消费数据：" + data);
                    }
                },
                new BaseConsumer<Integer>(){

                    @Override
                    public void consume(Integer data) {
                        System.out.println("consumer3消费数据：" + data);
                    }
                }
        );

        for (int i = 0; i < 100; i++) {
            producer.add(i);
        }
    }
}
