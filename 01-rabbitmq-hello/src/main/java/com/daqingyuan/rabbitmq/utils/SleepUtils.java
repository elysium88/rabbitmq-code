package com.daqingyuan.rabbitmq.utils;

/**
 * @Author: daQingYuan
 * @Date: 2021/8/3 15:48
 * @Description: 睡眠工具类
 */
public class SleepUtils {
    public static void sleep(int second) {
        try {
            Thread.sleep(1000 * second);
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
