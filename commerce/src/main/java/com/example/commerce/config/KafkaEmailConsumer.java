package com.example.commerce.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.commerce.dto.EmailDto;

@Component
public class KafkaEmailConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaEmailConsumer.class);
	
	@KafkaListener(topics = "${kafka.topic.email}", groupId = "commerce-group-test")
	public void consume(EmailDto emailDto) {
		logger.info("Sending email to: " + emailDto.getTo());
		logger.info("From: " + emailDto.getFrom());
		logger.info("Subject: " + emailDto.getSubject());
		logger.info("Body: " + emailDto.getBody());
	}
}
