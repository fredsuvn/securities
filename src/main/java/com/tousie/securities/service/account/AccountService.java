package com.tousie.securities.service.account;

import com.alibaba.fastjson.JSON;
import com.sonluo.spongebob.spring.server.ApiService;
import com.sonluo.spongebob.spring.server.ApiServiceMapping;
import com.sonluo.spongebob.spring.server.BeanOperator;
import com.sonluo.spongebob.spring.server.Request;
import com.sonluo.spongebob.spring.server.impl.DefaultBeanOperator;
import com.tousie.securities.common.cache.CacheService;
import com.tousie.securities.common.id.IdService;
import com.tousie.securities.common.status.StatusEnum;
import com.tousie.securities.common.verifycode.VerifyCodeService;
import com.tousie.securities.exception.BusinessException;
import com.tousie.securities.mapper.AccountMapper;
import com.tousie.securities.service.account.constants.AccountConstants;
import com.tousie.securities.service.account.constants.AccountStatus;
import com.tousie.securities.service.account.constants.AccountType;
import com.tousie.securities.service.account.data.AccountRecord;
import com.tousie.securities.service.account.model.AccountModel;
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
    private AccountMapper accountInfoMapper;

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

        if (!verifyCodeService.verifyCode("register", request.getPhone(), request.getVerifyCode())) {
            throw new BusinessException(StatusEnum.VERIFY_CODE_ERROR);
        }

        AccountRecord accountRecord = beanOperator.convert(request, AccountRecord.class);
        String id = idService.next("account");
        accountRecord.setId(id);
        accountRecord.setCreateDate(new Date());
        accountRecord.setStatus(AccountStatus.COMMON.getCode());
        accountRecord.setType(AccountType.COMMON.getCode());
        accountRecord.setAmt(5000000L);

        if (request.getInviteCode() != null) {
            try {
                String refereeId = idService.to10Radix(request.getInviteCode());
                accountRecord.setRefereeId(refereeId);
            } catch (Exception e) {
                throw new BusinessException(StatusEnum.INVITE_CODE_ERROR);
            }
        }

        accountInfoMapper.insert(accountRecord);
        cacheService.putCache(AccountRecord.class + accountRecord.getId(), accountRecord);

    }

    public AccountModel getAccountModel(String accountId) {
        int[] info = {0, 0, 0, 0};
        AccountModel accountModel = cacheService.getCache(AccountModel.class + accountId, () -> {
            info[0] = 1;
            AccountRecord record = accountInfoMapper.selectByPrimaryKey(accountId);
            if (record == null) {
                return null;
            }
            if (record.getParentId() != null) {
                info[1] = 1;
            }
            if (record.getRefereeId() != null) {
                info[2] = 1;
            }
            if (record.getManagerId() != null) {
                info[3] = 1;
            }
            return beanOperator.convert(record, AccountModel.class);
        });
        if (info[0] == 0) {
            return accountModel;
        }
        if (info[1] == 1) {
            AccountModel parent =
        }
        if (info[1] == 1) {

        }
        if (info[1] == 1) {

        }
    }
}
