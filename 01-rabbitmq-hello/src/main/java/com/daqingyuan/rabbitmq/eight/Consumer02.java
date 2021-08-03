package com.daqingyuan.rabbitmq.eight;

import com.daqingyuan.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @创建人 daQingYuan
 * @创建时间 2021/8/3 22:02
 * @描述 死信队列实战--消费者2(接收死信队列的消息)
 */
public class Consumer02 {

    //死信队列名称
    private static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();

        System.out.println("等待接收消息.....");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("Consumer02接收到消息" + message);
        };
        channel.basicConsume(DEAD_QUEUE, true, deliverCallback, consumerTag -> {
        });
    }
}
