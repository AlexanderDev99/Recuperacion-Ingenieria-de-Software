package com.fitflow.scheduler.controllers.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${app.rabbitmq.queue}")
    private String queueName;

    @Bean
    public Queue myQueue() {
        return new Queue(queueName, true);
    }
}
