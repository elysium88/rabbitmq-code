package com.daqingyuan.rabbitmq.consumer;

import com.daqingyuan.rabbitmq.config.ConfirmConfig;
import com.daqingyuan.rabbitmq.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: daQingYuan
 * @Date: 2021/8/4 15:09
 * @Description: 接收消息
 */
@Slf4j
@Component
public class ConfirmConsumer {
    //接收消息
    @RabbitListener(queues = ConfirmConfig.CONFIRM_QUEUE_NAME)
    public void receivedConfirmQueue(Message message) {
        String msg = new String(message.getBody());
        log.info("当前时间：{}，收到confirmQueue的消息：{}", new Date().toString(), msg);
    }
}
