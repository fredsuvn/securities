package com.tousie.securities.port.http;

import com.sonluo.spongebob.spring.server.ServiceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author sunqian
 */
@ControllerAdvice("com.tousie.securities.port.http")
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ServiceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void ServiceNotFound() {
    }
}
