package com.qfleaf.yunbi.common.util;

import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileConverterUtils {

    /**
     * 将MultipartFile(Excel或CSV)转换为CSV字符串
     *
     * @param file 上传的文件
     * @return CSV格式的字符串
     * @throws IOException              文件读取异常
     * @throws IllegalArgumentException 不支持的文件类型
     */
    public static String convertToCsv(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        if (fileName == null) {
            throw new IllegalArgumentException("文件名不能为空");
        }

        if (fileName.toLowerCase().endsWith(".xlsx") || fileName.toLowerCase().endsWith(".xls")) {
            return convertExcelToCsv(file.getInputStream());
        } else if (fileName.toLowerCase().endsWith(".csv")) {
            return convertCsvToStandardFormat(file.getInputStream());
        } else {
            throw new IllegalArgumentException("不支持的文件格式，只支持Excel(.xlsx, .xls)和CSV(.csv)文件");
        }
    }

    /**
     * 将Excel文件转换为CSV格式
     *
     * @param inputStream 文件输入流
     * @return CSV格式的字符串
     * @throws IOException 文件读取异常
     */
    private static String convertExcelToCsv(InputStream inputStream) throws IOException {
        StringBuilder csvData = new StringBuilder();

        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表

            for (Row row : sheet) {
                List<String> rowValues = new ArrayList<>();

                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        case STRING:
                            rowValues.add(cell.getStringCellValue());
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                rowValues.add(cell.getDateCellValue().toString());
                            } else {
                                rowValues.add(String.valueOf(cell.getNumericCellValue()));
                            }
                            break;
                        case BOOLEAN:
                            rowValues.add(String.valueOf(cell.getBooleanCellValue()));
                            break;
                        case FORMULA:
                            rowValues.add(cell.getCellFormula());
                            break;
                        default:
                            rowValues.add("");
                    }
                }

                // 将行数据转换为CSV格式
                csvData.append(String.join(",", rowValues)).append("\n");
            }
        }

        return csvData.toString();
    }

    /**
     * 将CSV文件转换为标准格式(直接读取原始内容)
     *
     * @param inputStream 文件输入流
     * @return CSV格式的字符串
     * @throws IOException 文件读取异常
     */
    private static String convertCsvToStandardFormat(InputStream inputStream) throws IOException {
        StringBuilder csvData = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            csvData.append(line).append("\n");
        }

        return csvData.toString();
    }
}
