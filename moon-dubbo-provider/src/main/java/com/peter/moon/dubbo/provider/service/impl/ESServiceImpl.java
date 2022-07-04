package com.peter.moon.dubbo.provider.service.impl;

import com.peter.moon.dubbo.common.dto.VertexDTO;
import com.peter.moon.dubbo.provider.service.ESService;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ESServiceImpl implements ESService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public void bulkInsertVertexes(String indexName, List<VertexDTO> vertexes) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (VertexDTO vertexDTO: vertexes) {
            bulkRequest.add(new IndexRequest("ren_1000w").id(vertexDTO.getKey()).source(XContentType.JSON, "code", vertexDTO.getCode()));
            Map<String, Object> docMap = new HashMap<>(vertexDTO.getProperties());
            docMap.put("code", vertexDTO.getCode());
            docMap.put("name", vertexDTO.getName());
            docMap.put("graphlabel", vertexDTO.getGraphlabel());
            bulkRequest.add(new UpdateRequest("ren_1000w", vertexDTO.getKey()).doc(docMap, XContentType.JSON).docAsUpsert(true));
        }
        bulkRequest.timeout(TimeValue.timeValueMinutes(2));
        bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
        restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
    }
}
