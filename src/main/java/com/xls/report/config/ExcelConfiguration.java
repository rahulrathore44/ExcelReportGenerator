/**
 * 
 */
package com.xls.report.config;

import java.io.File;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author - rahul.rathore
 * @date - 12-Jul-2015
 * @project - ExcelReportGenerator
 * @package - com.xls.report.config
 * @file name - ExcelConfiguration.java
 */
public class ExcelConfiguration {
	
	public static XSSFWorkbook getBookObject() {
		return new XSSFWorkbook();
	}
	
	public static XSSFRow CreateHeader(XSSFWorkbook book,XSSFSheet sheet,String[] aHeader) {
		XSSFRow row = sheet.createRow(0);
		XSSFCell[] headerCell = new XSSFCell[aHeader.length];
		XSSFCellStyle[] sHeaderStyle = new XSSFCellStyle[aHeader.length];
		
		for(int i = 0; i < headerCell.length; i++){
			headerCell[i] = row.createCell(i);
			
			sHeaderStyle[i] = book.createCellStyle();
			sHeaderStyle[i].setAlignment(HorizontalAlignment.CENTER);
			sHeaderStyle[i].setBorderBottom(BorderStyle.THIN);
			sHeaderStyle[i].setBorderLeft(BorderStyle.THIN);
			sHeaderStyle[i].setBorderRight(BorderStyle.THIN);
			sHeaderStyle[i].setBorderTop(BorderStyle.THIN);
			sHeaderStyle[i].setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			sHeaderStyle[i].setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			sHeaderStyle[i].setLocked(true);
			
			headerCell[i].setCellStyle(sHeaderStyle[i]);
			headerCell[i].setCellValue(aHeader[i]);
		}
		return row;
	}
	
	public static boolean isFilePresent(String ...aFileName) {
		try {
			for(String name : aFileName){
				File file = new File(name);
				if(!file.exists())
					return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
