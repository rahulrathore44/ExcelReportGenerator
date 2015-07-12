/**
 * 
 */
package com.xls.report.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author - rahul.rathore
 * @date - 21-May-2015
 * @project - ExcelReportGenerator
 * @package - com.java.name
 * @file name - FileName.java
 */
public class FileName {
	private static DateFormat dateFormat;
	private static Calendar cal;
	
	public static String getFileName() {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		cal = Calendar.getInstance();
		return "Excel_Report_" + dateFormat.format(cal.getTime()) + ".xlsx";
	}

}
