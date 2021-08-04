package com.daqingyuan.rabbitmq.eight;

import com.daqingyuan.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * @创建人 daQingYuan
 * @创建时间 2021/8/3 22:02
 * @描述 死信队列实战--消费者
 */
public class Consumer01 {
    //普通交换机名称
    private static final String NORMAL_EXCHANGE = "normal_exchange";
    //死信交换机名称
    private static final String DEAD_EXCHANGE = "dead_exchange";

    //普通队列名称
    private static final String NORMAL_QUEUE = "normal_queue";
    //死信队列名称
    private static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();
        //声明死信和普通交换机 类型为direct
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);


        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);
        //死信队列绑定死信交换机与routingkey
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "lisi");
        Map<String, Object> params = new HashMap<>();
        //正常队列设置死信交换机 参数key是固定值
        params.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        //正常队列设置死信routing-key 参数key是固定值
        params.put("x-dead-letter-routing-key", "lisi");
        //设置队列最大长度
        //params.put("x-max-length", 6);

        //正常队列绑定
        channel.queueDeclare(NORMAL_QUEUE, false, false, false, params);
        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, "zhangsan");
        System.out.println("等待接收消息.....");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            if (message.equals("info5")) {
                System.out.println("Consumer01接收到消息" + message + "并拒绝签收该消息");
                //requeue设置为false 代表拒绝重新入队 该队列如果配置了死信交换机将发送到死信队列中
                channel.basicReject(delivery.getEnvelope().getDeliveryTag(), false);
            } else {
                System.out.println("Consumer01接收到消息" + message);
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
            System.out.println("Consumer01接收到消息" + message);
        };
        channel.basicConsume(NORMAL_QUEUE, false, deliverCallback, consumerTag -> {
        });
    }
}
