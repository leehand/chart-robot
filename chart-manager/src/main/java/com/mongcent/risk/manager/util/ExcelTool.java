package com.mongcent.risk.manager.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelTool {
    private static Logger logger = LoggerFactory.getLogger(ExcelTool.class);
    private static ExcelTool tool = new ExcelTool();

    public static ExcelTool getInstance() {
        Class var0 = ExcelTool.class;
        synchronized(ExcelTool.class) {
            if (tool == null) {
                tool = new ExcelTool();
            }
        }

        return tool;
    }

    private ExcelTool() {
    }

    public Workbook getExcelWorkbook(String filePath) throws Exception {
        return this.getExcelWorkbook(new File(filePath));
    }

    public Workbook getExcelWorkbook(File file) throws Exception {
        FileInputStream is = new FileInputStream(file);
        Workbook wb = this.getExcelWorkbook((InputStream)is);
        return wb;
    }

    public Workbook getExcelWorkbook(InputStream in) throws InvalidFormatException, IOException {
        Workbook wb = WorkbookFactory.create(in);
        return wb;
    }

    public String getCellContent(Sheet sheet, int rowNum, int colNum) {
        Row row = sheet.getRow(rowNum);
        String contents = "";
        if (row != null && row.getCell(colNum) != null) {
            contents = this.getCellContent(row.getCell(colNum)).trim();
        }

        return contents;
    }

    public String getCellContent(Cell cell) {
        DecimalFormat df = new DecimalFormat("0");
        if (cell == null) {
            return "";
        } else {
            int type = cell.getCellType();
            String value;
            switch(type) {
            case 0:
                value = String.valueOf(df.format(cell.getNumericCellValue())).toString().trim();
                if (value.endsWith(".0")) {
                    value = value.substring(0, value.length() - 2).trim();
                }
                break;
            case 1:
                value = cell.getStringCellValue().toString().trim();
                break;
            case 2:
                try {
                    value = String.valueOf(df.format(cell.getNumericCellValue())).toString().trim();
                    break;
                } catch (Exception var6) {
                    logger.info("处理公式单元格失败！");
                    value = cell.getStringCellValue();
                    return value;
                }
            case 3:
            default:
                value = cell.toString().trim();
                break;
            case 4:
                value = String.valueOf(cell.getBooleanCellValue()).toString().trim();
            }

            return value;
        }
    }

    public boolean isNullRow(Sheet sheet, int rowNum) {
        int rowCount = sheet.getLastRowNum();
        return rowCount < rowNum;
    }

    public int getSheetCount(Workbook wb) {
        return wb.getNumberOfSheets();
    }

    public int getSheetRows(Sheet sheet) {
        return sheet.getPhysicalNumberOfRows();
    }

    public HSSFWorkbook createWorkbook() {
        HSSFWorkbook wb = new HSSFWorkbook();
        return wb;
    }

    public HSSFSheet createSheet(HSSFWorkbook wb, String sheetName) {
        HSSFSheet sheet = wb.createSheet(sheetName);
        return sheet;
    }

    public HSSFWorkbook createHead(HSSFWorkbook wb, HSSFSheet sheet, String[] heads) {
        HSSFRow row = sheet.createRow(0);

        for(int i = 0; i < heads.length; ++i) {
            HSSFCell cell = row.createCell(i);
            cell.setCellType(1);
            cell.setCellValue(new HSSFRichTextString(heads[i]));
            HSSFCellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment((short)2);
            cellStyle.setVerticalAlignment((short)1);
            cellStyle.setWrapText(true);
            HSSFFont font = wb.createFont();
            font.setFontName("宋体");
            font.setFontHeight((short)250);
            cellStyle.setFont(font);
            cell.setCellStyle(cellStyle);
        }

        return wb;
    }

    public HSSFWorkbook createRow(HSSFWorkbook wb, HSSFSheet sheet, String[] params) {
        HSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);

        for(int i = 0; i < params.length; ++i) {
            HSSFCell cell = row.createCell(i);
            cell.setCellType(1);
            cell.setCellValue(new HSSFRichTextString(params[i]));
            HSSFCellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment((short)2);
            cellStyle.setVerticalAlignment((short)1);
            cellStyle.setWrapText(true);
            HSSFFont font = wb.createFont();
            font.setFontHeight((short)250);
            cellStyle.setFont(font);
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(i);
        }

        return wb;
    }

    public void exportExcel(HSSFWorkbook wb, String fileName) {
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(new File(fileName));
            wb.write(fos);
        } catch (FileNotFoundException var15) {
            logger.error("文件未找到,fileName" + fileName, var15);
        } catch (IOException var16) {
            logger.error("导出失败", var16);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException var14) {
            }

        }

    }
}

