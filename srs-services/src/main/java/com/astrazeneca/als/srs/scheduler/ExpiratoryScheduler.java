package com.astrazeneca.als.srs.scheduler;

import com.astrazeneca.als.srs.service.SchedulerService;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by krvh271 on 5/20/17.
 */
@Component
public class ExpiratoryScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpiratoryScheduler.class);

    @Autowired
    SchedulerService schedulerService;

    @Scheduled(cron = "${srs.scheduler}")
    public void schedule() {
        LOGGER.info("Scheduler started at:" + new Date(System.currentTimeMillis()));
        schedulerService.monitorAvailability();
    }
}
