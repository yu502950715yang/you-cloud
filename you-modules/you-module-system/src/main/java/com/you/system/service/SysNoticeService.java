package com.you.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.you.system.domain.model.SysNotice;
import com.you.system.domain.qo.NoticeQo;

/**
 * 通知公告表 服务层
 */
public interface SysNoticeService extends IService<SysNotice> {


    /**
     * 通知公告分页查询
     *
     * @param qo 查询条件
     * @return 分页数据
     */
    IPage<SysNotice> listPage(NoticeQo qo);

    /**
     * 新增公告
     *
     * @param notice 内容
     * @return 结果
     */
    boolean saveNotice(SysNotice notice);

    /**
     * 根据ID删除通知公告
     *
     * @param noticeIds ID List
     * @return 结果
     */
    boolean deleteNoticeByIds(Long[] noticeIds);

    /**
     * 编辑公告
     *
     * @param notice 内容
     * @return 结果
     */
    boolean updateNotice(SysNotice notice);
}
