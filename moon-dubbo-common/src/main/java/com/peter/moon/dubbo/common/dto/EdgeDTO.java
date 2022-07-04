package com.peter.moon.dubbo.common.dto;

import com.arangodb.entity.Key;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class EdgeDTO implements Serializable {
    @Key
    private String key;
    private String source;
    private String target;
    private String graphlabel;
    private Map<String, String> properties = new HashMap<>();
}
