package com.peter.moon.dubbo.consumer.service.impl;

import com.peter.moon.dubbo.common.dto.D2RMessage;
import com.peter.moon.dubbo.common.serializer.hessian.HessianSerializer;
import com.peter.moon.dubbo.consumer.service.MoonKafkaProducer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaProducerImpl implements MoonKafkaProducer {
    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @Override
    public void send(D2RMessage msg) {

        ListenableFuture<SendResult<String, byte[]>> future =  kafkaTemplate.send("VERTEXES_QUEUE", new HessianSerializer().serialize(msg));
        future.addCallback(new D2rCallback(System.currentTimeMillis(), msg.getMessageId(), msg));
    }


    class D2rCallback implements ListenableFutureCallback<SendResult<String, byte[]>> {
        private final long startTime;
        private final String key;
        private final D2RMessage message;

        //实现的是DemoCallBack的有参构造
        public D2rCallback(long startTime, String key, D2RMessage d2RMessage) {
            //通过this把获取的参数内容传递到外层类中,这有这样当回调发生时onCompletion才可以获取类的参数
            this.startTime = startTime;
            this.key = key;
            this.message = d2RMessage;
        }

        @Override
        public void onFailure(Throwable ex) {

        }

        @Override
        public void onSuccess(SendResult<String, byte[]> result) {
            long elapsedTime = System.currentTimeMillis() - startTime;

            if (result.getRecordMetadata() != null) {
                System.out.println(
                        "message(" + key + ", " + message + ") sent to partition(" + result.getRecordMetadata().partition() +
                                "), " +
                                "offset(" + result.getRecordMetadata().offset() + ") in " + elapsedTime + " ms");
            } else {
            }
        }
    }
}
