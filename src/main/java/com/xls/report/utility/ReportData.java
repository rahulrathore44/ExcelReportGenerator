/**
 * 
 */
package com.xls.report.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.xls.report.config.Configuration;
import com.xls.report.utility.DocBuilder;
import com.xls.report.utility.NodeFactory;



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

		_list = NodeFactory.getNodeList(doc,"test"); // for getting test nodes

		/* Outer loop for scanning all the <test> */
		for (int i = 0; i < _list.getLength(); i++) {
					
			/* for getting the class nodes */
			_testDetailMap = new HashMap<String,ArrayList<String>>();
			_classNodeList = NodeFactory.getEleListByTagName(_list.item(i), "class");
			_sheetName = NodeFactory.getNameAttribute(_list.item(i), "name");
			
			/* inner loop for scanning all the <class> */
			for (int j = 0; j < _classNodeList.getLength(); j++) {
				_classNodeName = NodeFactory.getNameAttribute(_classNodeList.item(j), "name");
				
				/* for getting the list of test method nodes */
				_methodNodeList = NodeFactory.getEleListByTagName(_classNodeList.item(j), "test-method"); 

				/* inner loop for scanning all the <test-method> */
				for (int k = 0; k < _methodNodeList.getLength(); k++) {
					_testMethodDataList = new ArrayList<String>();
					
					_testMethodName = NodeFactory.getNameAttribute(_methodNodeList.item(k), "name");
					_testMethodStatus = NodeFactory.getNameAttribute(_methodNodeList.item(k),"status");
					
					if(NodeFactory.isDataProviderPresent(_methodNodeList.item(k), "data-provider")){
						String name = "";
						for(int m = 0; m < NodeFactory.getEleListByTagName(_methodNodeList.item(k), "value").getLength(); m++){
							name = name + "," + NodeFactory.getEleListByTagName(_methodNodeList.item(k), "value").item(m).getTextContent().trim();
						}
						_testMethodName = _testMethodName + "(" + name.substring(1, name.length()) + ")";
					}
					
					_testMethodDataList.add(Configuration.aTestNameIndex, _classNodeName + "." + _testMethodName);
					_testMethodDataList.add(Configuration.aTestStatusIndex, _testMethodStatus);
					
					if ("fail".equalsIgnoreCase(_testMethodStatus)) {
						
						_exceptionNodeList = NodeFactory.getEleListByTagName(_methodNodeList.item(k), "exception");
						_exceptionTraceNodeList = NodeFactory.getEleListByTagName(_methodNodeList.item(k), "message");
						_exceptionTrace = _exceptionTraceNodeList.item(0).getTextContent();
						_expMessage = NodeFactory.getNameAttribute(_exceptionNodeList.item(0), "class");
						
						_testMethodDataList.add(Configuration.aExceptionMsgIndex, _expMessage);
						_testMethodDataList.add(Configuration.aExceptionStackTrace, _exceptionTrace);
					}
					_testDetailMap.put(_testMethodDataList.get(0),_testMethodDataList);
				}
				
			}
			_testMethodDataMap.put(_sheetName, _testDetailMap);
		}
		return _testMethodDataMap;
	}

}
