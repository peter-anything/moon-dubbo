package com.peter.moon.dubbo.consumer.config;

import com.peter.moon.dubbo.common.dto.D2RMessage;
import com.peter.moon.dubbo.common.serializer.hessian.HessianSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Slf4j
@Configuration
public class RocketMQTransactionProducerConfig {
    private String producerGroup = "trac_producer_group";

    TransactionMQProducer producer = null;

    @Value("${rocketmq.producer.groupName}")
    private String             groupName;

    @Value("${rocketmq.producer.namesrvAddr}")
    private String             namesrvAddr;

    @Value("${rocketmq.producer.maxMessageSize}")
    private Integer            maxMessageSize;

    @Value("${rocketmq.producer.sendMsgTimeout}")
    private Integer            sendMsgTimeout;

    @Value("${rocketmq.producer.retryTimesWhenSendFailed}")
    private Integer            retryTimesWhenSendFailed;

    TransactionListener transactionListener = new TransactionListenerImpl();


    private ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100,
            TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory()
    {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("client-transaction-msg-check-thread");
            return thread;
        }
    });

    @Bean
    public TransactionMQProducer getTransactionProducer() {

        TransactionMQProducer producer = new TransactionMQProducer(producerGroup);

        //指定NameServer地址，多个地址以 ; 隔开
        //如 producer.setNamesrvAddr("192.168.100.141:9876;192.168.100.142:9876;192.168.100.149:9876");

        producer.setNamesrvAddr(namesrvAddr);
        producer.setExecutorService(executorService);
        producer.setTransactionListener(transactionListener);
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }

        return producer;
    }
}

class TransactionListenerImpl implements TransactionListener {

    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object arg) {
        System.out.println("====executeLocalTransaction=======");
//        String body = new String(message.getBody());
        D2RMessage msg = (D2RMessage) new HessianSerializer().deserialize(message.getBody(), D2RMessage.class);
        String key = message.getKeys();
        String transactionId = message.getTransactionId();
        System.out.println("transactionId="+transactionId+", key="+key+", body="+msg);
        // 执行本地事务begin TODO

        // 执行本地事务end TODO

        int status = Integer.parseInt(arg.toString());

        //二次确认消息，然后消费者可以消费
        if(status == 1){
            return LocalTransactionState.COMMIT_MESSAGE;
        }

        //回滚消息，broker端会删除半消息
        if(status == 2){
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }

        //broker端会进行回查消息，再或者什么都不响应
        if(status == 3){
            return LocalTransactionState.UNKNOW;
        }
        return null;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        System.out.println("====checkLocalTransaction=======");
        String body = new String(messageExt.getBody());
        String key = messageExt.getKeys();
        String transactionId = messageExt.getTransactionId();
        System.out.println("transactionId="+transactionId+", key="+key+", body="+body);

        //要么commit 要么rollback

        //可以根据key去检查本地事务消息是否完成

        return LocalTransactionState.COMMIT_MESSAGE;
    }
}