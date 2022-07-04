package com.peter.moon.dubbo.common.csv;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;

public class CSVRow implements Row {
    private static final Logger logger = LoggerFactory.getLogger(CSVRow.class);

    private CSVReader csvReader;
    private int columnLen = 0;
    String[] nextLine;

    public CSVRow(CSVReader csvReader) throws SQLException {
        this.csvReader = csvReader;
    }

    public boolean hasNext() {
        try {
            nextLine = csvReader.readNext();
            if (nextLine == null) {
                csvReader.close();
            }
            return nextLine != null;
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
                return nextLine;
            }
        };
    }

    @Override
    public Iterator<RowData> iterator() {
        return this;
    }
}
