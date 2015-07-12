/**
 * 
 */
package com.xls.report.utility;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author - rahul.rathore
 * @date - 12-Jul-2015
 * @project - ExcelReportGenerator
 * @package - com.xls.report.utility
 * @file name - NodeFactory.java
 */
public class NodeFactory {
	
	public static NodeList getNodeList(Document doc,String aNodeName) {
		return doc.getElementsByTagName(aNodeName);
	}

	public static String getNameAttribute(Node aNode, String aAttribute) {
		return ((Element) aNode).getAttribute(aAttribute);
	}

	public static NodeList getEleListByTagName(Node aNode, String aTagName) {
		return ((Element) aNode).getElementsByTagName(aTagName);
	}
	
	public static boolean isDataProviderPresent(Node aNode,final String aDataProvider){
		return (getNameAttribute(aNode, "data-provider").length() >= 1);
	}
	
	

}
