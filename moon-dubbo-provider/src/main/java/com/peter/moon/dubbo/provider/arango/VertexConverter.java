package com.peter.moon.dubbo.provider.arango;

import com.arangodb.velocypack.VPackBuilder;
import com.arangodb.velocypack.VPackSlice;
import com.peter.moon.dubbo.common.dto.VertexDTO;
import org.springframework.core.convert.converter.Converter;

import java.util.Map;

public class VertexConverter implements Converter<VertexDTO, VPackSlice> {
    @Override
    public VPackSlice convert(VertexDTO vertexDTO) {
        VPackBuilder builder = new VPackBuilder();
        builder.add("code", vertexDTO.getCode());
        builder.add("name", vertexDTO.getName());
        builder.add("graphlabel", vertexDTO.getGraphlabel());
        for(Map.Entry<String, String> entry: vertexDTO.getProperties().entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        return builder.slice();
    }
}
