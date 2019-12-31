package com.wkj.project.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "topic.B")
@Slf4j
public class TopicReceiver2 {

    @RabbitHandler
    public void process(String message)
    {
        System.out.println("Topic Receiver2  : " + message);
    }
}
