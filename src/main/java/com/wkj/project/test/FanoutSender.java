package com.wkj.project.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class FanoutSender {

    @Autowired
    private AmqpTemplate rabbitmqTemplate;

    //todo 发布订阅模式 消息生产者
    public void send(){
        String context = "hi, fanout msg ";
        System.out.println("Sender : " + context);
        rabbitmqTemplate.convertAndSend("fanoutExchange","", context);
    }
}
