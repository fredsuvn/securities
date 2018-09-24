package com.tousie.securities.common.verifycode;

import com.tousie.securities.common.AppConstants;
import com.tousie.securities.common.async.AsyncService;
import com.tousie.securities.common.mdc.MdcService;
import com.tousie.securities.common.verifycode.sender.Sender;
import com.tousie.securities.mapper.VerifyCodeInfoMapper;
import com.tousie.securities.service.account.data.VerifyCodeRecord;
import com.tousie.securities.utils.Randoms;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class VerifyCodeService {

    private static final Logger logger = LoggerFactory.getLogger(VerifyCodeService.class);

    private static final String CODE_RANGE = "0123456789";

    @Resource
    private VerifyCodeProperties verifyCodeProperties;

    @Resource
    private MdcService mdcService;

    @Resource
    private VerifyCodeInfoMapper verifyCodeInfoMapper;

    @Resource
    private AsyncService asyncService;

    private Sender sender = null;
    private Map<String, Map<String, VerifyCode>> cache = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() throws Exception {
        sender = (Sender) Class.forName("com.tousie.securities.common.verifycode.sender."
                + StringUtils.capitalize(verifyCodeProperties.getSendMode())
                + "Sender").newInstance();
    }

    public void sendVerifyCode(String serviceId, String phone) {
        VerifyCode verifyCode = new VerifyCode(serviceId, phone, Randoms.ofRange(CODE_RANGE, 6));
        Map<String, VerifyCode> map = cache.computeIfAbsent(serviceId, id -> new ConcurrentHashMap<>());
        map.put(phone, verifyCode);
        if (!sender.send(Collections.singletonList(phone), verifyCode.getCode())) {
            map.remove(phone);
        }

        asyncService.submit(() -> {
            VerifyCodeRecord verifyCodeRecord = new VerifyCodeRecord();
            verifyCodeRecord.setServiceId(serviceId);
            verifyCodeRecord.setPhone(phone);
            verifyCodeRecord.setCode(verifyCode.getCode());
            verifyCodeInfoMapper.insert(verifyCodeRecord);
        });
    }

    public boolean verifyCode(String serviceId, String phone, String code) {
        Map<String, VerifyCode> map = cache.get(serviceId);
        if (MapUtils.isEmpty(map)) {
            return false;
        }
        VerifyCode verifyCode = map.get(phone);
        if (verifyCode == null) {
            return false;
        }
        if (System.currentTimeMillis() - verifyCode.getLastTime() > verifyCodeProperties.getExpiredInMinutes()) {
            return false;
        }
        return true;
    }

    @Scheduled(cron = "${verify-code.scheduling.cron}")
    public void clearExpired() {
        mdcService.putRequestId(verifyCodeProperties.getScheduling().getMdcPrefix() + "-" + Randoms.ofRange(AppConstants.RANDOM_ID_RANGE, 9));
        logger.info("Start clean up expired verify code.");

        int[] count = {0};
        cache.forEach((serviceId, codes) -> {
            codes.forEach((phone, code) -> {
                if (System.currentTimeMillis() - code.getLastTime() > verifyCodeProperties.getExpiredInMinutes() * 60 * 1000) {
                    codes.remove(phone);
                    count[0]++;
                }
            });
        });

        logger.info("End clean up expired verify code, count: {}.", count[0]);
        mdcService.removeRequestId();
    }
}
