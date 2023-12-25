package com.you.common.model;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlInjectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.you.common.utils.StrUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PageModel<T> extends Page<T> {

    /**
     * 防止泄露数据库字段
     */
    private List<OrderItem> orderList;

    public void setOrderList(List<OrderItem> orderList) {
        this.orderList = orderList;
        if (!orderList.isEmpty()) {
            List<OrderItem> orders = new ArrayList<>();
            orderList.forEach(item -> {
                // 检查是否有sql注入风险，如果有风险则剔除排序字段
                if (!SqlInjectionUtils.check(item.getColumn())) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setColumn(StrUtil.convertCamelToUnderscore(item.getColumn()));
                    orderItem.setAsc(item.isAsc());
                    orders.add(orderItem);
                }
            });
            this.orders = orders;
        }
    }
}
