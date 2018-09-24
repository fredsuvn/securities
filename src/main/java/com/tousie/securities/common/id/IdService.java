package com.tousie.securities.common.id;

import com.tousie.securities.common.AppConstants;
import com.tousie.securities.mapper.IdMapper;
import com.tousie.securities.utils.Randoms;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sunqian
 */
@Component
public class IdService {

    //    private static final String RANDOM_RANGE = "0123456789";
    private static final String RADIX_36 = "0123456789abcdefghijklmnopqrstuvwxyz".toUpperCase();

    @Value("${app.instanceId}")
    private String instanceId = "00000";

    @Resource
    private IdMapper idMapper;

    private Map<String, IdGenerator> idRecordMap = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        idMapper.selectAll().forEach(r -> {
            IdGenerator idGenerator = new IdGenerator(idMapper, r);
            idRecordMap.put(r.getName(), idGenerator);
        });
    }

    public String next() {
        return buildTimestamp() + "-" + buildRandomString() + "-" + instanceId;
    }

    public String next(String idName) {
        IdGenerator idGenerator = idRecordMap.get(idName);
        return String.valueOf(idGenerator.next());
    }

    public String to36Radix(String id) {
        long b = Long.valueOf(id);
        String nb = "";
        while (b != 0) {
            Long c = b % 36;
            Long d = b / 36;
            nb = RADIX_36.charAt(c.intValue()) + nb;
            b = Math.round(Math.floor(d));
        }
        return nb.toUpperCase();
    }

    public String to10Radix(String num36) {
        HashMap<Character, Long> map = new HashMap<>();
        for (int i = 0; i < RADIX_36.length(); i++) {
            map.put(RADIX_36.charAt(i), (long) i);
        }

        int size = num36.length();
        long num = 0;
        for (int i = 0; i < size; i++) {
            String char2str = String.valueOf(num36.charAt(i)).toUpperCase();
            num = (long) (map.get(char2str.charAt(0)) * Math.pow(36, size - i - 1) + num);
        }

        return String.valueOf(num);
    }

    private String buildTimestamp() {
        long l = System.currentTimeMillis();
        return StringUtils.leftPad(String.valueOf(l), 19, '0');
    }

    private String buildRandomString() {
        return Randoms.ofRange(AppConstants.RANDOM_ID_RANGE, 8);
    }
}
