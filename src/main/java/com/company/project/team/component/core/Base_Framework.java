package com.company.project.team.component.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import com.company.project.team.component.utilities.BrowserstackUtils;
import com.company.project.team.component.utilities.CommonUtils;

public class Base_Framework {

	private String BROWSERSTACK_USERNAME;
	private String BROWSERSTACK_KEY;
	private String URL;
	private DesiredCapabilities caps;
	private String testExecutionEnvironment;
	private String executionMode;
	
	public Base_Framework(String testCaseName) throws Exception {
		this.executionMode =  CommonUtils.getConfigProperty("EXECUTION.MODE");
		this.testExecutionEnvironment = CommonUtils.getConfigProperty("TEST.ENVIRONMENT");
		
		if(this.executionMode.equalsIgnoreCase("BROWSERSTACK")) {
			browserStackSetup(testCaseName);
		}else if(this.executionMode.equalsIgnoreCase("LOCAL")) {
			localSetup();
		}else {
			throw new Exception("Invalid Execution Mode");
		}
		
	}

	public String getTestExecutionEnvironment() {
		return this.testExecutionEnvironment;
	}
	
	private void localSetup() {
		
	}
	
	private void browserStackSetup(String testCaseName ) throws Exception {
		// BrowserStack Configuration
		this.BROWSERSTACK_USERNAME = CommonUtils.getConfigProperty("BROWSERSTACK.USERNAME");
		this.BROWSERSTACK_KEY = CommonUtils.getConfigProperty("BROWSERSTACK.PASSWORD");
		this.URL = "https://" + BROWSERSTACK_USERNAME + ":" + BROWSERSTACK_KEY + "@hub-cloud.browserstack.com/wd/hub";

		caps = new DesiredCapabilities();
		caps.setCapability("os", CommonUtils.getConfigProperty("OS"));
		caps.setCapability("os_version", CommonUtils.getConfigProperty("OS.VERSION"));
		caps.setCapability("browser", CommonUtils.getConfigProperty("BROWSER"));
		caps.setCapability("browser_version", CommonUtils.getConfigProperty("BROWSER.VERSION"));
		caps.setCapability("browserstack.debug", "true");
		caps.setCapability("project", CommonUtils.getConfigProperty("PROJECT"));
		caps.setCapability("build", CommonUtils.getConfigProperty("BUILD"));
		caps.setCapability("name", testCaseName); // Sets Test Case Execution Name in BrowserStack
	}
	
	public WebDriver getDriver() throws Exception {
		WebDriver driver=null;
		
		if(this.executionMode.equalsIgnoreCase("BROWSERSTACK")) {
			try {
				driver = new RemoteWebDriver(new URL(this.URL),this.caps);
				BrowserstackUtils.browserStackLog(driver, "info", "Launched Browser in BrowserStack - " + this.URL);
			} catch (MalformedURLException e) {
				throw new Exception("RemoteWebDriver Failed - Unable to Invoke Browser - Malformed URL Exception");			
			}
		}else if(executionMode.equalsIgnoreCase("LOCAL")) {
			driver = new ChromeDriver();
			BrowserstackUtils.browserStackLog(driver, "info", "Launched Browser Locally");
		}else {
			throw new Exception("Invalid Execution Mode");
		}
		
		return driver;
	}
	
	public void launchApplication(WebDriver driver) throws Exception {
		String appURL = CommonUtils.getConfigProperty("APPLICATION.URL");
		driver.manage().window().maximize();
		BrowserstackUtils.browserStackLog(driver, "info", "Maximize Browser Window");
		driver.manage().deleteAllCookies();
		BrowserstackUtils.browserStackLog(driver, "info", "Delete All Cookies");
		driver.get(appURL);
		BrowserstackUtils.browserStackLog(driver, "info", "Hit URL to Launch Application - " + appURL);
		
	}
	
	
}
