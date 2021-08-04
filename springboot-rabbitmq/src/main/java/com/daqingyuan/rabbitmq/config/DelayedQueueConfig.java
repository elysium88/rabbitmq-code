package com.daqingyuan.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: daQingYuan
 * @Date: 2021/8/4 14:14
 * @Description: 延迟交换机队列
 */
@Configuration
public class DelayedQueueConfig {

    //交换机
    public static final String DELAYED_EXCHANGE_NAME = "delayed.exchange";

    //队列
    public static final String DELAYED_QUEUE_NAME = "delayed.queue";

    //routingKey
    public static final String DELAYED_ROUTING_KEY = "delayed.routingKey";


    //声明队列
    @Bean("delayedQueue")
    public Queue delayedQueue() {
        return new Queue(DELAYED_QUEUE_NAME);
    }

    //声明交换机 基于插件的
    @Bean("delayedExchange")
    public CustomExchange delayedExchange() {
        Map<String, Object> arguments = new HashMap<>();
        //自定义交换机的类型
        arguments.put("x-delayed-type", "direct");
        /**
         * 1.交换机名称
         * 2.交换机类型
         * 3.是否需持久化
         * 4.是否需要自动删除
         * 5.其他的参数
         */
        return new CustomExchange(DELAYED_EXCHANGE_NAME,"x-delayed-message", true, false, arguments);
    }

    //队列和交换机绑定
    @Bean
    public Binding delayedQueueBindDelayedExchange(@Qualifier("delayedQueue") Queue delayedQueue,
                                                   @Qualifier("delayedExchange") CustomExchange delayedExchange) {
        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(DELAYED_ROUTING_KEY).noargs();

    }
}