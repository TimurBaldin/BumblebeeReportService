package com.bumblebee_reportservice.services.handlers;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("reportExcelHandler")
public class ReportExcelHandler implements ReportHandler<List<Map<String, List<String>>>, byte[]> {

    private XSSFRow row;
    private XSSFCell cell;

    @Override
    public byte[] buildReport(List<Map<String, List<String>>> data) {

        final XSSFWorkbook book = new XSSFWorkbook();
        final XSSFSheet sheet = book.createSheet();

        row = sheet.createRow(0);

        int cellIndex = 0;
        int rowIndex = 1;
        int maxSizeData = getMaxSize(data);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            //пишем заголовки документа
            for (Map<String, List<String>> value : data) {
                final int finalRowIndex = cellIndex;
                value.keySet().forEach(
                        key -> {
                            cell = row.createCell(finalRowIndex, CellType.STRING);
                            cell.setCellValue(key);
                        }
                );
                ++cellIndex;
            }

            //пишем тестовые данные документа
            for (int i = 0; i <= maxSizeData; i++) {
                row = sheet.createRow(rowIndex);
                int value_id = 0;

                for (Map<String, List<String>> value : data) {
                    cell = row.createCell(value_id);
                    cell.setCellType(CellType.STRING);

                    if (value.values().size() - 1 >= i) {
                        cell.setCellValue(
                                new ArrayList<>(value.values()).get(0).get(i)
                        );
                    } else {
                        cell.setCellValue("");
                    }
                }
                ++rowIndex;
            }

            book.write(baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int getMaxSize(List<Map<String, List<String>>> data) {
        return data.stream().mapToInt(map -> map.values().size() - 1).max().orElse(0);
    }
}
