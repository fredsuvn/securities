package com.tousie.securities.common.logging;

import com.sonluo.spongebob.spring.server.Session;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author sunqian
 */
@Component
public class LoggingService {

    public void logCreateSession(Logger logger, Session session) {
        logger.info("Create session, id = {}, protocol = {}.", session.getId(), session.getProtocol());
    }

    public void logDestroySession(Logger logger, Session session) {
        logger.info("Destroy session, id = {}, protocol = {}.", session.getId(), session.getProtocol());
    }

    public void logDestroySession(Logger logger, String id, String protocol) {
        logger.info("Destroy session, id = {}, protocol = {}.", id, protocol);
    }

    public void logAddCache(Logger logger, String key) {
        logger.info("Add cache: {}.", key);
    }

    public void logRemoveCache(Logger logger, String key) {
        logger.info("Remove cache: {}.", key);
    }
}
