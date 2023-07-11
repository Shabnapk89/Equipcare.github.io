package org.shabnapuliyalakunnath.equipcare;

import ch.qos.logback.classic.model.processor.LogbackClassicDefaultNestedComponentRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableAutoConfiguration
@EntityScan(basePackages = {"org.shabnapuliyalakunnath.equipcare.entity"})
public class HemsApplication {

	public static void main(String[] args) {
		LoggerFactory.getLogger(HemsApplication.class).info("Starting HEMS Application!");
		SpringApplication.run(HemsApplication.class, args);
	}

}
