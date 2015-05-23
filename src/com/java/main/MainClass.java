/**
 * 
 */
package com.java.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * @author - rahul.rathore
 * @date - 21-May-2015
 * @project - ExcelReportGenerator
 * @package - com.java.main
 * @file name - MainClass.java
 */
public class MainClass {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		if((args.length  < 1 ))
			throw new FileNotFoundException("File : testng-result.xml "
					+ "was not present at the "
					+ "given location");
		ExcelReport.generateReport(args[0]);
	}

}
