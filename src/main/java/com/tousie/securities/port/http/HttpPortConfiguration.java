package com.tousie.securities.port.http;

import com.google.common.base.Charsets;
import org.apache.catalina.Context;
import org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class HttpPortConfiguration {

    @Resource
    private HttpPortProperties httpPortProperties;

    @Bean
    public WebServerFactoryCustomizer<ConfigurableTomcatWebServerFactory> webServerFactoryCustomizer() {
        return new WebServerFactoryCustomizer<ConfigurableTomcatWebServerFactory>() {
            @Override
            public void customize(ConfigurableTomcatWebServerFactory factory) {
                factory.setPort(httpPortProperties.getPort());
                factory.setUriEncoding(Charsets.UTF_8);
                factory.addContextCustomizers(new TomcatContextCustomizer() {
                    @Override
                    public void customize(Context context) {
                        context.setSessionTimeout(httpPortProperties.getSessionTimeoutInMinutes());
                        context.setRequestCharacterEncoding("UTF-8");
                        context.setResponseCharacterEncoding("UTF-8");
                        context.setBackgroundProcessorDelay(httpPortProperties.getBackgroundProcessDelayInSeconds());
                    }
                });
            }
        };
    }
}
