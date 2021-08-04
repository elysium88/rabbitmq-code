package com.daqingyuan.rabbitmq.controller;

import com.daqingyuan.rabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author: daQingYuan
 * @Date: 2021/8/4 15:04
 * @Description: 开始发消息 测试确认
 */
@Slf4j
@RestController
@RequestMapping("/confirm")
public class ProducerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/sendMsg/{msg}")
    public void sendMessage(@PathVariable String msg) {

        CorrelationData correlationData1 = new CorrelationData("1");
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME, ConfirmConfig.CONFIRM_ROUTING_KEY, msg+"key1",correlationData1);
        log.info("当前时间:{},发送一条确认消息{}", new Date().toString(), msg);


        CorrelationData correlationData2 = new CorrelationData("2");
        //rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME, ConfirmConfig.CONFIRM_ROUTING_KEY, msg,correlationData);
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE_NAME, ConfirmConfig.CONFIRM_ROUTING_KEY+2, msg+"key2",correlationData2);//发送失败
        log.info("当前时间:{},发送一条确认消息{}", new Date().toString(), msg);
    }
}
