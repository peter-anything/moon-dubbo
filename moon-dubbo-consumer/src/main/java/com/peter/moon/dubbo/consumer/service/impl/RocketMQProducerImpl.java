package com.peter.moon.dubbo.consumer.service.impl;

import com.peter.moon.dubbo.common.dto.D2RMessage;
import com.peter.moon.dubbo.common.serializer.hessian.HessianSerializer;
import com.peter.moon.dubbo.consumer.service.MoonKafkaProducer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RocketMQProducerImpl implements MoonKafkaProducer {
    private static String TOPIC = "DemoTopic";
    private static String TAGS = "glmapperTags";

    @Autowired
    private DefaultMQProducer defaultMQProducer;
    @Override
    public void send(D2RMessage msg) {
        Message sendMsg = new Message(TOPIC, TAGS, new HessianSerializer().serialize(msg));
        try {
            SendCallback sendCallback = new D2rCallback(System.currentTimeMillis(), msg.getMessageId(), msg);
            defaultMQProducer.send(sendMsg, sendCallback);
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }


    class D2rCallback implements SendCallback {
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
        public void onSuccess(SendResult result) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            System.out.println(result);
        }

        @Override
        public void onException(Throwable e) {

        }
    }
}
