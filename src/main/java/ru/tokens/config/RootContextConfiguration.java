package ru.tokens.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.concurrent.Executor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Controller;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 *
 * @author solon4ak
 */
@Configuration
@Import({FileUploadConfig.class, MailConfig.class})
@EnableAsync(proxyTargetClass = true)
@EnableScheduling
@ComponentScan(
        basePackages = "ru.tokens.site",
//        includeFilters = @ComponentScan.Filter(Service.class),
        excludeFilters = @ComponentScan.Filter(Controller.class)
)
public class RootContextConfiguration implements AsyncConfigurer, SchedulingConfigurer {

    private static final Logger log = LogManager.getLogger();
    private static final Logger schedulingLogger
            = LogManager.getLogger(log.getName() + " .[scheduling]");

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
        return mapper;
    }

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan(new String[]{"ru.tokens.site"});
        return marshaller;
    }

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        log.info("Setting up thread pool task scheduler with {] threads", 120);
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(1200);
        scheduler.setThreadNamePrefix("task-");
        scheduler.setAwaitTerminationSeconds(60);
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        scheduler.setErrorHandler(
                t -> schedulingLogger.error(
                        "Unknown error ocured while executing task", t
                )
        );
        scheduler.setRejectedExecutionHandler(
                (r, e) -> schedulingLogger.error(
                        "Execution of task {} was rejected for unknown reasons", r
                )
        );

        return scheduler;
    }

    @Override
    public Executor getAsyncExecutor() {
        Executor executor = this.taskScheduler();
        log.info("Configuring asynhcronous method executor {}.", executor);
        return executor;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        TaskScheduler scheduler = this.taskScheduler();
        log.info("Configuring scheduled method executor {}.", scheduler);
        taskRegistrar.setTaskScheduler(scheduler);
    }
    
    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }
    
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
        processor.setValidator(this.localValidatorFactoryBean());
        return processor;
    }
}
