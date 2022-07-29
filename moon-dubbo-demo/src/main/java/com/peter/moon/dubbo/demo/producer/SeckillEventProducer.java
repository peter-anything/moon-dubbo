package com.peter.moon.dubbo.demo.producer;

import com.lmax.disruptor.EventTranslatorVararg;
import com.lmax.disruptor.RingBuffer;
import com.peter.moon.dubbo.demo.event.SeckillEvent;

public class SeckillEventProducer {
    private final static EventTranslatorVararg<SeckillEvent> translator = new EventTranslatorVararg<SeckillEvent>() {
        @Override
        public void translateTo(SeckillEvent event, long sequence, Object... args) {
            event.setSeckillId((Long) args[0]);
            event.setUserId((Long) args[1]);
        }
    };

    private final RingBuffer<SeckillEvent> ringBuffer;


    public SeckillEventProducer(RingBuffer<SeckillEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void seckill(long seckillId, long userId) {
        this.ringBuffer.publishEvent(translator, seckillId, userId);
    }
}
