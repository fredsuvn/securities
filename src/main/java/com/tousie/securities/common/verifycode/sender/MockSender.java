package com.tousie.securities.common.verifycode.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MockSender implements Sender {

    private static final Logger logger = LoggerFactory.getLogger(MockSender.class);

    @Override
    public boolean send(List<String> phones, String code) {
        logger.info("Mock sender send for phones {} with code>>>>>>>>>>>>>>>> {}.", phones, code);
        return true;
    }
}
