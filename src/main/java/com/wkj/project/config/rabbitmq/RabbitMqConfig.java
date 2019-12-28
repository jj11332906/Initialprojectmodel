package com.wkj.project.config.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    /**
     * 简单模式
     * @return
     */
    @Bean
    public Queue helloQueue(){
        return new Queue("hello");
    }

    /**
     * 工作模式
     * @return
     */
    @Bean
    public Queue Queue2() {
        return new Queue("neo");
    }
}
