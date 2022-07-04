package com.peter.moon.dubbo.common.util;

import com.opencsv.CSVReader;
import com.peter.moon.dubbo.common.csv.BatchCSVRow;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CsvUtil {
    public static final String GRAPHLABEL = "graphlabel";
    public static final String CODE = "code";
    public static final String NAME = "name";
    public static final String SOURCE = "source";
    public static final String TARGET = "target";

    public static BatchCSVRow readDataFromFile(String filePath, boolean isVertex) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        InputStreamReader isr = new InputStreamReader(fis,
                StandardCharsets.UTF_8);
        CSVReader reader = new CSVReader(isr);
        return new BatchCSVRow(reader, isVertex);
    }
}
