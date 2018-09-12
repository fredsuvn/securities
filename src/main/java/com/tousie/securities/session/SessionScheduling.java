package com.tousie.securities.session;//package com.tousie.securities.session;
//
//import com.google.common.cache.Cache;
//import com.tousie.securities.common.AppConstants;
//import com.tousie.securities.common.mdc.MdcService;
//import com.tousie.securities.utils.Randoms;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.scheduled.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
//@Component
//public class SessionScheduling {
//
//    private static final Logger logger = LoggerFactory.getLogger(SessionScheduling.class);
//
//    @Resource
//    private SessionSchedulingProperties sessionSchedulingProperties;
//
//    @Resource
//    private MdcService mdcService;
//
//    @Resource
//    private SessionManager sessionManager;
//
//    @Scheduled(cron = "${scheduled.session.corn}")
//    public void clearInvalidatedSession() {
//        mdcService.putRequestId(sessionSchedulingProperties.getMdcPrefix() + "-" + Randoms.ofRange(AppConstants.RANDOM_ID_RANGE, 9));
//        logger.info("Starting to clear invalidated session: ");
//        sessionManager.forEachSessionCache(Cache::cleanUp);
//        logger.info("Cleared.");
//        mdcService.removeRequestId();
//    }
//}
