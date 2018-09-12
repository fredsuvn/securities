package com.tousie.securities.port.websocket;

import com.tousie.securities.common.AppConstants;
import com.tousie.securities.common.mdc.MdcService;
import com.tousie.securities.utils.Randoms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

@Configuration
public class WebSocketScheduling {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketScheduling.class);

    @Resource
    private WebSocketPortProperties webSocketPortProperties;

    @Resource
    private WebSocketSessionManager webSocketSessionManager;

    @Resource
    private MdcService mdcService;

    @Scheduled(cron = "${port.websocket.scheduling.cron}")
    public void sessionScheduling() {
        mdcService.putRequestId(webSocketPortProperties.getScheduling().getMdcPrefix() + "-" + Randoms.ofRange(AppConstants.RANDOM_ID_RANGE, 9));
        logger.info("Starting clean up webSocket session inactive greater than {} minutes.", webSocketPortProperties.getSessionTimeoutInMinutes());

        int[] i = {0};
        long begin = System.currentTimeMillis();
        webSocketSessionManager.forEachSession(session -> {
            try {
                if (!session.isOpen()) {
                    webSocketSessionManager.removeSession(session.getId());
                    logger.info("Remove from manager, id = {}.", session.getId());
                    i[0]++;
                    return;
                }

                long inactive = System.currentTimeMillis() - session.getLastActiveTime();
                if (inactive > webSocketPortProperties.getSessionTimeoutInMinutes() * 60 * 1000) {
                    String id = session.getId();
                    webSocketSessionManager.closeSession(id);
                    logger.info("Close and remove from manager, id = {}.", id);
                    i[0]++;
                }
            } catch (Exception e) {
                logger.error("An error occurs.", e);
            }
        });
        long end = System.currentTimeMillis();

        logger.info("Cleaned {} webSocket session, cost {} ms.", i[0], (end - begin));
        mdcService.removeRequestId();
    }


//        schedulingService.addCronTask(new CronTask(() -> {

//        }, webSocketPortProperties.getScheduling().getCorn()));
}
