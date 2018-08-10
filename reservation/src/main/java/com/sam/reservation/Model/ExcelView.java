package com.sam.reservation.Model;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


public class ExcelView extends AbstractXlsxView {

    private Logger log = LoggerFactory.getLogger(getClass());


    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        // change the file name
        log.info(httpServletRequest.getContentType());
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=\"report.xlsx\"");
        httpServletResponse.setHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        @SuppressWarnings("unchecked")
        Map<Dish, Integer> dishesOccurences = (Map<Dish, Integer>) model.get("occorrenzePiatti");

        // create excel xls sheet
        Sheet sheet = workbook.createSheet("Report piatti serviti");
        sheet.setDefaultColumnWidth(30);

        // create style for header cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font.setBold(true);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);

        log.info("excel creation");

        // create header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Nome Piatto");
        header.getCell(0).setCellStyle(style);
        header.createCell(1).setCellValue("Quantità");
        header.getCell(1).setCellStyle(style);


        int rowCount = 1;

        for (Map.Entry<Dish, Integer> entry : dishesOccurences.entrySet()) {

            Row userRow = sheet.createRow(rowCount++);
            userRow.createCell(0).setCellValue(entry.getKey().getName());
            userRow.createCell(1).setCellValue(entry.getValue());
        }

    }
}
