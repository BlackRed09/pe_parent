package com.blackred.job;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class MySchedule {
    public static void main(String[] args) {

        SchedulerFactory factory = new StdSchedulerFactory();

        JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity("job1","groupa").build();
        SimpleTrigger trigger = newTrigger().withIdentity("trigger1","groupa").startNow().withSchedule(simpleSchedule().withIntervalInSeconds(5).withRepeatCount(11)).build();
        try {
            Scheduler scheduler = factory.getScheduler();
            scheduler.scheduleJob(jobDetail,trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
