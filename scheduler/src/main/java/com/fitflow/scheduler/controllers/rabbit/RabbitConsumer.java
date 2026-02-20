package com.fitflow.scheduler.controllers.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitConsumer {

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void consumeMessage(String message) {
        System.out.println(" LOG RECIBIDO DESDE RABBITMQ: >> " + message);
    }
}
