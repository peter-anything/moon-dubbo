package com.peter.moon.dubbo.provider.service;

import com.peter.moon.dubbo.common.dto.VertexDTO;

import java.io.IOException;
import java.util.List;

public interface ESService {
    void bulkInsertVertexes(String indexName, List<VertexDTO> vertexes) throws IOException;
}
