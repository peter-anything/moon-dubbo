package com.peter.moon.dubbo.provider.arango;

import com.arangodb.velocypack.VPackBuilder;
import com.arangodb.velocypack.VPackSlice;
import com.peter.moon.dubbo.common.dto.EdgeDTO;
import org.springframework.core.convert.converter.Converter;

import java.util.Map;

public class EdgeConverter implements Converter<EdgeDTO, VPackSlice>  {
    @Override
    public VPackSlice convert(EdgeDTO edgeDTO) {
        VPackBuilder builder = new VPackBuilder();
        builder.add("source", edgeDTO.getSource());
        builder.add("target", edgeDTO.getTarget());
        builder.add("graphlabel", edgeDTO.getGraphlabel());
        for(Map.Entry<String, String> entry: edgeDTO.getProperties().entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        return builder.slice();
    }
}
