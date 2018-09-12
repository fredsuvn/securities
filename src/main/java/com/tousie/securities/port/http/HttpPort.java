package com.tousie.securities.port.http;

import com.sonluo.spongebob.spring.server.Server;
import com.tousie.securities.common.mdc.MdcService;
import com.tousie.securities.common.message.MessageService;
import com.tousie.securities.common.status.StatusEnum;
import com.tousie.securities.model.message.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class HttpPort {

    private static final Logger logger = LoggerFactory.getLogger(HttpPort.class);

    @Resource
    private Server globalServer;

    @Resource
    private MdcService mdcService;

    @Resource
    private MessageService messageService;

    @RequestMapping("service/{url:.+}")
    public ResponseMessage port(@PathVariable("url") String url, @RequestParam Map<String, Object> requestParam,
                                HttpServletRequest servletRequest) {
        mdcService.putRequestId();
        Object result = globalServer.doService(new HttpRequest(url, requestParam, servletRequest));
        ResponseMessage response = messageService.toResponseMessage(result);
        mdcService.removeRequestId();
        return response;
    }
}
