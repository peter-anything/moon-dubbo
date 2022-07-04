package com.peter.moon.dubbo.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class D2RMessage<T> implements Serializable {
    //    records = None
    //    task_id = None
    //    schema = None
    //    which_graph = None
    //    message_id = None
    private String messageId;
    private Long taskId;
    private List<T> records;
}
