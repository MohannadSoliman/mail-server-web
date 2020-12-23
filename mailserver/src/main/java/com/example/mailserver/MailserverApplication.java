package com.example.mailserver;

import com.example.mailserver.Logic.operationsHandlers.FileStorageProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
	FileStorageProperties.class
})
public class MailserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailserverApplication.class, args);
	}

}
