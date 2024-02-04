package com.you.common.mq.producer;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class ProducerMessage {

    private final StreamBridge streamBridge;

    public ProducerMessage(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    /**
     * 发送消息
     *
     * @param topicName 主题名
     * @param data      数据
     */
    public void send(String topicName, Object data) {
        // 这个地方也可以使用实体类啥的，都一样。
        streamBridge.send(topicName, data);
    }
}
