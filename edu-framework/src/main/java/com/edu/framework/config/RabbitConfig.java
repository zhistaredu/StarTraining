//package com.edu.framework.config;
//
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitConfig {
//
//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
//            if (ack) {
//                // Message sent successfully
//                System.out.println("Message sent successfully");
//            } else {
//                // Message sent failed
//                System.out.println("Message sent failed");
//            }
//        });
//        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
//            // Handle returned (unrouted) messages here
//            System.out.println("Returned message: " + new String(message.getBody()));
//            System.out.println("Reply Code: " + replyCode);
//            System.out.println("Reply Text: " + replyText);
//            System.out.println("Exchange: " + exchange);
//            System.out.println("Routing Key: " + routingKey);
//        });
//        return rabbitTemplate;
//    }
//}