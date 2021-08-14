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

    //TODO Не использовать глобальные объекты, удалить
    private XSSFRow row;
    private XSSFCell cell;

    @Override
    public byte[] buildReport(List<Map<String, List<String>>> data) throws IOException {
        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet();

        row = sheet.createRow(0);

        int cellIndex = 0;
        int rowIndex = 1;
        int maxSizeData = getMaxSize(data);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
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

                if (isIndexInCollection(value, i)) {
                    cell.setCellValue(
                            new ArrayList<>(value.values()).get(0).get(i)
                    );
                } else {
                    cell.setCellValue("");
                }
                ++value_id;
            }
            ++rowIndex;
        }
        book.write(baos);
        return baos.toByteArray();
    }

    private int getMaxSize(List<Map<String, List<String>>> data) {
        //todo упростить
        int max = 0;
        for (Map<String, List<String>> v : data) {
            for (Map.Entry<String, List<String>> d : v.entrySet()) {
                if ((d.getValue().size() - 1 > max)) {
                    max = d.getValue().size() - 1;
                }
            }
        }
        return max;
    }

    private boolean isIndexInCollection(Map<String, List<String>> data, int index) {
        for (Map.Entry<String, List<String>> d : data.entrySet()) {
            return d.getValue().size() - 1 >= index;
        }
        return false;
    }
}
