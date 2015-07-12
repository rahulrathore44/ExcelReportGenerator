/**
 * 
 */
package com.xls.report.config;

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

}
