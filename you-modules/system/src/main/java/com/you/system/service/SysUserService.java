package com.you.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.you.system.domain.bo.SysUserBo;
import com.you.system.domain.qo.AuthUserQo;
import com.you.system.domain.qo.UserQo;
import com.you.system.domain.vo.SysUserVo;
import com.you.system.model.LoginUser;
import com.you.system.model.SysUser;

import java.util.List;

public interface SysUserService extends IService<SysUser> {

    /**
     * 根据username获取用户信息
     *
     * @param username 登录名
     * @return 用户
     */
    SysUser getUserByUsername(String username);

    /**
     * 根据userId获取用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    LoginUser getLoginUserByUserId(Long userId);

    /**
     * 角色已分配用户分页
     *
     * @param qo 查询条件
     * @return 分页数据
     */
    IPage<SysUser> ruleAllocatedListPage(AuthUserQo qo);

    /**
     * 角色未分配用户分页
     *
     * @param qo 查询条件
     * @return 分页数据
     */
    IPage<SysUser> ruleUnallocatedListPage(AuthUserQo qo);

    /**
     * 用户分页
     *
     * @param qo 查询条件
     * @return 分页数据
     */
    IPage<SysUserVo> listPage(UserQo qo);

    /**
     * 保存用户
     *
     * @param user 前端数据
     * @return 是否成功
     */
    boolean save(SysUserBo user);

    /**
     * 校验用户名是否已存在
     *
     * @param userId   用户id
     * @param username 用户名
     */
    void checkUsernameUnique(Long userId, String username);

    /**
     * 删除用户
     *
     * @param userIds 用户id list
     * @return 是否成功
     */
    boolean removeByIds(List<Long> userIds);

    /**
     * 修改用户状态
     *
     * @param sysUser 用户数据
     * @return 是否成功
     */
    boolean changeStatus(SysUser sysUser);
}
