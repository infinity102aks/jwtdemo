package com.ankit.schedule_notification.scheduler_notification_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
public class SchedulerNotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulerNotificationServiceApplication.class, args);
	}

}
