package com.wkj.project.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "hello")
@Slf4j
public class Receiver {

    //todo 简单模式
    @RabbitHandler
    public void process(String hello){
      log.info("Direct Mode  Receiver : " + hello);
    }
}
