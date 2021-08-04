package com.daqingyuan.rabbitmq.consumer;

import com.daqingyuan.rabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: daQingYuan
 * @Date: 2021/8/4 16:15
 * @Description:
 */
@Slf4j
@Component
public class WarningConsumer {
    //接收消息
    @RabbitListener(queues = ConfirmConfig.WARNING_QUEUE_NAME)
    public void receivedWarningQueue(Message message) {
        String msg = new String(message.getBody());
        log.error("当前时间：{}，收到报警路由的消息：{}", new Date().toString(), msg);
    }
}
