package com.company.project.team.component.utilities;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Base64;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class BrowserstackUtils {

	// Setting the status of test as 'passed' or 'failed' based on the condition
	public static void logValidationStatus(WebDriver driver, String reason, Boolean isPassed) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		if (isPassed) {
			jse.executeScript(
					"browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \""
							+ reason + "\"}}");
		} else {
			jse.executeScript(
					"browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \""
							+ reason + "\"}}");
		}
	}

	public static String getDownloadedReportCopntent(WebDriver driver, String filename) throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		System.out.println(filename);
		int counter = 0;
		boolean isFileExist = false;
		while (counter < 60 & !isFileExist) {
			isFileExist = Boolean.valueOf(String.valueOf(jse.executeScript(
					"browserstack_executor: {\"action\": \"fileExists\", \"arguments\": {\"fileName\": \"" + filename
							+ "\"}}")));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			counter++;
		}
		browserStackLog(driver, "debug", filename + " Exist = " + isFileExist);		

		// Get file properties
		String fileProperties =(String) jse.executeScript(
				"browserstack_executor: {\"action\": \"getFileProperties\", \"arguments\": {\"fileName\": \"" + filename
						+ "\"}}");
		browserStackLog(driver, "debug", filename + " Properties = " + fileProperties);	
		
		// Get file content. The content is Base64 encoded
		String base64EncodedFile = (String) jse.executeScript(
				"browserstack_executor: {\"action\": \"getFileContent\", \"arguments\": {\"fileName\": \"" + filename
						+ "\"}}");

		// Decode the content to Base64 and write to a file
		byte[] data = Base64.getDecoder().decode(base64EncodedFile);

		// Creating Temporary File in temp folder
		String filePath = CommonUtils.getConfigProperty("BASE.PATH") + "\\"
				+ CommonUtils.getConfigProperty("TEMP.FOLDER.PATH") + "\\" + filename;
		CommonUtils.generateFileFromBytes(filePath, data);
		String fileContent = "";
		if (filePath.endsWith(".pdf")) {
			fileContent = PDFUtils.getPdfContent(filePath);
			try {
				Files.deleteIfExists(Paths.get(filePath));
			} catch (NoSuchFileException e) {
				throw new Exception("No Such File.Directory Exists");
			} catch (DirectoryNotEmptyException e) {
				throw new Exception("Diretory is not Empty");
			} catch (IOException e) {
				throw new Exception("Invalid Permission");
			}			
			browserStackLog(driver, "debug", "File Deleted : " + filePath);	
		} else if (filePath.endsWith(".xls")) {
			return new String(data);
		}

		return fileContent;
	}

	// LOG LEVEL - debug/info/warn
	public static void browserStackLog(WebDriver driver, String logLevel, String message) throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("browserstack_executor: {\"action\": \"annotate\", \"arguments\": {\"data\": \"" + message
				+ "\", \"level\": \"" + logLevel.toLowerCase() + "\"}}");

		switch (logLevel.toLowerCase()) {

		case "warn":
			CommonUtils.log(message, "WARN");
			break;
		case "info":
			CommonUtils.log(message, "INFO");
			break;
		case "debug":
			CommonUtils.log(message, "DEBUG");
			break;
		default:
			throw new Exception("Invalid Log Level !!!!");
		}

	}

	// LOG LEVEL - error
	public static void browserStackLogException(WebDriver driver, Exception e) throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String str[] = CommonUtils.exceptionToString(e);
		String message = String.join("||", str);
		jse.executeScript("browserstack_executor: {\"action\": \"annotate\", \"arguments\": {\"data\": \"" + message
				+ "\", \"level\": \"error\"}}");
		CommonUtils.log(message, "ERROR");
	}

}
