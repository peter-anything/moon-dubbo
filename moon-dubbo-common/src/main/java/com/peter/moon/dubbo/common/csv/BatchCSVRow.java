package com.peter.moon.dubbo.common.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.peter.moon.dubbo.common.dto.EdgeDTO;
import com.peter.moon.dubbo.common.dto.VertexDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BatchCSVRow implements Row {
    private static final Logger logger = LoggerFactory.getLogger(CSVRow.class);

    private CSVReader csvReader;
    private int batchSize = 1000;
    private int columnLen = 0;
    boolean isVertex;
    String[] nextLine;
    List<Object> batchRecords;

    public BatchCSVRow(CSVReader csvReader, boolean isVertex) {
        this.csvReader = csvReader;
        this.isVertex = isVertex;
    }

    public boolean hasNext() {
        try {
            int count = 0;
            batchRecords = new ArrayList<>();
            while (count < batchSize) {
                nextLine = csvReader.readNext();
                if (nextLine == null) break;
                int columnLen = nextLine.length;
                if (nextLine[0].equals("V")) {
                    if (!this.isVertex) continue;
                    count++;
                    VertexDTO vertexDTO = new VertexDTO();
                    vertexDTO.setGraphlabel(nextLine[1]);
                    vertexDTO.setCode(nextLine[2]);
                    vertexDTO.setName(nextLine[3]);
                    for (int idx = 4; idx < columnLen; idx += 2) {
                        if (nextLine[idx].equals("") && nextLine[idx + 1].equals("")) {
                            continue;
                        }
                        vertexDTO.getProperties().put(nextLine[idx], nextLine[idx + 1]);
                    }
                    vertexDTO.setKey(DigestUtils.md5DigestAsHex(vertexDTO.getCode().getBytes()));
                    batchRecords.add(vertexDTO);
                } else {
                    if (this.isVertex) continue;
                    count++;
                    EdgeDTO edgeDTO = new EdgeDTO();
                    edgeDTO.setGraphlabel(nextLine[1]);
                    edgeDTO.setSource(nextLine[2]);
                    edgeDTO.setTarget(nextLine[3]);
                    for (int idx = 4; idx < columnLen; idx += 2) {
                        if (nextLine[idx].equals("") && nextLine[idx + 1].equals("")) {
                            continue;
                        }
                        edgeDTO.getProperties().put(nextLine[idx], nextLine[idx + 1]);
                    }
                    batchRecords.add(edgeDTO);
                }
            }

            return !batchRecords.isEmpty();
        } catch (CsvValidationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public RowData next() {
        return new RowData() {
            @Override
            public Object getData() {
                return batchRecords;
            }
        };
    }

    @Override
    public Iterator<RowData> iterator() {
        return this;
    }
}
