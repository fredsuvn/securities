package com.tousie.securities.service.account;

import com.sonluo.spongebob.spring.server.ApiService;
import com.sonluo.spongebob.spring.server.ApiServiceMapping;
import com.sonluo.spongebob.spring.server.BeanOperator;
import com.sonluo.spongebob.spring.server.Session;
import com.sonluo.spongebob.spring.server.impl.DefaultBeanOperator;
import com.tousie.securities.common.cache.CacheService;
import com.tousie.securities.common.id.IdService;
import com.tousie.securities.common.status.StatusEnum;
import com.tousie.securities.common.verifycode.VerifyCodeService;
import com.tousie.securities.exception.BusinessException;
import com.tousie.securities.mapper.AccountMapper;
import com.tousie.securities.mapper.AccountRecordMapper;
import com.tousie.securities.service.account.constants.AccountStatus;
import com.tousie.securities.service.account.constants.AccountType;
import com.tousie.securities.service.account.constants.RecordChangeType;
import com.tousie.securities.service.account.constants.RecordOperationType;
import com.tousie.securities.service.account.data.AccountRecord;
import com.tousie.securities.service.account.data.AccountRecordRecord;
import com.tousie.securities.service.account.model.AccountModel;
import com.tousie.securities.service.account.model.ManagerModel;
import com.tousie.securities.service.account.params.request.LoginRequest;
import com.tousie.securities.service.account.params.request.UserRegisterRequest;
import com.tousie.securities.service.account.params.request.VerifyCodeRequest;
import org.apache.catalina.Manager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author sunqian
 */
@ApiService
@ApiServiceMapping("account")
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Resource
    private AccountMapper accountMapper;

    @Resource
    private AccountRecordMapper accountRecordMapper;

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
    public void checkPhone(String phone) {
        AccountRecord condition = new AccountRecord();
        condition.setPhone(phone);
        if (accountMapper.selectOne(condition) != null) {
            throw new BusinessException(StatusEnum.REPEAT_PHONE);
        }
    }

    @ApiServiceMapping(exclude = "loginInterceptor")
    public void checkName(String name) {
        AccountRecord condition = new AccountRecord();
        condition.setNickName(name);
        if (accountMapper.selectOne(condition) != null) {
            throw new BusinessException(StatusEnum.REPEAT_NAME);
        }
    }

    @ApiServiceMapping(exclude = "loginInterceptor")
    public AccountModel register(UserRegisterRequest request, Session session) {

        if (!verifyCodeService.verifyCode("register", request.getPhone(), request.getVerifyCode())) {
            throw new BusinessException(StatusEnum.VERIFY_CODE_ERROR);
        }

        checkPhone(request.getPhone());
        checkName(request.getNickName());

        AccountRecord accountRecord = beanOperator.convert(request, AccountRecord.class);
        String id = idService.next("account");
        accountRecord.setId(id);
        accountRecord.setCreateDate(new Date());
        accountRecord.setStatus(AccountStatus.COMMON.getCode());
        accountRecord.setType(AccountType.COMMON.getCode());

        long amt = 5000000L;
        accountRecord.setAmt(amt);
        accountRecord.setIncome(amt);

        if (request.getInviteCode() != null) {
            try {
                String refereeId = idService.to10Radix(request.getInviteCode());
                accountRecord.setRefereeId(refereeId);
            } catch (Exception e) {
                throw new BusinessException(StatusEnum.INVITE_CODE_ERROR);
            }
        }

        accountMapper.insert(accountRecord);

        AccountRecordRecord recordRecord = new AccountRecordRecord();
        recordRecord.setId(idService.next());
        recordRecord.setAccountId(id);
        recordRecord.setAmt(amt);
        recordRecord.setChangeType(RecordChangeType.IN.getCode());
        recordRecord.setOperationType(RecordOperationType.REGISTER.getCode());
        recordRecord.setDesc(RecordOperationType.REGISTER.getDesc());
        accountRecordMapper.insert(recordRecord);

        AccountModel accountModel = beanOperator.convert(accountRecord, AccountModel.class);
        cacheService.putCache(AccountModel.class + accountModel.getId(), accountModel);
        session.setAttribute(AccountModel.class, id);
        return accountModel;
    }

    @ApiServiceMapping(exclude = "loginInterceptor")
    public AccountModel login(LoginRequest request, Session session) {
        Example example = new Example(AccountRecord.class);
        example.createCriteria().andEqualTo("phone", request.getPhone());
        AccountRecord record = accountMapper.selectOneByExample(example);
        if (record == null) {
            throw new BusinessException(StatusEnum.LOGIN_IN_WRONG_ACCOUNT);
        }
        if (!request.getPassword().equals(record.getPassword())) {
            throw new BusinessException(StatusEnum.LOGIN_IN_WRONG_PASSWORD);
        }
        AccountModel accountModel = getAccountModel(record.getId());
        session.setAttribute(AccountModel.class, accountModel.getId());
        return accountModel;
    }

    @ApiServiceMapping(exclude = "loginInterceptor")
    public void logout(Session session) {
        session.removeAttribute(AccountModel.class);
    }

    @ApiServiceMapping(include = "managerInterceptor")
    public List<AccountModel> getManagedAccountModels(Session session) {
        String accountId = (String) session.getAttribute(AccountModel.class);
        AccountRecord condition = new AccountRecord();
        condition.setManagerId(accountId);
        return accountMapper.select(condition).stream().map(r -> {
            AccountModel accountModel = beanOperator.convert(r, AccountModel.class);
        });
    }

    public AccountModel getAccountModel(String accountId) {
        return cacheService.getCache(AccountModel.class + accountId, () -> {
            AccountRecord record = accountMapper.selectByPrimaryKey(accountId);
            if (record == null) {
                return null;
            }
            AccountModel accountModel = beanOperator.convert(record, AccountModel.class);

            if (!AccountType.MANAGER.getCode().equals(record.getType()) && record.getManagerId() != null) {
                AccountModel manager = getAccountModel(record.getManagerId());
                if (manager != null) {
                    accountModel.setManager(beanOperator.convert(manager, ManagerModel.class));
                }
            }

            return accountModel;
        });
    }

    public void updateAccountModel(AccountModel accountModel) {
        AccountRecord record = beanOperator.convert(accountModel, AccountRecord.class);
        accountMapper.updateByPrimaryKeySelective(record);
        cacheService.putCache(AccountModel.class + accountModel.getId(), accountModel);
    }
}
