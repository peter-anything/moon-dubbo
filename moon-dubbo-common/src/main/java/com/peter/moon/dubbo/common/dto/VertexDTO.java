package com.peter.moon.dubbo.common.dto;

import com.arangodb.entity.Key;
import com.arangodb.springframework.annotation.Document;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Document("ren_1000w_vertexes")
@Data
public class VertexDTO implements Serializable {
    @Key
    private String key;
    /**
     * 类型
     */
    private String graphlabel;

    /**
     * 查询主键
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * properties 不包含name
     */
    private Map<String, String> properties = new HashMap<>();

}
