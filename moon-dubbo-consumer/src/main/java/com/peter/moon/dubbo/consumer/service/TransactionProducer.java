package com.peter.moon.dubbo.consumer.service;

import com.peter.moon.dubbo.common.dto.D2RMessage;

public interface TransactionProducer {
    void send(D2RMessage msg);
}
