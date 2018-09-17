package com.tousie.securities.service.account;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.sonluo.spongebob.spring.server.ApiService;
import com.sonluo.spongebob.spring.server.ApiServiceMapping;
import com.sonluo.spongebob.spring.server.BeanOperator;
import com.sonluo.spongebob.spring.server.Request;
import com.sonluo.spongebob.spring.server.impl.DefaultBeanOperator;
import com.tousie.securities.common.cache.CacheService;
import com.tousie.securities.common.id.IdService;
import com.tousie.securities.mapper.UserInfoMapper;
import com.tousie.securities.service.account.constants.UserConstants;
import com.tousie.securities.service.account.data.UserInfo;
import com.tousie.securities.service.account.params.request.UserRegisterRequest;
import com.tousie.securities.service.account.params.request.VerifyPhoneRequest;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author sunqian
 */
@ApiService
@ApiServiceMapping("user")
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private IdService idService;

    @Resource
    private CacheService cacheService;

    @Resource
    private UserAsync userAsync;

    private BeanOperator beanOperator = DefaultBeanOperator.INSTANCE;

    @ApiServiceMapping(exclude = "loginInterceptor")
    public void register(UserRegisterRequest request) {
        UserInfo userInfo = beanOperator.convert(request, UserInfo.class);
        String id = idService.next();
        userInfo.setId(id);
        userInfo.setCreateDate(new Date());
        userInfo.setStatus(UserConstants.REGISTER);
        userInfoMapper.insert(userInfo);
        cacheService.putCache(UserInfo.class + userInfo.getId(), userInfo);

        userAsync.senMessageForPhone(request.getPhone());
    }

    @ApiServiceMapping(exclude = "loginInterceptor")
    public void verifyPhone(VerifyPhoneRequest request) {

        String accountSid = "8aaf070865c25ae60165d71ce9f70f7a";
        String authToken = "8404aa0d04574a5cbc01195b3ebbf05a";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = formatter.format(LocalDateTime.now());
        String SigParameter =
                DigestUtils.md5Hex(accountSid + authToken + timestamp).toUpperCase();


        String url = String.format("https://app.cloopen.com:8883/2013-12-26/Accounts/%s/SMS/TemplateSMS?sig=%s", accountSid, SigParameter);

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.setRequestMethod("POST");

            JSONObject json = new JSONObject();
            json.put("to", "15251897368");
            json.put("appId", "8aaf070865c25ae60165d71cea470f80");
            json.put("templateId", "1");
            json.put("datas", new String[]{"666", "999"});
            json.put("data", new String[]{"666", "999"});
            String body = json.toJSONString();

            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            connection.setRequestProperty("Content-Length", String.valueOf(body.length()));
            Base64 base64 = new Base64();
            String authorization = base64.encodeToString((accountSid + ":" + timestamp).getBytes());
            connection.setRequestProperty("Authorization", authorization);

            connection.getOutputStream().write(body.getBytes());

            InputStream inputStream = connection.getInputStream();
            System.out.println(IOUtils.toString(inputStream, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiServiceMapping(exclude = "loginInterceptor")
    public void payback(Request request) {
        System.out.println(JSON.toJSONString(request.getContent()));
    }


    @ApiServiceMapping(exclude = "loginInterceptor")
    public Object testPay(VerifyPhoneRequest request) {

        String uid = "7da00b9009d843893cb527e9";
        String token = "2fdab1da65fc8ad4c1820a84f3f6f274";

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
//        String timestamp = formatter.format(LocalDateTime.now());
//        String SigParameter =
//                DigestUtils.md5Hex(accountSid + authToken + timestamp).toUpperCase();


        String url = String.format("https://pay.bbbapi.com/?format=json");

        Map<String, String> params = new LinkedHashMap<>();
        params.put("goodsname", "测试商品");
        params.put("istype", "2");
        params.put("notify_url", "http://www.cogician.com:18088/service/user.payback");
        params.put("orderid", idService.next());
        params.put("orderuid", idService.next().substring(0, 33));
        params.put("price", "0.05");
        params.put("return_url", "http://www.cogician.com:18088/service/user.payback");
//        params.put("token", token);
        params.put("uid", uid);
        params.put("key", getKey(params, token));

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.setRequestMethod("POST");

//            JSONObject json = new JSONObject();
//            json.put("to", "15251897368");
//            json.put("appId", "8aaf070865c25ae60165d71cea470f80");
//            json.put("templateId", "1");
//            json.put("datas", new String[]{"666", "999"});
//            json.put("data", new String[]{"666", "999"});
//            String body = json.toJSONString();

//            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            connection.setRequestProperty("Content-Length", String.valueOf(body.length()));
//            Base64 base64 = new Base64();
//            String authorization = base64.encodeToString((accountSid + ":" + timestamp).getBytes());
//            connection.setRequestProperty("Authorization", authorization);

            String body = toKeyValue(params);
            connection.getOutputStream().write(body.getBytes());

            InputStream inputStream = connection.getInputStream();
            String ret = IOUtils.toString(inputStream, "UTF-8");
            System.out.println(ret);
            return JSON.parseObject(ret);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String toKeyValue(Map<String, String> params) {
        StringBuilder ret = new StringBuilder("1=1");
        params.forEach((k, v) ->{
                    try {
                        String key = URLEncoder.encode(k, "UTF-8");
                        String value = URLEncoder.encode(v, "UTF-8");
                        ret.append("&" + key + "=" + value);
                    } catch (Exception e) {
                    }
                }
        );
        return ret.toString();
    }

    private String getKey(Map<String, String> remoteMap, String token) {
        String key = "";
        if (null != remoteMap.get("goodsname")) {
            key += remoteMap.get("goodsname");
        }
        if (null != remoteMap.get("istype")) {
            key += remoteMap.get("istype");
        }
        if (null != remoteMap.get("notify_url")) {
            key += remoteMap.get("notify_url");
        }
        if (null != remoteMap.get("orderid")) {
            key += remoteMap.get("orderid");
        }
        if (null != remoteMap.get("orderuid")) {
            key += remoteMap.get("orderuid");
        }
        if (null != remoteMap.get("price")) {
            key += remoteMap.get("price");
        }
        if (null != remoteMap.get("return_url")) {
            key += remoteMap.get("return_url");
        }
        key += token;
        if (null != remoteMap.get("uid")) {
            key += remoteMap.get("uid");
        }
        return DigestUtils.md5Hex(key).toLowerCase();
    }

}
