package com.peter.moon.dubbo.demo;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.peter.moon.dubbo.demo.consumer.SeckillEventConsumer;
import com.peter.moon.dubbo.demo.event.SeckillEvent;
import com.peter.moon.dubbo.demo.factory.SeckillEventFactory;
import com.peter.moon.dubbo.demo.producer.SeckillEventProducer;

import java.util.concurrent.ThreadFactory;

public class SeckillEventMain {
    public static void producerWithTranslator() {
        SeckillEventFactory factory = new SeckillEventFactory();
        int ringBufferSize = 1024;
        ThreadFactory threadFactory = new ThreadFactory() {
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable);
            }
        };
        Disruptor<SeckillEvent> disruptor = new Disruptor<SeckillEvent>(factory, ringBufferSize, threadFactory);
        disruptor.handleEventsWith(new SeckillEventConsumer());
        disruptor.start();

        RingBuffer<SeckillEvent> ringBuffer = disruptor.getRingBuffer();

        SeckillEventProducer seckillEventProducer = new SeckillEventProducer(ringBuffer);
        for (int i = 0; i < 100; i++) {
            seckillEventProducer.seckill(i, i);
        }

        disruptor.shutdown();
    }

    public static void main(String[] args) {
        producerWithTranslator();
    }
}
