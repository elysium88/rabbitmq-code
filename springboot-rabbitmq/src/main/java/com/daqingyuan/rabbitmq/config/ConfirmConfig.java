package com.daqingyuan.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: daQingYuan
 * @Date: 2021/8/4 14:58
 * @Description: 配置类 发布确认高级
 */
@Configuration
public class ConfirmConfig {
    //交换机
    public static final String CONFIRM_EXCHANGE_NAME = "confirm.exchange";

    //队列
    public static final String CONFIRM_QUEUE_NAME = "confirm.queue";

    //routingKey
    public static final String CONFIRM_ROUTING_KEY = "confirm.routingKey";

    //备份交换机
    public static final String BACKUP_EXCHANGE_NAME = "backup.exchange";

    //备份队列
    public static final String BACKUP_QUEUE_NAME = "backup.queue";

    //报警队列
    public static final String WARNING_QUEUE_NAME = "warning.queue";

    //声明队列
    @Bean("confirmQueue")
    public Queue confirmQueue() {
        return new Queue(CONFIRM_QUEUE_NAME);
    }

    //声明交换机 基于插件的
    //新增备份交换机 当确认交换机出现问题 发送到备份交换机
    @Bean("confirmExchange")
    public DirectExchange confirmExchange() {

        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE_NAME).durable(true).
                withArgument("alternate-exchange", BACKUP_EXCHANGE_NAME).build();

        //return new DirectExchange(CONFIRM_EXCHANGE_NAME);
    }

    //队列和交换机绑定
    @Bean
    public Binding confirmQueueBindConfirmExchange(@Qualifier("confirmQueue") Queue queue,
                                                   @Qualifier("confirmExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(CONFIRM_ROUTING_KEY);

    }


    //声明交换机
    @Bean("backupExchange")
    public FanoutExchange backupExchange() {

        return new FanoutExchange(BACKUP_EXCHANGE_NAME);
    }

    //声明队列
    @Bean("backupQueue")
    public Queue backupQueue() {
        return new Queue(BACKUP_QUEUE_NAME);
    }

    //声明队列
    @Bean("warningQueue")
    public Queue warningQueue() {
        return new Queue(WARNING_QUEUE_NAME);
    }

    //队列和交换机绑定
    @Bean
    public Binding backupQueueBindBackupExchange(@Qualifier("backupQueue") Queue queue,
                                                 @Qualifier("backupExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);

    }

    //队列和交换机绑定
    @Bean
    public Binding warningQueueBindConfirmExchange(@Qualifier("warningQueue") Queue queue,
                                                   @Qualifier("backupExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);

    }
}
