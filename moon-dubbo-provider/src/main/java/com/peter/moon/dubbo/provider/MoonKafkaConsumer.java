package com.peter.moon.dubbo.provider;

import com.peter.moon.dubbo.common.dto.D2RMessage;
import com.peter.moon.dubbo.common.dto.VertexDTO;
import com.peter.moon.dubbo.common.serializer.hessian.HessianSerializer;
import com.peter.moon.dubbo.provider.service.ArangoService;
import com.peter.moon.dubbo.provider.service.ESService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class MoonKafkaConsumer {
    @Autowired
    ESService esService;

    @Autowired
    ArangoService arangoService;

    @KafkaListener(topics = {"VERTEXES_QUEUE"}, concurrency = "8")
    public void listen(ConsumerRecord<?, byte[]> record) throws IOException {
        String graph = "rentest_1000w";

        System.out.println("partition: " + record.partition());
        byte[] recordValueBytes = record.value();
        D2RMessage<VertexDTO> d2RMessage = (D2RMessage<VertexDTO>) new HessianSerializer().deserialize(recordValueBytes, D2RMessage.class);
        arangoService.bulkInsertVertexes(graph + "_vertexes", d2RMessage.getRecords());
        esService.bulkInsertVertexes(graph, d2RMessage.getRecords());
    }
}
