package com.tousie.securities.common.verifycode.sender;

import java.util.List;

public interface Sender {

    boolean send(List<String> phones, String code);
}
