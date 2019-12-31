package com.wkj.project.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TopicSend {

    @Autowired
    private AmqpTemplate rabbitmqTemplate;

    /**
     * send的key是topic.1  send1的key是topic.message，send2的key是topic.messages

     所以理论上send会被两个队列消费，1.2都应该只有一个队列消费

     我们再新建两个消费者 TopicReceiver,TopicReceiver2
     */

    public void send() {
        String context = "hi, i am message all";
        System.out.println("Sender : " + context);
        rabbitmqTemplate.convertAndSend("topicExchange", "topic.1", context);
    }

    public void send1() {
        String context = "hi, i am message 1";
        System.out.println("Sender : " + context);
        rabbitmqTemplate.convertAndSend("topicExchange", "topic.message", context);
    }

    public void send2() {
        String context = "hi, i am messages 2";
        System.out.println("Sender : " + context);
        rabbitmqTemplate.convertAndSend("topicExchange", "topic.messages", context);
    }
}
