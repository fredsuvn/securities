package com.tousie.securities.service.account;

import com.sonluo.spongebob.spring.server.ApiService;
import com.sonluo.spongebob.spring.server.ApiServiceMapping;
import com.tousie.securities.common.id.IdService;
import com.tousie.securities.mapper.UserInfoMapper;
import com.tousie.securities.service.account.data.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

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

    @ApiServiceMapping
    public UserInfo testInsert(UserInfo userInfo) {
        userInfo.setId(idService.next());
        userInfoMapper.insert(userInfo);
        return userInfo;
    }
}
