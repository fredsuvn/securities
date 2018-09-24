package com.tousie.securities.common.verifycode.sender;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Deprecated
public class CloopenSender implements Sender {

    @Override
    public boolean send(List<String> phones, String code) {

//        String uid = "7da00b9009d843893cb527e9";
//        String token = "2fdab1da65fc8ad4c1820a84f3f6f274";
//
////        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
////        String timestamp = formatter.format(LocalDateTime.now());
////        String SigParameter =
////                DigestUtils.md5Hex(accountSid + authToken + timestamp).toUpperCase();
//
//
//        String url = String.format("https://pay.bbbapi.com/?format=json");
//
//        Map<String, String> params = new LinkedHashMap<>();
//        params.put("goodsname", "测试商品");
//        params.put("istype", "2");
//        params.put("notify_url", "http://www.cogician.com:18088/service/user.payback");
//        params.put("orderid", idService.next());
//        params.put("orderuid", idService.next().substring(0, 33));
//        params.put("price", "0.05");
//        params.put("return_url", "http://www.cogician.com:18088/service/user.payback");
////        params.put("token", token);
//        params.put("uid", uid);
//        params.put("key", getKey(params, token));
//
//        try {
//            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
//            connection.setDoInput(true);
//            connection.setDoOutput(true);
//
//            connection.setRequestMethod("POST");
//
////            JSONObject json = new JSONObject();
////            json.put("to", "15251897368");
////            json.put("appId", "8aaf070865c25ae60165d71cea470f80");
////            json.put("templateId", "1");
////            json.put("datas", new String[]{"666", "999"});
////            json.put("data", new String[]{"666", "999"});
////            String body = json.toJSONString();
//
////            connection.setRequestProperty("Accept", "application/json");
//            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
////            connection.setRequestProperty("Content-Length", String.valueOf(body.length()));
////            Base64 base64 = new Base64();
////            String authorization = base64.encodeToString((accountSid + ":" + timestamp).getBytes());
////            connection.setRequestProperty("Authorization", authorization);
//
//            String body = toKeyValue(params);
//            connection.getOutputStream().write(body.getBytes());
//
//            InputStream inputStream = connection.getInputStream();
//            String ret = IOUtils.toString(inputStream, "UTF-8");
//            System.out.println(ret);
//            return JSON.parseObject(ret);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return false;
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
