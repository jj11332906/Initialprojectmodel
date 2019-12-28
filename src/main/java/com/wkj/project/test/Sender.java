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

    public void send(){
        String context = "Direct Mode " + new Date();
        log.info("Sender : "+context);
        rabbitmqTemplate.convertAndSend("hello",context);
    }
}
