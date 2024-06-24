package com.kk.msdi;

import java.io.FileReader;
import java.util.Properties;

public class PropertiesUtil {

	public static AllProperties getProperties() {
		Properties properties = new Properties();
		AllProperties allProp=new AllProperties();
		try {
			String propFileName = "config.properties";
			properties.load(new FileReader(propFileName));
			String USERNAME = properties.getProperty("USERNAME");
			String PASSWORD = properties.getProperty("PASSWORD");
			String TARGET_URL = properties.getProperty("TARGET_URL");
			String TABLE_NAME = properties.getProperty("TABLE_NAME");
			String TABLE_SYS_ID = properties.getProperty("TABLE_SYS_ID");
			String LOG_FILE = properties.getProperty("LOG_FILE");
			String STATUS_LOG_FILE = properties.getProperty("STATUS_LOG_FILE");
			String FOLDERS_TO_MONITOR=properties.getProperty("FOLDERS_TO_MONITOR");
			
			
			allProp.USERNAME=USERNAME;
			allProp.PASSWORD=PASSWORD;
			allProp.TARGET_URL=TARGET_URL;
			allProp.TABLE_NAME=TABLE_NAME;
			allProp.TABLE_SYS_ID=TABLE_SYS_ID;
			allProp.LOG_FILE=LOG_FILE;
			allProp.STATUS_LOG_FILE=STATUS_LOG_FILE;
			allProp.FOLDERS_TO_MONITOR=FOLDERS_TO_MONITOR;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allProp;
	}
	
	public static String getLogFileName()
	{
		String LOG_FILE="";
		try {
			Properties properties = new Properties();
			String propFileName = "config.properties";
			properties.load(new FileReader(propFileName));
			
			LOG_FILE= properties.getProperty("LOG_FILE");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return LOG_FILE;
	}

}



class AllProperties {
	String USERNAME;
	String PASSWORD;
	String TARGET_URL;
	String TABLE_NAME;
	String TABLE_SYS_ID;
	String LOG_FILE;
	String STATUS_LOG_FILE;
	String FOLDERS_TO_MONITOR;
}