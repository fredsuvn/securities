package com.tousie.securities.session;

import com.tousie.securities.common.mdc.MdcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SessionScheduled {

    private static final Logger logger = LoggerFactory.getLogger(SessionScheduled.class);

    @Resource
    private MdcService mdcService;

    @Scheduled(cron = "0/5 * * * * *")
    public void clearInvalidatedSession() {
        mdcService.putRequestId("[clear-session]");
        logger.info("Starting to clear invalidated session: ");
        logger.info("Cleared.");
        mdcService.removeRequestId();
    }
}
