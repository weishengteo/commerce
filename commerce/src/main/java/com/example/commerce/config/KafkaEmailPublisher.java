package com.example.commerce.config;

import org.springframework.kafka.core.KafkaTemplate;

import com.example.commerce.dto.EmailDto;

public class KafkaEmailPublisher {
	
	private final KafkaTemplate<String, EmailDto> kafkaTemplate;
    private final String emailTopic;
    
    public KafkaEmailPublisher(KafkaTemplate<String, EmailDto> kafkaTemplate, String emailTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.emailTopic = emailTopic;
    }

    public void sendEmailEvent(EmailDto email) {
    	System.out.println("Sending email: " + email.getSubject());
        kafkaTemplate.send(emailTopic, email);
    }
}
