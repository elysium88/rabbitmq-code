package com.daqingyuan.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: daQingYuan
 * @Date: 2021/8/4 10:42
 * @Description: ttl队列  配置文件类代码
 */
@Configuration
public class TtlQueueConfig {

    //普通交换机名称
    public static final String X_EXCHANGE = "X";
    //死信交换机名称
    public static final String Y_DEAD_LETTER_EXCHANGE = "Y";
    //普通队列名称
    public static final String QUEUE_A = "QA";
    public static final String QUEUE_B = "QB";
    //死信队列名称
    public static final String DEAD_LETTER_QUEUE = "QD";
    //普通队列
    public static final String QUEUE_C = "QC";



    //声明交换机
    @Bean("xExchange")
    public DirectExchange xExchange() {
        return new DirectExchange(X_EXCHANGE);

    }

    @Bean("yExchange")
    public DirectExchange yExchange() {
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);

    }

    //声明普通队列 TTL 10S
    @Bean("queueA")
    public Queue queueA() {
        Map<String, Object> params = new HashMap<>();
        //正常队列设置死信交换机 参数key是固定值
        params.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        //正常队列设置死信routing-key 参数key是固定值
        params.put("x-dead-letter-routing-key", "YD");
        //设置ttl时间 ms
        params.put("x-message-ttl", 10000);

        return QueueBuilder.durable(QUEUE_A).withArguments(params).build();
    }

    //声明普通队列TTL 40S
    @Bean("queueB")
    public Queue queueB() {
        Map<String, Object> params = new HashMap<>();
        //正常队列设置死信交换机 参数key是固定值
        params.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        //正常队列设置死信routing-key 参数key是固定值
        params.put("x-dead-letter-routing-key", "YD");
        //设置ttl时间 ms
        params.put("x-message-ttl", 40000);

        return QueueBuilder.durable(QUEUE_B).withArguments(params).build();
    }

    //声明死信队列 QD
    @Bean("queueD")
    public Queue queueD() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }

    //交换机和队列绑定 声明队列 A 绑定 X 交换机
    @Bean
    public Binding queueABindX(@Qualifier("queueA") Queue queueA,
                               @Qualifier("xExchange") DirectExchange xExchange) {
        return BindingBuilder.bind(queueA).to(xExchange).with("XA");
    }
    //声明队列 B 绑定 X 交换机
    @Bean
    public Binding queueBBindX(@Qualifier("queueB") Queue queueB,
                               @Qualifier("xExchange") DirectExchange xExchange) {
        return BindingBuilder.bind(queueB).to(xExchange).with("XB");
    }

    //声明死信队列 QD 绑定关系
    @Bean
    public Binding queueDBindY(@Qualifier("queueD") Queue queueD,
                               @Qualifier("yExchange") DirectExchange yExchange) {
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");
    }

    //声明普通队列
    @Bean("queueC")
    public Queue queueC() {
        Map<String, Object> params = new HashMap<>();
        //正常队列设置死信交换机 参数key是固定值
        params.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        //正常队列设置死信routing-key 参数key是固定值
        params.put("x-dead-letter-routing-key", "YD");

        return QueueBuilder.durable(QUEUE_C).withArguments(params).build();
    }

    @Bean
    public Binding queueCBindX(@Qualifier("queueC") Queue queueC,
                               @Qualifier("xExchange") DirectExchange xExchange) {
        return BindingBuilder.bind(queueC).to(xExchange).with("XC");
    }
}
