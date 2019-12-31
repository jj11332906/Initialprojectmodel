package com.wkj.project.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "topic.A")
@Slf4j
public class TopicReceiver {

    @RabbitHandler
    public void process(String message)
    {
        System.out.println("Topic Receiver1  : " + message);
    }
}
