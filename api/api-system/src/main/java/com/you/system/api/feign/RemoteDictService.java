package com.you.system.api.feign;

import com.you.common.core.constant.ServiceNameConstants;
import com.you.common.core.domain.R;
import com.you.system.api.domain.model.SysDictData;
import com.you.system.api.failback.RemoteDictFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(contextId = "remoteDictService", value = ServiceNameConstants.SYSTEM_SERVICE,
        fallbackFactory = RemoteDictFallbackFactory.class)
public interface RemoteDictService {

    @GetMapping("/type/{dictType}")
    R<List<SysDictData>> getDictDataByType(@PathVariable("dictType") String dictType);
}
