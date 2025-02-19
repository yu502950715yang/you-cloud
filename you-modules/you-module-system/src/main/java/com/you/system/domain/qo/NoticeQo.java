package com.you.system.domain.qo;

import com.you.common.core.domain.BaseQo;
import com.you.system.domain.model.SysNotice;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NoticeQo extends BaseQo<SysNotice> {

    /**
     * 通知标题
     */
    private String noticeTitle;

    /**
     * 通知类型
     */
    private String noticeType;

    /**
     * 创建者
     */
    private String createBy;
}
