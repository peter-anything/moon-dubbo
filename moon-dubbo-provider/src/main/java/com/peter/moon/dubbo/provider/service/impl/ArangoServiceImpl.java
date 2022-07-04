package com.peter.moon.dubbo.provider.service.impl;

import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;
import com.arangodb.model.DocumentCreateOptions;
import com.arangodb.model.OverwriteMode;
import com.peter.moon.dubbo.common.dto.VertexDTO;
import com.peter.moon.dubbo.provider.service.ArangoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ArangoServiceImpl implements ArangoService {
    @Autowired
    private ArangoDatabase arangoDatabase;

    @Override
    public void bulkInsertVertexes(String collectionName, List<VertexDTO> vertexes) throws IOException {
        DocumentCreateOptions docCreateOptions = new DocumentCreateOptions();
        docCreateOptions.returnNew(false);
        docCreateOptions.overwriteMode(OverwriteMode.update);

        List<BaseDocument> documents = new ArrayList<>();
        for(VertexDTO vertexDTO: vertexes) {
            BaseDocument document = new BaseDocument();
            document.setKey(vertexDTO.getKey());
            document.addAttribute("code", vertexDTO.getCode());
            document.addAttribute("name", vertexDTO.getName());
            document.addAttribute("graphlabel", vertexDTO.getGraphlabel());
            for(Map.Entry<String, String> entry: vertexDTO.getProperties().entrySet()) {
                document.addAttribute(entry.getKey(), entry.getValue());
            }
            documents.add(document);
        }
        arangoDatabase.collection(collectionName).insertDocuments(documents);
    }
}
