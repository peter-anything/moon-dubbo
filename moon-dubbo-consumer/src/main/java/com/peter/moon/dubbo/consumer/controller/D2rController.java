package com.peter.moon.dubbo.consumer.controller;

import com.peter.moon.dubbo.common.csv.BatchCSVRow;
import com.peter.moon.dubbo.common.csv.RowData;
import com.peter.moon.dubbo.common.dto.D2RMessage;
import com.peter.moon.dubbo.common.dto.VertexDTO;
import com.peter.moon.dubbo.common.util.CsvUtil;
import com.peter.moon.dubbo.consumer.service.MoonKafkaProducer;
import io.reactivex.rxjava3.core.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class D2rController {
    @Autowired
    MoonKafkaProducer moonKafkaProducer;

    private static String TOPIC = "DemoTopic";
    private static String TAGS = "glmapperTags";

    @RequestMapping(value="/d2r/threeImport/{fileName}", method= RequestMethod.POST)
    public String threeImport(@PathVariable String fileName) throws IOException {
        BatchCSVRow batchCSVRow = CsvUtil.readDataFromFile("D:\\new_download\\" + fileName, true);
        Observable.fromIterable(batchCSVRow);

        for (RowData batchRow: batchCSVRow) {
            List<VertexDTO> vertexes = (List<VertexDTO>) batchRow.getData();
            D2RMessage<VertexDTO> d2RMessage = new D2RMessage();
            d2RMessage.setMessageId(vertexes.get(0).getCode());
            d2RMessage.setRecords(vertexes);
            moonKafkaProducer.send(d2RMessage);
        }
        return "success";
    }

    @RequestMapping(value="/d2r/threeImport2/{fileName}", method= RequestMethod.POST)
    public String threeImport2(@PathVariable String fileName) throws IOException {
        BatchCSVRow batchCSVRow = CsvUtil.readDataFromFile("/Users/wangxiaobin/Downloads/" + fileName, true);
        Observable.fromIterable(batchCSVRow);

        for (RowData batchRow: batchCSVRow) {
            List<VertexDTO> vertexes = (List<VertexDTO>) batchRow.getData();
            D2RMessage<VertexDTO> d2RMessage = new D2RMessage();
            d2RMessage.setMessageId(vertexes.get(0).getCode());
            d2RMessage.setRecords(vertexes);

            moonKafkaProducer.send(d2RMessage);
        }
        return "success";
    }
}
