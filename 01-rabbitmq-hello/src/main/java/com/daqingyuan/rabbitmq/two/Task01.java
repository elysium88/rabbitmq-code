package com.daqingyuan.rabbitmq.two;

import com.daqingyuan.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 * @Author: daQingYuan
 * @Date: 2021/8/3 15:16
 * @Description: 生成者，可以发出大量消息
 */
public class Task01 {
    //队列名称
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        //获取信道
        Channel channel = RabbitMqUtils.getChannel();
        //生成队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //从控制台接收消息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String next = scanner.next();
            channel.basicPublish("", QUEUE_NAME, null, next.getBytes());
            System.out.println("发送消息完成： " + next);
        }
    }

}
