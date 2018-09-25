package com.tousie.securities.interceptors;

import com.sonluo.spongebob.spring.server.Request;
import com.sonluo.spongebob.spring.server.ServiceCallInterceptor;
import com.sonluo.spongebob.spring.server.Session;
import com.tousie.securities.common.status.StatusEnum;
import com.tousie.securities.exception.BusinessException;
import com.tousie.securities.service.account.AccountService;
import com.tousie.securities.service.account.constants.AccountType;
import com.tousie.securities.service.account.model.AccountModel;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.Map;

/**
 * @author sunqian
 */
@Component
public class ManagerInterceptor implements ServiceCallInterceptor {

    @Resource
    private AccountService accountService;

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE + 200;
    }

    @Override
    public void doIntercept(Request request, Object[] args, @Nullable Object result, Map<Object, Object> requestLocal) {
        Session session = request.getSession(true);
        String accountId = (String) session.getAttribute(AccountModel.class);
        AccountModel accountModel = accountService.getAccountModel(accountId);
        if (!AccountType.MANAGER.getCode().equals(accountModel.getType())) {
            throw new BusinessException(StatusEnum.NEED_MANAGER);
        }
    }

    @Override
    public boolean joinDefaultGroup() {
        return false;
    }
}
