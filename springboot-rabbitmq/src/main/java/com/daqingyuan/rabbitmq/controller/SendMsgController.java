package com.daqingyuan.rabbitmq.controller;

import com.daqingyuan.rabbitmq.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author: daQingYuan
 * @Date: 2021/8/4 11:13
 * @Description: 发送延迟消息
 */
@Slf4j
@RestController
@RequestMapping("/ttl")
public class SendMsgController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //开始发消息
    @GetMapping("/sendMsg/{message}")
    public void sendMsg(@PathVariable("message") String msg) {
        log.info("当前时间:{},发送一条信息给两个TTL队列:{}", new Date().toString(), msg);
        rabbitTemplate.convertAndSend("X", "XA", "消息来自TTL为10s的队列：" + msg);
        rabbitTemplate.convertAndSend("X", "XB", "消息来自TTL为40s的队列：" + msg);
    }

    //开始发消息 加上过期时间
    @GetMapping("/sendExpirationMsg/{message}/{ttlTime}")
    public void sendExpirationMsg(@PathVariable("message") String msg, @PathVariable("ttlTime") String ttlTime) {
        log.info("当前时间:{},发送一条时长{}毫秒的信息给队列QC:{}", new Date().toString(), ttlTime, msg);
        rabbitTemplate.convertAndSend("X", "XC", "消息来自QC的队列：" + msg, message -> {
            //发送消息的时候 延迟时长 单位ms
            message.getMessageProperties().setExpiration((ttlTime));
            return message;
        });
    }

    //开始发消息 基于插件的 消息和延迟时间
    @GetMapping("/sendDelayMsg/{message}/{delayTime}")
    public void sendDelayedMsg(@PathVariable("message") String msg, @PathVariable("delayTime") Integer  delayTime) {
        log.info("当前时间:{},发送一条时长{}毫秒的信息给延迟队列:{}", new Date().toString(), delayTime, msg);
        rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAYED_EXCHANGE_NAME, DelayedQueueConfig.DELAYED_ROUTING_KEY, "消息来自延迟的队列：" + msg, message -> {
            //发送消息的时候 延迟时长 单位ms
            message.getMessageProperties().setDelay((delayTime));
            return message;
        });
    }

}
