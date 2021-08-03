package com.daqingyuan.rabbitmq.two;

import com.daqingyuan.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @Author: daQingYuan
 * @Date: 2021/8/3 15:05
 * @Description: 这是一个工作线程（相当于之前消费者）
 */
public class Worker01 {

    public static final String QUEUE_NAME="hello";

    public static void main(String[] args) throws Exception {
        //获取信道
        Channel channel = RabbitMqUtils.getChannel();

        //接收消息回调
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("接收到的消息 = " + new String(message.getBody()));
        };
        //取消消息的回调
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消费者取消消费接口回调逻辑 = " + consumerTag);
        };
        System.out.println("----------------worker02等待接收消息----------");
        //消息的接收
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }
}
