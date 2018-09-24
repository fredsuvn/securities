package com.tousie.securities.service.account;

import com.alibaba.fastjson.JSON;
import com.sonluo.spongebob.spring.server.ApiService;
import com.sonluo.spongebob.spring.server.ApiServiceMapping;
import com.sonluo.spongebob.spring.server.BeanOperator;
import com.sonluo.spongebob.spring.server.Request;
import com.sonluo.spongebob.spring.server.impl.DefaultBeanOperator;
import com.tousie.securities.common.cache.CacheService;
import com.tousie.securities.common.id.IdService;
import com.tousie.securities.common.verifycode.VerifyCodeService;
import com.tousie.securities.mapper.AccountInfoMapper;
import com.tousie.securities.service.account.constants.UserConstants;
import com.tousie.securities.service.account.data.AccountRecord;
import com.tousie.securities.service.account.params.request.UserRegisterRequest;
import com.tousie.securities.service.account.params.request.VerifyCodeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author sunqian
 */
@ApiService
@ApiServiceMapping("account")
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Resource
    private AccountInfoMapper accountInfoMapper;

    @Resource
    private IdService idService;

    @Resource
    private CacheService cacheService;

    @Resource
    private VerifyCodeService verifyCodeService;

    private BeanOperator beanOperator = DefaultBeanOperator.INSTANCE;

    @ApiServiceMapping(exclude = "loginInterceptor")
    public void verifyCode(VerifyCodeRequest request) {
        verifyCodeService.sendVerifyCode(request.getServiceId(), request.getPhone());
    }

    @ApiServiceMapping(exclude = "loginInterceptor")
    public void register(UserRegisterRequest request) {
        AccountRecord accountRecord = beanOperator.convert(request, AccountRecord.class);
        String id = idService.next();
        accountRecord.setId(id);
        accountRecord.setCreateDate(new Date());
        accountRecord.setStatus(UserConstants.REGISTER);
        accountInfoMapper.insert(accountRecord);
        cacheService.putCache(AccountRecord.class + accountRecord.getId(), accountRecord);

        userAsync.senMessageForPhone(request.getPhone());
    }

    @ApiServiceMapping(exclude = "loginInterceptor")
    public void payback(Request request) {
        System.out.println(JSON.toJSONString(request.getContent()));
    }
}
