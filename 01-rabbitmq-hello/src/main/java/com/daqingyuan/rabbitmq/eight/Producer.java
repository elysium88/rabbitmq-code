package com.daqingyuan.rabbitmq.eight;

import com.daqingyuan.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

/**
 * @创建人 daQingYuan
 * @创建时间 2021/8/3 22:21
 * @描述 死信队列生产者
 */
public class Producer {
    private static final String NORMAL_EXCHANGE = "normal_exchange";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        //设置消息的TTL时间
        /*AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .expiration("10000")
                .build();
        for (int i = 1; i < 11; i++) {
            String message = "info" + i;
            channel.basicPublish(NORMAL_EXCHANGE, "zhangsan", properties, message.getBytes());
            System.out.println("生产者发送消息:"+message);
        }*/
        for (int i = 1; i < 11; i++) {
            String message = "info" + i;
            channel.basicPublish(NORMAL_EXCHANGE, "zhangsan", null, message.getBytes());
            System.out.println("生产者发送消息:"+message);
        }

    }
}
