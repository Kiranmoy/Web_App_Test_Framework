package com.company.project.team.component.utilities;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.text.StringSubstitutor;

public class CommonUtils {

	public static final String PROJECT_PATH = System.getProperty("user.dir");

	public static void handledSleep(int sleepInSeconds) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal2.add(Calendar.SECOND, sleepInSeconds);
		while (cal2.after(cal1)) {
			cal1 = Calendar.getInstance();
		}
	}

	public static String getConfigProperty(String key) throws Exception {
		// Read config.properties file
		FileReader reader = null;
		Properties prop = null;
		try {
			reader = new FileReader(PROJECT_PATH + "/src/test/resources" + "/config.properties");
			prop = new Properties();
			prop.load(reader);
		} catch (FileNotFoundException e) {
			throw new Exception("Failed to Read config.properties");
		} catch (IOException e) {
			throw new Exception("Failed to Load config.properties");
		}
		String value = prop.getProperty(key);
		return value;
	}

	public static String getDBConfigProperty(String key) throws Exception {
		// Read config.properties file
		FileReader reader = null;
		Properties prop = null;
		try {
			reader = new FileReader(PROJECT_PATH + "/src/test/resources" + "/dbConfig.properties");
			prop = new Properties();
			prop.load(reader);
		} catch (FileNotFoundException e) {
			throw new Exception("Failed to Read dbConfig.properties");
		} catch (IOException e) {
			throw new Exception("Failed to Load dbConfig.properties");
		}
		String value = prop.getProperty(key);
		return value;
	}

	public static String getTestData(String key) throws Exception {
		// Read config.properties file
		FileReader reader = null;
		Properties prop = null;
		try {
			reader = new FileReader(PROJECT_PATH + "/src/test/resources" + "/testData.properties");
			prop = new Properties();
			prop.load(reader);
		} catch (FileNotFoundException e) {
			throw new Exception("Failed to Read testData.properties");
		} catch (IOException e) {
			throw new Exception("Failed to Load testData.properties");
		}
		String value = prop.getProperty(key);
		return value;
	}

	// Pass Parameters in the following format : KEY=VALUE, KEY=VALUE, ....
	public static String formatPropertyString(String templateString, String... params) {
		// Build map
		Map<String, String> parameters = new HashedMap<String, String>();
		for (String value : params) {
			String[] entry = value.split("=");
			String mapKey = entry[0];
			String mapValue = entry[1];
			parameters.put(mapKey, mapValue);
		}
		// Build StringSubstitutor
		StringSubstitutor sub = new StringSubstitutor(parameters);
		// Replace
		String resolvedString = sub.replace(templateString);
		return resolvedString;
	}

	// Specify Date in any Fromat like dd-MMMM-yyyy or dd-MM-yyyy
	// Return Date ARRAY like [dd, MMMM, yyyy]
	// days -It can also Subtract (-days) or add(+days) add days to the Current date
	public static String[] getDateArray(String date, String delimiter, int days) {

		String[] dateArray = date.split(delimiter);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);

		// Extract date & month in the format: 25 & November
		SimpleDateFormat simpleformat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
		simpleformat = new SimpleDateFormat(dateArray[2]);
		String strYear = simpleformat.format(cal.getTime());
		simpleformat = new SimpleDateFormat(dateArray[1]);
		String strMonth = simpleformat.format(cal.getTime());
		simpleformat = new SimpleDateFormat(dateArray[0]);
		String strDay = simpleformat.format(cal.getTime());

		return new String[] { strDay, strMonth, strYear };
	}

	public static void generateFileFromBytes(String absoluteFilePath, byte[] data) throws Exception {
		OutputStream stream = null;
		try {
			stream = new FileOutputStream(absoluteFilePath);
			stream.write(data);
		} catch (FileNotFoundException e) {
			throw new Exception("File Not Found : " + absoluteFilePath);
		} catch (IOException e) {
			throw new Exception("Failed To Write : " + absoluteFilePath);
		}finally {
			try {
				stream.close();
			}catch(IOException e) {
				throw new Exception("Failed to Close : " + absoluteFilePath);
			}
		}
		
	}
	
	public static void log(String message, String logLevel) {
		StackTraceElement ste = Thread.currentThread().getStackTrace()[3];
		String callerClass = ste.getClassName();
		String callerMethod = ste.getMethodName();
		int callerLineNumber = ste.getLineNumber();
		
		// 2021-03-24 16:34:26.666
		Date date = new Date();
		String timeStamp = new Timestamp(date.getTime()).toString();
		String logFormat = "[%s]  - %s  - [%s , %s (%d)] \t: %s%n";
		System.out.printf(logFormat, logLevel, timeStamp, callerClass, callerMethod, callerLineNumber, message);		
	}
	
	public static String[] exceptionToString(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		
		String[] erroMSG = sw.toString().split("\n");
		int i=0;
		for(String s : erroMSG) {
			erroMSG[i] = s.trim().replace("\"", "'");
			i++;
		}
		return erroMSG;
	}
	

}
