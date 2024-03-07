package com.you.system.domain.vo;

import com.you.system.domain.model.SysMenu;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MenuTree {

    private SysMenu menu;

    /**
     * 父菜单名称
     */
    private String parentName;

    /**
     * 子菜单
     */
    private List<MenuTree> children = new ArrayList<>();
}
