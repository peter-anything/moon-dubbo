package com.peter.moon.dubbo.consumer.handler;

import com.peter.moon.dubbo.common.dto.D2RMessage;
import com.peter.moon.dubbo.common.serializer.hessian.HessianSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class TransactionMessageListenerHandler implements MessageListenerConcurrently {
    private static String TOPIC = "TransactionDemoTopic";

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                    ConsumeConcurrentlyContext context) {
        if (CollectionUtils.isEmpty(msgs)) {
            log.info("receive blank msgs...");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        MessageExt messageExt = msgs.get(0);
        D2RMessage msg = (D2RMessage) new HessianSerializer().deserialize(messageExt.getBody(), D2RMessage.class);
        if (messageExt.getTopic().equals(TOPIC)) {
            // mock 消费逻辑
            mockConsume(msg);
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    private void mockConsume(D2RMessage msg){
        System.out.println(String.format("receive msg1: {}.", msg));
        log.info("receive msg1: {}.", msg);
    }
}