package com.tousie.securities.port.websocket;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author sunqian
 */
@Configuration
@ConfigurationProperties(prefix = "port.websocket")
public class WebSocketPortProperties {

    private int sessionTimeoutInMinutes;
    private Scheduling scheduling;
    private int maxMessageLogWidth;

    public int getSessionTimeoutInMinutes() {
        return sessionTimeoutInMinutes;
    }

    public void setSessionTimeoutInMinutes(int sessionTimeoutInMinutes) {
        this.sessionTimeoutInMinutes = sessionTimeoutInMinutes;
    }

    public Scheduling getScheduling() {
        return scheduling;
    }

    public void setScheduling(Scheduling scheduling) {
        this.scheduling = scheduling;
    }

    public int getMaxMessageLogWidth() {
        return maxMessageLogWidth;
    }

    public void setMaxMessageLogWidth(int maxMessageLogWidth) {
        this.maxMessageLogWidth = maxMessageLogWidth;
    }

    public static class Scheduling{

        private String mdcPrefix;
        private String cron;

        public String getMdcPrefix() {
            return mdcPrefix;
        }

        public void setMdcPrefix(String mdcPrefix) {
            this.mdcPrefix = mdcPrefix;
        }

        public String getCron() {
            return cron;
        }

        public void setCron(String cron) {
            this.cron = cron;
        }
    }
}
