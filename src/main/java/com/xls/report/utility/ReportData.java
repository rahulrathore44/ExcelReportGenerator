/**
 * 
 */
package com.xls.report.utility;

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
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.xls.report.config.Configuration;
import com.xls.report.config.ExcelConfiguration;



/**
 * @author - rahul.rathore
 * @date - 21-May-2015
 * @project - ExcelReportGenerator
 * @package - com.java.main
 * @file name - ExcelReport.java
 */
public class ReportData {

	private static NodeList _list;
	private static NodeList _classNodeList;
	private static NodeList _methodNodeList;
	private static NodeList _exceptionNodeList;
	private static NodeList _exceptionTraceNodeList;
	private static String _classNodeName;
	private static String _testMethodName;
	private static String _testMethodStatus;
	private static String _expMessage = "";
	private static String _exceptionTrace = "";
	private static String _sheetName = "";
	private static Map<String, Map<String, ArrayList<String>>> _testMethodDataMap = null;
	
	public static Map<String, Map<String, ArrayList<String>>> getTestMethodDetail(String xmlFile) throws SAXException, IOException, ParserConfigurationException {
		_testMethodDataMap = new HashMap<String, Map<String, ArrayList<String>>>();
		Map<String,ArrayList<String>> _testDetailMap = null;
		ArrayList<String> _testMethodDataList = null;
		Document doc = DocBuilder.getDocument(xmlFile);
		doc.getDocumentElement().normalize();
		_list = NodeFactory.getNodeList(doc,Configuration.aTestNode); // for getting test nodes

		for (int i = 0; i < _list.getLength(); i++) {
			_testDetailMap = new HashMap<String,ArrayList<String>>();
			_classNodeList = NodeFactory.getEleListByTagName(_list.item(i), Configuration.aClassNode);
			_sheetName = NodeFactory.getNameAttribute(_list.item(i), Configuration.aNameAttribute);
			
			for (int j = 0; j < _classNodeList.getLength(); j++) {
				_classNodeName = NodeFactory.getNameAttribute(_classNodeList.item(j), Configuration.aNameAttribute);
				_methodNodeList = NodeFactory.getEleListByTagName(_classNodeList.item(j), Configuration.aTestMethodNode); 

				for (int k = 0; k < _methodNodeList.getLength(); k++) {
					_testMethodDataList = new ArrayList<String>();
					_testMethodName = NodeFactory.getNameAttribute(_methodNodeList.item(k), Configuration.aNameAttribute);
					_testMethodStatus = NodeFactory.getNameAttribute(_methodNodeList.item(k),Configuration.aStatusAttribute);
					
					if(NodeFactory.isDataProviderPresent(_methodNodeList.item(k), Configuration.aDataProviderAttribute)){
						String name = "";
						for(int m = 0; m < NodeFactory.getEleListByTagName(_methodNodeList.item(k), Configuration.aValueAttribute).getLength(); m++){
							name = name + "," + NodeFactory.getEleListByTagName(_methodNodeList.item(k), Configuration.aValueAttribute).item(m).getTextContent().trim();
						}
						_testMethodName = _testMethodName + "(" + name.substring(1, name.length()) + ")";
					}
					_testMethodDataList.add(Configuration.aTestNameIndex, _classNodeName + "." + _testMethodName);
					_testMethodDataList.add(Configuration.aTestStatusIndex, _testMethodStatus);
					
					if ("fail".equalsIgnoreCase(_testMethodStatus)) {
						_exceptionNodeList = NodeFactory.getEleListByTagName(_methodNodeList.item(k), Configuration.aExceptionNode);
						_exceptionTraceNodeList = NodeFactory.getEleListByTagName(_methodNodeList.item(k), Configuration.aMessageAttribute);
						_exceptionTrace = _exceptionTraceNodeList.item(Configuration.aFirstIndex).getTextContent();
						_expMessage = NodeFactory.getNameAttribute(_exceptionNodeList.item(Configuration.aFirstIndex), Configuration.aClassNode);
						_testMethodDataList.add(Configuration.aExceptionMsgIndex, _expMessage);
						_testMethodDataList.add(Configuration.aExceptionStackTrace, _exceptionTrace);
					}
					_testDetailMap.put(_testMethodDataList.get(Configuration.aFirstIndex),_testMethodDataList);
				}
			}
			_testMethodDataMap.put(_sheetName, _testDetailMap);
		}
		return _testMethodDataMap;
	}
	
	public static XSSFWorkbook createExcelFile(HashMap<String, Map<String, ArrayList<String>>> data) {
		XSSFWorkbook _book = new XSSFWorkbook();
		XSSFCellStyle _failCelStyle = _book.createCellStyle();
		XSSFCellStyle _passCelStyle = _book.createCellStyle();
		
		for(String sheetNameKey : data.keySet()){
			XSSFSheet _sheet = _book.createSheet(sheetNameKey);
			XSSFRow _row = ExcelConfiguration.CreateHeader(_book,_sheet,Configuration.aHeader);
			HashMap<String, ArrayList<String>> testMethods = (HashMap<String, ArrayList<String>>) data.get(sheetNameKey);
			int l = 1;
			
			for(String testMethod : testMethods.keySet()){
				_passCelStyle.setFillForegroundColor(HSSFColor.BRIGHT_GREEN.index);
				_failCelStyle.setFillForegroundColor(HSSFColor.RED.index);
				_passCelStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				_failCelStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				_row = _sheet.createRow(l++);
				XSSFCell _cellName = _row.createCell(Configuration.aTestNameIndex);
				_cellName.setCellValue(testMethod);
				ArrayList<String> testData = testMethods.get(testMethod);
				XSSFCell _cellStatus = _row.createCell(Configuration.aTestStatusIndex);
				
				if ("fail".equalsIgnoreCase(testData.get(Configuration.aTestStatusIndex))) {
					_cellStatus.setCellStyle(_failCelStyle);
					_cellStatus.setCellValue(testData.get(Configuration.aTestStatusIndex));
					XSSFCell _expCell = _row.createCell(Configuration.aExceptionMsgIndex);
					_expCell.setCellValue(testData.get(Configuration.aExceptionMsgIndex));
					XSSFCell _exceptionTraceCell = _row.createCell(Configuration.aExceptionStackTrace);
					_exceptionTraceCell.setCellValue(testData.get(Configuration.aExceptionStackTrace).trim());
				} else {
					_cellStatus.setCellStyle(_passCelStyle);
					_cellStatus.setCellValue(testData.get(Configuration.aTestStatusIndex));
				}
			}
		}
		return _book;
	}

}
