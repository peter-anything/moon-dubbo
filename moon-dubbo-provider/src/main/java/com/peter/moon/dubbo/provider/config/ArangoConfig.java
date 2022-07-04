package com.peter.moon.dubbo.provider.config;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import com.arangodb.DbName;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArangoConfig {
    @Bean
    public ArangoDB arango() {
        return new ArangoDB.Builder()
                .host("192.168.1.102", 18529)
                .user("root").password("datagrand").build();
    }

    @Bean
    public ArangoDatabase database() {
        return arango().db(DbName.of("kg_graph"));
    }
}
