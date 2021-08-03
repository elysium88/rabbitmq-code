package com.daqingyuan.rabbitmq.three;

import com.daqingyuan.rabbitmq.utils.RabbitMqUtils;
import com.daqingyuan.rabbitmq.utils.SleepUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @Author: daQingYuan
 * @Date: 2021/8/3 15:05
 * @Description: 消息在手动应答时不丢失，放回队列中重新消费
 */
public class Worker01 {

    public static final String QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception {
        //获取信道
        Channel channel = RabbitMqUtils.getChannel();

        System.out.println("----------------worker01等待接收消息处理时间短----------");
        //接收消息回调
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            //沉睡1秒
            SleepUtils.sleep(1);

            System.out.println("接收到的消息 = " + new String(message.getBody()));
            /**
             * 1.消息标记 tag
             * 2.是否批量应答未应答消息
             */
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };
        //取消消息的回调
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消费者取消消费接口回调逻辑 = " + consumerTag);
        };
        //设置不公平分发
        channel.basicQos(1);
        //消息的接收
        boolean autoAck = false;//采用手动应答
        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, cancelCallback);
    }
}
