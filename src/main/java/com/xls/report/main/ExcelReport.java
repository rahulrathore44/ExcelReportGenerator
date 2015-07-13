/**
 * 
 */
package com.xls.report.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.SAXException;

import com.xls.report.config.Configuration;
import com.xls.report.config.ExcelConfiguration;
import com.xls.report.utility.FileName;
import com.xls.report.utility.ReportData;



/**
 * @author - rahul.rathore
 * @date - 21-May-2015
 * @project - ExcelReportGenerator
 * @package - com.java.main
 * @file name - ExcelReport.java
 */
public class ExcelReport {

	private static FileOutputStream _reportFile;
	private static XSSFWorkbook _book;
		
	public static void generateReport(String xmlFile) throws SAXException, IOException, ParserConfigurationException {
		HashMap<String, Map<String, ArrayList<String>>> data = (HashMap<String, Map<String, ArrayList<String>>>) ReportData.getTestMethodDetail(xmlFile);
		_book = ReportData.createExcelFile(data);
		String fileName = FileName.getFileName();
		_reportFile = new FileOutputStream(new File(fileName));
		_book.write(_reportFile);
		_reportFile.close();
		System.out.println("Report File : " + fileName);
	}
	
	public static void CreateOrUpdateReport(String srcExcel,String srcXml) throws FileNotFoundException {
		if(!ExcelConfiguration.isFilePresent(srcExcel,srcXml))
			throw new FileNotFoundException();
		
		
		
	}
	
}
