/**
 * 
 */
package com.xls.report.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.SAXException;

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
	
		
	/**
	 * @param xmlFile
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException	
	 */
	public static void generateReport(String xmlFile) throws SAXException, IOException, ParserConfigurationException {
		HashMap<String, Map<String, ArrayList<String>>> data = (HashMap<String, Map<String, ArrayList<String>>>) ReportData.getTestMethodDetail(xmlFile);
		_book = ReportData.createExcelFile(data);
		String fileName = FileName.getFileName();
		_reportFile = new FileOutputStream(new File(fileName));
		_book.write(_reportFile);
		_reportFile.close();
		System.out.println("Report File : " + fileName);
	}
	
	/**
	 * @param srcExcel
	 * @param srcXml
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException	
	 */
	public static void CreateOrUpdateReport(String srcExcel,String srcXml) throws SAXException, IOException, ParserConfigurationException {
		
		File createFile = new File(srcExcel);
		if(!createFile.exists()){
			generateReport(srcXml);
			return;
		}
		
		HashMap<String, Map<String, ArrayList<String>>> data = (HashMap<String, Map<String, ArrayList<String>>>) ReportData.getTestMethodDetail(srcXml);
		XSSFWorkbook book = ExcelConfiguration.getBook(srcExcel);
		for(String sheetName : data.keySet()){
			if(ExcelConfiguration.isSheetPresent(book, sheetName)){
				XSSFSheet xlSheet = ExcelConfiguration.getSheet(book, sheetName);
				HashMap<String, ArrayList<String>> sheetMap = (HashMap<String, ArrayList<String>>) data.get(sheetName);
				HashMap<String, ArrayList<String>> newSheetMap = ExcelConfiguration.appendExcelData(xlSheet,sheetMap);
				data.put(sheetName, newSheetMap);
			}else{
				XSSFSheet xlSheet = ExcelConfiguration.getSheet(book, sheetName);
				HashMap<String, ArrayList<String>> sheetMap = ExcelConfiguration.appendExcelDataToMap(xlSheet);
				data.put(sheetName, sheetMap);
			}
		}
		_book = ReportData.createExcelFile(data);
		String fileName = FileName.getFileName();
		_reportFile = new FileOutputStream(new File(fileName));
		_book.write(_reportFile);
		_reportFile.close();
		System.out.println("Report File : " + fileName);
		
	}
	
}
