package com.tousie.securities.port;

import com.tousie.securities.common.AppConstants;
import com.tousie.securities.utils.Randoms;
import org.springframework.stereotype.Component;

@Component
public class RequestIdGenerator {

    public String generateId() {
        return Randoms.ofRange(AppConstants.RANDOM_ID_RANGE, 15);
    }
}
