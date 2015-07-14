/**
 * 
 */
package com.xls.report.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.xls.report.main.ExcelReport;

/**
 * @author - rahul.rathore
 * @date - 21-May-2015
 * @project - ExcelReportGenerator
 * @package - com.java.main
 * @file name - MainClass.java
 */
public class MainClass {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException, InterruptedException {
		/*if((args.length  < 1 ))
			throw new FileNotFoundException("File : testng-result.xml "
					+ "was not present at the "
					+ "given location");*/
		//ExcelReport.generateReport(args[0]);
		ExcelReport.generateReport("F:\\testng-results.xml");
		Thread.sleep(2000);
		ExcelReport.generateReport("F:\\dptestng-results.xml");
		Thread.sleep(2000);
		ExcelReport.generateReport("F:\\pktestng-results.xml");
		Thread.sleep(2000);
		ExcelReport.generateReport("F:\\AllAnotestng-results.xml");
		//ExcelReport.CreateOrUpdateReport("Excel_Report_2015-07-14_23-39-30.xlsx", "F:\\newdptestng-results.xml");
		
		/*HashMap<String, Map<String, ArrayList<String>>> data = (HashMap<String, Map<String, ArrayList<String>>>) ExcelReportUpdate.getTestMethodDetail("F:\\testng-results.xml");
		for(String outerKey : data.keySet()){
			HashMap<String, ArrayList<String>> innerData = (HashMap<String, ArrayList<String>>) data.get(outerKey);
			for(String innerKey : innerData.keySet()){
				ArrayList<String> nextInnerData = innerData.get(innerKey);
				for(int i = 0; i < nextInnerData.size(); i++){
					if(i >= 1 && "fail".equalsIgnoreCase(nextInnerData.get(1)))
						continue;
					System.out.println(nextInnerData.get(i));
				}
			}
		}*/
		//ExcelReport.generateReport("G:\\AutomationWorkspace\\Automation-Frameworks\\Webdriver\\test-output\\testng-results.xml");
	}

}
