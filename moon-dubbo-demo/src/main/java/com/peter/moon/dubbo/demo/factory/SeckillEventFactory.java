package com.peter.moon.dubbo.demo.factory;

import com.lmax.disruptor.EventFactory;
import com.peter.moon.dubbo.demo.event.SeckillEvent;

public class SeckillEventFactory implements EventFactory<SeckillEvent> {
    @Override
    public SeckillEvent newInstance() {
        return new SeckillEvent();
    }
}
