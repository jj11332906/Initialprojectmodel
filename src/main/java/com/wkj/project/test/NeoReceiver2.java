package com.wkj.project.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "neo")
@Slf4j
public class NeoReceiver2 {

    //todo 工作模式
    @RabbitHandler
    public void process(String hello) {
        log.info("工作 Mode  Receiver2 : " + hello);
    }
}
