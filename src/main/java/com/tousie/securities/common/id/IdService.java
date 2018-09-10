package com.tousie.securities.common.id;

import com.tousie.securities.common.AppConstants;
import com.tousie.securities.utils.Randoms;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author sunqian
 */
@Component
public class IdService {

    @Value("${app.instanceId}")
    private String instanceId;

    public String next() {
        return buildTimestamp() + "-" + buildRandomString() + "-" + instanceId;
    }

    private String buildTimestamp() {
        long l = System.currentTimeMillis();
        return StringUtils.leftPad(String.valueOf(l), 19, '0');
    }

    private String buildRandomString() {
        return Randoms.ofRange(AppConstants.RANDOM_ID_RANGE, 8);
    }
}
