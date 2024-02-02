package com.you.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.you.common.core.domain.model.SysRole;
import com.you.system.domain.bo.SysRoleBo;
import com.you.system.domain.qo.RoleQo;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {

    /**
     * 角色分页
     *
     * @param roleQo 查询条件
     * @return 分页数据
     */
    IPage<SysRole> listPage(RoleQo roleQo);

    /**
     * 修改角色状态
     *
     * @param roleId 角色id
     * @param status 角色状态
     * @return 是否成功
     */
    boolean updateRoleStatus(Long roleId, String status);

    /**
     * 校验角色名称唯一
     *
     * @param roleId   角色id
     * @param roleName 角色名称
     */
    void checkRoleNameUnique(Long roleId, String roleName);

    /**
     * 校验权限字符串唯一
     *
     * @param roleId  角色id
     * @param roleKey 权限字符串
     */
    void checkRoleKeyUnique(Long roleId, String roleKey);

    /**
     * 新增角色
     *
     * @param role 新增角色实体
     * @return 是否成功
     */
    boolean save(SysRoleBo role);

    /**
     * 根据角色id获取角色信息
     *
     * @param roleId 角色id
     * @return 角色信息
     */
    SysRoleBo getRoleById(Long roleId);

    /**
     * 编辑角色
     *
     * @param role 角色实体
     * @return 是否成功
     */
    boolean edit(SysRoleBo role);

    /**
     * 根据角色id 批量删除角色
     *
     * @param roleIds 角色id List
     * @return 是否成功
     */
    boolean removeByIds(List<Long> roleIds);

    /**
     * 获取所有未删除状态为正常的角色(排除超级管理员)
     *
     * @return 角色list
     */
    List<SysRole> getAllRole();
}
