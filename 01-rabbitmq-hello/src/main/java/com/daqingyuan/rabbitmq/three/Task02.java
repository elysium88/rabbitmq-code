package com.daqingyuan.rabbitmq.three;

import com.daqingyuan.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.util.Scanner;

/**
 * @Author: daQingYuan
 * @Date: 2021/8/3 15:37
 * @Description: 消息在手动应答时不丢失，放回队列中重新消费
 */
public class Task02 {
    //队列名称
    public static final String QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception {
        //获取信道
        Channel channel = RabbitMqUtils.getChannel();

        //开启发布确认
        channel.confirmSelect();

        //生成队列
        boolean durable = true;//队列持久化
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //从控制台接收消息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String next = scanner.next();
            //设置生产者发送消息为持久化消息（要求保存到磁盘上）保存内存中
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, next.getBytes());
            System.out.println("生产者发送消息： " + next);
        }
    }
}
