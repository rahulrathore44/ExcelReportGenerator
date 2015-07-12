/**
 * 
 */
package com.xls.report.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.xls.report.config.Configuration;
import com.xls.report.utility.DocBuilder;
import com.xls.report.utility.FileName;
import com.xls.report.utility.NodeFactory;
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
	private static NodeList _list;
	private static NodeList _classNodeList;
	private static NodeList _methodNodeList;
	private static NodeList _exceptionNodeList;
	private static NodeList _exceptionTraceNodeList;
	private static XSSFCellStyle _failCelStyle;
	private static XSSFCellStyle _passCelStyle;
	private static String _classNodeName;
	private static String _testMethodName;
	private static String _testMethodStatus;
	private static String _expMessage = "";
	private static String _exceptionTrace = "";
	private static XSSFSheet _sheet;
	private static XSSFRow _row;
	private static XSSFCell _cellName;
	private static XSSFCell _cellStatus;
	private static XSSFCell _expCell;
	private static XSSFCell _exceptionTraceCell;

	private static void CreateHeader(XSSFWorkbook book,XSSFSheet sheet,String[] aHeader) {
		_row = sheet.createRow(0);
		XSSFCell[] headerCell = new XSSFCell[aHeader.length];
		XSSFCellStyle[] sHeaderStyle = new XSSFCellStyle[aHeader.length];
		
		for(int i = 0; i < headerCell.length; i++){
			headerCell[i] = _row.createCell(i);
			
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
	}
	
	public static void generateReport(String xmlFile) throws SAXException, IOException, ParserConfigurationException {
		HashMap<String, Map<String, ArrayList<String>>> data = (HashMap<String, Map<String, ArrayList<String>>>) ReportData.getTestMethodDetail(xmlFile);
		String fileName = FileName.getFileName();
		
		_reportFile = new FileOutputStream(new File(fileName));
		_book = new XSSFWorkbook();

		_failCelStyle = _book.createCellStyle();
		_passCelStyle = _book.createCellStyle();
		
		for(String sheetNameKey : data.keySet()){
			_sheet = _book.createSheet(sheetNameKey);
			CreateHeader(_book,_sheet,Configuration.aHeader);
			HashMap<String, ArrayList<String>> testMethods = (HashMap<String, ArrayList<String>>) data.get(sheetNameKey);
			int l = 1;
			
			for(String testMethod : testMethods.keySet()){
				_passCelStyle.setFillForegroundColor(HSSFColor.BRIGHT_GREEN.index);
				_failCelStyle.setFillForegroundColor(HSSFColor.RED.index);

				_passCelStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				_failCelStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

				_row = _sheet.createRow(l++);
				_cellName = _row.createCell(0);
				_cellName.setCellValue(testMethod);
				ArrayList<String> testData = testMethods.get(testMethod);
				_cellStatus = _row.createCell(1);
				
				if ("fail".equalsIgnoreCase(testData.get(Configuration.aTestStatusIndex))) {
					_cellStatus.setCellStyle(_failCelStyle);
					_cellStatus.setCellValue(testData.get(Configuration.aTestStatusIndex));
					_expCell = _row.createCell(2);
					_expCell.setCellValue(testData.get(Configuration.aExceptionMsgIndex));
					_exceptionTraceCell = _row.createCell(3);
					_exceptionTraceCell.setCellValue(testData.get(Configuration.aExceptionStackTrace).trim());
				} else {
					_cellStatus.setCellStyle(_passCelStyle);
					_cellStatus.setCellValue(testData.get(Configuration.aTestStatusIndex));
				}
				
				/*_cellStatus.setCellValue(testData.get(Configuration.aTestStatusIndex));

				if ("fail".equalsIgnoreCase(testData.get(Configuration.aTestStatusIndex))) {
					_expCell = _row.createCell(2);
					_expCell.setCellValue(testData.get(Configuration.aExceptionMsgIndex));
					_exceptionTraceCell = _row.createCell(3);
					_exceptionTraceCell.setCellValue(testData.get(Configuration.aExceptionStackTrace).trim());
				}*/
			}
			
		}
		_book.write(_reportFile);
		_reportFile.close();
		System.out.println("Report File : " + fileName);
		
	}
	
	public static void adsasdgenerateReport(String xmlFile) throws SAXException,
			IOException, ParserConfigurationException {
		Document doc = DocBuilder.getDocument(xmlFile);
		doc.getDocumentElement().normalize();
		String fileName = FileName.getFileName();
		_reportFile = new FileOutputStream(new File(fileName));
		_book = new XSSFWorkbook();

		_failCelStyle = _book.createCellStyle();
		_passCelStyle = _book.createCellStyle();
		_list = NodeFactory.getNodeList(doc,"test"); // for getting test nodes

		/* Outer loop for scanning all the <test> */
		for (int i = 0; i < _list.getLength(); i++) {
			int l = 1;
			
			/* for getting the class nodes */
			_classNodeList = NodeFactory.getEleListByTagName(_list.item(i), "class");
			_sheet = _book.createSheet(NodeFactory.getNameAttribute(_list.item(i), "name"));
			CreateHeader(_book,_sheet,Configuration.aHeader);
			/* inner loop for scanning all the <class> */
			for (int j = 0; j < _classNodeList.getLength(); j++) {
				_classNodeName = NodeFactory.getNameAttribute(_classNodeList.item(j), "name");
				
				/* for getting the list of test method nodes */
				_methodNodeList = NodeFactory.getEleListByTagName(_classNodeList.item(j), "test-method"); 

				/* inner loop for scanning all the <test-method> */
				for (int k = 0; k < _methodNodeList.getLength(); k++) {
					_testMethodName = NodeFactory.getNameAttribute(_methodNodeList.item(k), "name");
					_testMethodStatus = NodeFactory.getNameAttribute(_methodNodeList.item(k),"status");
					
					if(NodeFactory.isDataProviderPresent(_methodNodeList.item(k), "data-provider")){
						String name = "";
						for(int m = 0; m < NodeFactory.getEleListByTagName(_methodNodeList.item(k), "value").getLength(); m++){
							name = name + "," + NodeFactory.getEleListByTagName(_methodNodeList.item(k), "value").item(m).getTextContent().trim();
						}
						_testMethodName = _testMethodName + "(" + name.substring(1, name.length()) + ")";
					}
					_passCelStyle.setFillForegroundColor(HSSFColor.BRIGHT_GREEN.index);
					_failCelStyle.setFillForegroundColor(HSSFColor.RED.index);

					_passCelStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
					_failCelStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

					_row = _sheet.createRow(l++);
					_cellName = _row.createCell(0);
					_cellName.setCellValue(_classNodeName + "." + _testMethodName);
					_cellStatus = _row.createCell(1);

					if ("fail".equalsIgnoreCase(_testMethodStatus)) {
						_cellStatus.setCellStyle(_failCelStyle);
					} else {
						_cellStatus.setCellStyle(_passCelStyle);
					}

					_cellStatus.setCellValue(_testMethodStatus);

					if ("fail".equalsIgnoreCase(_testMethodStatus)) {
						_exceptionNodeList = NodeFactory.getEleListByTagName(_methodNodeList.item(k), "exception");
						_exceptionTraceNodeList = NodeFactory.getEleListByTagName(_methodNodeList.item(k), "message");
						_exceptionTrace = _exceptionTraceNodeList.item(0).getTextContent();
						_expMessage = NodeFactory.getNameAttribute(_exceptionNodeList.item(0), "class");
						_expCell = _row.createCell(2);
						_expCell.setCellValue(_expMessage);
						_exceptionTraceCell = _row.createCell(3);
						_exceptionTraceCell.setCellValue(_exceptionTrace.trim());
					}

				}
			}
		}
		_book.write(_reportFile);
		_reportFile.close();
		System.out.println("Report File : " + fileName);
	}
	
}
