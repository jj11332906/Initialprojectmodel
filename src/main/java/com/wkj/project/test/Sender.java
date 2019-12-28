package com.wkj.project.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class Sender {

    @Autowired
    private AmqpTemplate rabbitmqTemplate;

    //todo 简单模式
    public void send(){
        String context = "Direct Mode " + new Date();
        log.info("Sender : "+context);
        rabbitmqTemplate.convertAndSend("hello",context);
    }

    //todo 工作模式
    public void send(int i){
        String context = "工作 Mode ,spring boot neo queue:" + i;
        log.info("Sender : "+context);
        rabbitmqTemplate.convertAndSend("neo",context);
    }
}
