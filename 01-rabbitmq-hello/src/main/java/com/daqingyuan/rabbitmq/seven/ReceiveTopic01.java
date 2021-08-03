package com.daqingyuan.rabbitmq.seven;

import com.daqingyuan.rabbitmq.one.Consumer;
import com.daqingyuan.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @创建人 daQingYuan
 * @创建时间 2021/8/3 21:33
 * @描述
 */
public class ReceiveTopic01 {
    //交换机名称
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        //声明队列
        String queueName = "Q1";
        channel.queueDeclare(queueName, false, false, false, null);
        //队列绑定
        channel.queueBind(queueName, EXCHANGE_NAME, "*.orange.*");

        System.out.println("等待接收ReceiveTopic01消息");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("接收队列:" + queueName + "绑定键:" + delivery.getEnvelope().getRoutingKey() + ",消息:" + message);
        };


        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}
