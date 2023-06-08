package com.you.auth.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.you.auth.model.User;
import com.you.auth.service.LoginService;
import com.you.common.core.constant.LoginConstants;
import com.you.common.core.enums.UserStatus;
import com.you.common.core.exception.CommonException;
import com.you.common.core.model.R;
import com.you.common.core.utils.StrUtils;
import com.you.system.api.feign.RemoteUserService;
import com.you.system.model.LoginUser;
import org.springframework.stereotype.Service;

/**
 * 登录service实现类
 *
 * @author Eric
 * @since 1.0
 * Create with Intellij IDEA on 2023-04-20 15:26
 */
@Service
public class LoginServiceImpl implements LoginService {

    private final RemoteUserService remoteUserService;

    public LoginServiceImpl(RemoteUserService remoteUserService) {
        this.remoteUserService = remoteUserService;
    }

    @Override
    public User login(String username, String password) {
        if (StrUtils.isAnyBlank(username, password)) {
            throw new CommonException("用户名/密码必须填写");
        }
        //查询用户
        R<LoginUser> loginUserResult = remoteUserService.getUserByUsername(username);
        if (null == loginUserResult || null == loginUserResult.getData()) {
            throw new CommonException("账号或密码不正确");
        }
        //接口报错返回错误信息
        if (R.FAIL == loginUserResult.getCode()) {
            throw new CommonException(loginUserResult.getMsg());
        }
        LoginUser loginUser = loginUserResult.getData();
        // 校验用户状态
        if (UserStatus.DISABLE.getCode().equals(loginUser.getStatus())) {
            throw new CommonException("对不起，您的账号已被禁用");
        }
        // 校验用户密码
        if (!password.equals(loginUser.getPassword())) {
            throw new CommonException("账号或密码不正确");
        }
        // sa-token登录
        StpUtil.login(loginUser.getUserId(), LoginConstants.DEVICE_PC);
        SaTokenInfo token = StpUtil.getTokenInfo();
        return new User(loginUser.getUserId(), loginUser.getUsername(), token);
    }
}
