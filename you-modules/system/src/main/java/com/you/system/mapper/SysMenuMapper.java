package com.you.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.you.system.model.SysMenu;
import com.you.system.qo.MenuQo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据用户id获取菜单数据(目录、菜单)
     *
     * @param userId 用户id，传null时查询所有
     * @return 菜单list
     */
    List<SysMenu> selectMenuTypeMCByUserId(Long userId);

    List<SysMenu> selectMenuByUserId(Long userId);

    List<SysMenu> selectMenuList(@Param("qo") MenuQo qo);
}
