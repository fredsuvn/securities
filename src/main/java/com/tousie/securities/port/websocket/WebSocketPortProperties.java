package com.tousie.securities.port.websocket;

import com.tousie.securities.common.async.SchedulingProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author sunqian
 */
@Configuration
@ConfigurationProperties(prefix = "port.websocket")
public class WebSocketPortProperties {

    private int sessionTimeoutInMinutes;
    private SchedulingProperties scheduling;
    private int maxMessageLogWidth;

    public int getSessionTimeoutInMinutes() {
        return sessionTimeoutInMinutes;
    }

    public void setSessionTimeoutInMinutes(int sessionTimeoutInMinutes) {
        this.sessionTimeoutInMinutes = sessionTimeoutInMinutes;
    }

    public SchedulingProperties getScheduling() {
        return scheduling;
    }

    public void setScheduling(SchedulingProperties scheduling) {
        this.scheduling = scheduling;
    }

    public int getMaxMessageLogWidth() {
        return maxMessageLogWidth;
    }

    public void setMaxMessageLogWidth(int maxMessageLogWidth) {
        this.maxMessageLogWidth = maxMessageLogWidth;
    }
}
