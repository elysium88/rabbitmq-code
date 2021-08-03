package com.daqingyuan.rabbitmq.one;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    //队列名称
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("121.89.206.56");
        factory.setUsername("admin");
        factory.setPassword("123456");

        //channel 实现了自动 close 接口 自动关闭 不需要显示关闭
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //接收消息回调
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("deliverCallback = " + message);
            System.out.println("deliverCallback2 = " + new String(message.getBody()));
        };
        //取消消息的回调
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("cancelCallback = " + consumerTag);
        };

        /**
         * 消费者消费消息
         * 1.消费哪个队列
         * 2.消费成功之后是否要自动应答 true 代表自动应答 false 手动应答
         * 3.消费者未成功消费的回调
         * 4.取消消费的回调
         */
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }
}
