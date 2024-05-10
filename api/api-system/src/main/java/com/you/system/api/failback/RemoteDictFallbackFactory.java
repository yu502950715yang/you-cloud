package com.you.system.api.failback;

import com.you.common.core.domain.R;
import com.you.system.api.domain.model.SysDictData;
import com.you.system.api.feign.RemoteDictService;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.List;

public class RemoteDictFallbackFactory implements FallbackFactory<RemoteDictService> {
    @Override
    public RemoteDictService create(Throwable cause) {
        return new RemoteDictService() {
            @Override
            public R<List<SysDictData>> getDictDataByType(String dictType) {
                return R.fail("获取数据字典失败：" + cause.getMessage());
            }
        };
    }
}
