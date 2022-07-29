package com.peter.moon.dubbo.demo.consumer;

import com.lmax.disruptor.EventHandler;
import com.peter.moon.dubbo.demo.event.SeckillEvent;

public class SeckillEventConsumer implements EventHandler<SeckillEvent> {
    @Override
    public void onEvent(SeckillEvent event, long sequence, boolean endOfBatch) throws Exception {
        long secKillId = event.getSeckillId();
        long userId = event.getUserId();
        System.out.println(String.format("secKillId: %d, userId: %d", secKillId, userId));
    }
}
