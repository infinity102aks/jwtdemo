package com.ankit.schedule_notification.scheduler_notification_service.worker;

import com.ankit.schedule_notification.scheduler_notification_service.consumer.SchedulerEvent;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class SchedulerTriggerJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        SchedulerEvent event = (SchedulerEvent) jobDataMap.get("event");

        System.out.println(event);
    }


    @Bean
    public JobDetailFactoryBean myJobDetail() {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(SchedulerTriggerJob.class);
        factory.setDurability(true);
        return factory;
    }
}
