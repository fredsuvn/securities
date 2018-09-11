package com.tousie.securities.port.http;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author sunqian
 */
@Configuration
@ConfigurationProperties(prefix = "port.http")
public class HttpPortProperties {

    private int port;

    private int sessionTimeoutInMinutes;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getSessionTimeoutInMinutes() {
        return sessionTimeoutInMinutes;
    }

    public void setSessionTimeoutInMinutes(int sessionTimeoutInMinutes) {
        this.sessionTimeoutInMinutes = sessionTimeoutInMinutes;
    }
}
