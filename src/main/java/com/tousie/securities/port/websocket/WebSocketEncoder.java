package com.tousie.securities.port.websocket;

import com.alibaba.fastjson.JSON;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @author sunqian
 */
public class WebSocketEncoder implements Encoder.Text<Object> {

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public String encode(Object object) throws EncodeException {
        return JSON.toJSONString(object);
    }
}
