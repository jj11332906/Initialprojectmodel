package com.wkj.project.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "fanout.C")
@Slf4j
public class FanoutReceiveC {

    //todo 发布订阅模式，消息消费者
    @RabbitHandler
    public void process(String message) {
        System.out.println("fanout Receiver C  : " + message);
    }
}
