package com.alexhiz.rabbitMQ.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchenduledRabbitMQService {

    private final RabbitTemplate rabbitTemplate;

    public SchenduledRabbitMQService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedRate = 5000) // Envía un mensaje cada 5 segundos
    public void sendMessage() {
        String message = "Mensaje enviado a RabbitMQ";
        rabbitTemplate.convertAndSend("myQueue", message);
        System.out.println("Mensaje enviado a RabbitMQ");
        
    }
}
