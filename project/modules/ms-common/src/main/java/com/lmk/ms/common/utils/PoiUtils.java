package com.lmk.ms.common.utils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * POI工具类
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/08/11
 */
@Slf4j
public class PoiUtils {

    /** 数值正则匹配 */
    private static Pattern NUMBER_PATTERN = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+");

    /**
     * 打开文档
     * @param path
     * @return
     */
    public static XSSFWorkbook open(String path){
        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(new File(path));
        } catch (IOException | InvalidFormatException e) {
            log.error(e.getMessage());
        }
        return wb;
    }

    /**
     * 关闭文档
     * @param wb
     * @return
     */
    public static void close(XSSFWorkbook wb){
        if(wb != null){
            try {
                wb.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    /**
     * 获取日期值
     * @param row       行
     * @param column    列索引
     * @return
     */
    public static Date getDate(XSSFRow row, int column){
        Date date = null;
        try {
            XSSFCell cell = row.getCell(column);
            date = cell.getDateCellValue();
        } catch (Exception e) {
            // e.printStackTrace();
            log.debug(e.getMessage());
        }
        return date;
    }

    /**
     * 获取文本值
     * @param row       行
     * @param column    列索引
     * @return
     */
    public static String getString(XSSFRow row, int column){
        String text = null;
        try {
            XSSFCell cell = row.getCell(column);
            switch (cell.getCellType()){
                case STRING:
                    text = cell.getStringCellValue();
                    break;
                case BOOLEAN:
                    text = String.valueOf(cell.getBooleanCellValue());
                    break;
                case NUMERIC:
                    text = String.valueOf(cell.getNumericCellValue());
            }
        } catch (Exception e) {
            // e.printStackTrace();
            log.debug(e.getMessage());
        }
        return text;
    }

    /**
     * 获取文本值
     * @param row       行
     * @param column    列索引
     * @param evaluator 计算器
     * @return
     */
    public static String getString(XSSFRow row, int column, FormulaEvaluator evaluator){
        String text = null;
        try {
            XSSFCell cell = row.getCell(column);
            switch (cell.getCellType()){
                case STRING:
                    text = cell.getStringCellValue();
                    break;
                case BOOLEAN:
                    text = String.valueOf(cell.getBooleanCellValue());
                    break;
                case NUMERIC:
                    text = String.valueOf(cell.getNumericCellValue());
                case FORMULA:
                    text = String.valueOf(evaluator.evaluate(cell));
            }
        } catch (Exception e) {
            // e.printStackTrace();
            log.debug(e.getMessage());
        }
        return text;
    }

    /**
     * 获取整型值
     * @param row       行
     * @param column    列索引
     * @return
     */
    public static Integer getInteger(XSSFRow row, int column){
        Integer value = null;
        try {
            XSSFCell cell = row.getCell(column);
            switch (cell.getCellType()){
                case STRING:
                    String str = cell.getStringCellValue();
                    if(str != null && NUMBER_PATTERN.matcher(str).matches()){
                        value = Integer.valueOf(str);
                    }
                    break;
                case NUMERIC:
                    value = (int) cell.getNumericCellValue();
            }
        } catch (Exception e) {
            // e.printStackTrace();
            log.debug(e.getMessage());
        }
        return value;
    }

    /**
     * 获取浮点值，默认保留2位小数
     * @param row
     * @param column
     * @return
     */
    public static Double getDouble(XSSFRow row, int column){
        return getDouble(row, column, 2);
    }

    /**
     * 获取浮点值
     * @param row       行
     * @param column    列索引
     * @param scale     小数位精度
     * @return
     */
    public static Double getDouble(XSSFRow row, int column, int scale){
        Double value = null;
        try {
            XSSFCell cell = row.getCell(column);
            switch (cell.getCellType()){
                case STRING:
                    String str = cell.getStringCellValue();
                    if(str != null && NUMBER_PATTERN.matcher(str).matches()){
                        value = Double.valueOf(str);
                    }
                    break;
                case NUMERIC:
                    value = cell.getNumericCellValue();
            }

            if(value != null){
                BigDecimal bd = new BigDecimal(value);
                bd = bd.setScale(scale, RoundingMode.HALF_UP);
                double d = Double.parseDouble(bd.toPlainString()); // 防止获取到科学计数法的小数
                value = d == 0.0 ? null : d;
            }
        } catch (Exception e) {
            // e.printStackTrace();
            log.debug(e.getMessage());
        }
        return value;
    }
}
