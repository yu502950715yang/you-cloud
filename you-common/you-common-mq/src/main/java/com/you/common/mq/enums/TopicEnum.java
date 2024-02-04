package com.you.common.mq.enums;

import lombok.Getter;

@Getter
public enum TopicEnum {

    OPER_LOG("topic_operLog", "操作日志");

    private final String topicName;
    private final String desc;

    TopicEnum(String topicName, String desc) {
        this.topicName = topicName;
        this.desc = desc;
    }
}
