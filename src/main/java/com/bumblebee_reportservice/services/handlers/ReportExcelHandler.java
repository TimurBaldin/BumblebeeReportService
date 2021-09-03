package com.bumblebee_reportservice.services.handlers;

import com.bumblebee_reportservice.services.dto.TestDataDto;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component("reportExcelHandler")
public class ReportExcelHandler implements ReportHandler<List<TestDataDto>, byte[]> {

    @Override
    public byte[] buildReport(List<TestDataDto> data) throws IOException {
        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet();

        XSSFRow row = sheet.createRow(0);
        XSSFCell cell;

        int cellIndex = 0;
        int rowIndex = 1;
        int maxSizeData = getMaxSize(data);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //пишем заголовки документа
        for (TestDataDto dto : data) {
            cell = row.createCell(cellIndex, CellType.STRING);
            cell.setCellValue(dto.getGeneratorName());
            ++cellIndex;
        }

        //пишем тестовые данные документа
        for (int i = 0; i <= maxSizeData; i++) {
            row = sheet.createRow(rowIndex);
            int value_id = 0;

            for (TestDataDto dto : data) {
                cell = row.createCell(value_id);
                cell.setCellType(CellType.STRING);

                if (isIndexInCollection(dto.getData(), i)) {
                    cell.setCellValue(dto.getData().get(i));
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

    private int getMaxSize(List<TestDataDto> data) {
        int max = 0;
        for (TestDataDto dto : data) {
            if (dto.getData().size() - 1 > max) {
                max = dto.getData().size() - 1;
            }
        }
        return max;
    }

    private boolean isIndexInCollection(List<String> data, int index) {
        if (data.size() - 1 <= index) {
            return true;
        }
        return false;
    }
}
