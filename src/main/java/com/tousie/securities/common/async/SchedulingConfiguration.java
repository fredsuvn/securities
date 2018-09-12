package com.tousie.securities.common.async;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author sunqian
 */
//@Component
@Configuration
@EnableScheduling
public class SchedulingConfiguration implements SchedulingConfigurer {

//    @Resource
//    private ThreadPoolTaskExecutor asyncExecutor;

//    private ScheduledTaskRegistrar taskRegistrar;

//    private final Map<String, >

    //Called after application starting up
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//        taskRegistrar.setScheduler(asyncExecutor);
//        this.taskRegistrar = taskRegistrar;
    }

//    public void addTriggerTask(Runnable task, Trigger trigger) {
//        taskRegistrar.addTriggerTask(task, trigger);
//    }
//
//    public void addTriggerTask(TriggerTask task) {
//        taskRegistrar.addTriggerTask(task);
//    }
//
//    public void addCronTask(Runnable task, String expression) {
//        taskRegistrar.addCronTask(task, expression);
//    }
//
//    public void addCronTask(CronTask task) {
//        taskRegistrar.addCronTask(task);
//    }
//
//    public void addFixedRateTask(Runnable task, long interval) {
//        taskRegistrar.addFixedRateTask(task, interval);
//    }
//
//    public void addFixedRateTask(IntervalTask task) {
//        taskRegistrar.addFixedRateTask(task);
//    }
//
//    public void addFixedDelayTask(Runnable task, long delay) {
//        taskRegistrar.addFixedDelayTask(task, delay);
//    }
//
//    public void addFixedDelayTask(IntervalTask task) {
//        taskRegistrar.addFixedDelayTask(task);
//    }
//
//    public List<TriggerTask> getTriggerTaskList() {
//        return taskRegistrar.getTriggerTaskList();
//    }
//
//    public List<CronTask> getCronTaskList() {
//        return taskRegistrar.getCronTaskList();
//    }
//
//    public List<IntervalTask> getFixedRateTaskList() {
//        return taskRegistrar.getFixedRateTaskList();
//    }
//
//    public List<IntervalTask> getFixedDelayTaskList() {
//        return taskRegistrar.getFixedDelayTaskList();
//    }
//
//    public Set<ScheduledTask> getScheduledTasks() {
//        return taskRegistrar.getScheduledTasks();
//    }
}
