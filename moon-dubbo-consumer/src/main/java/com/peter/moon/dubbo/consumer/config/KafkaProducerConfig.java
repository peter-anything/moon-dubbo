package com.peter.moon.dubbo.consumer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaProducerConfig {
    @Bean
    public NewTopic vertexesTp() {
        return new NewTopic("VERTEXES_QUEUE", 8, (short) 0);
    }
}
