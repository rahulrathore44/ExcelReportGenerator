/**
 * 
 */
package com.xls.report.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xls.report.config.ElementLocator;


public class JsonUtility {
	
	/**
	 * @param aMesg
	 * @return	
	 */
	public static String getJsonString(String aMesg) {
		int sIndex = aMesg.indexOf('{');
		int eIndex = aMesg.indexOf('}');
		
		if(sIndex == -1 || eIndex == -1)
			return "";
		//System.out.println("====================" + aMesg.substring(sIndex, eIndex));
		return aMesg.substring(sIndex, (eIndex + 1));
				
	}
	
	/**
	 * @param aMesg
	 * @return	
	 */
	public static String deserializeJsonObject(String aMesg) {
		Gson gson = new GsonBuilder().create();
		ElementLocator locator = gson.fromJson(getJsonString(aMesg), ElementLocator.class);
		if(locator == null)
			return "";
		return locator.toString();
	}
}
