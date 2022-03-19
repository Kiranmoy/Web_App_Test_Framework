package com.company.project.team.component.core;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;


public class WebDriver_Operations {

	WebDriver driver;
	private static final int WAIT_TIME = 20; // Seconds
	private static final int SHORT_WAIT_TIME = 10; // Seconds
	
	public WebDriver_Operations(WebDriver driver) {
		this.driver = driver;
	}
	
	public void waitForPageLoading() {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(this.driver);
		wait.withTimeout(Duration.ofSeconds(WAIT_TIME))
		.pollingEvery(Duration.ofMillis(250))
		.ignoring(NoSuchElementException.class)
		.ignoring(StaleElementReferenceException.class)
		.until(new Function<WebDriver,Boolean>(){
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor)driver).executeScript("return.document.readyState").equals("complete");
			}			
		});
	}
	
	public void waitForTitleToAppear(String expectedTitle) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(this.driver);
		wait.withTimeout(Duration.ofSeconds(WAIT_TIME))
		.pollingEvery(Duration.ofMillis(250))
		.ignoring(NoSuchElementException.class)
		.ignoring(StaleElementReferenceException.class)
		.until(new Function<WebDriver,Boolean>(){
			@Override
			public Boolean apply(WebDriver driver) {
				ExpectedConditions.titleContains(expectedTitle);
				return ((JavascriptExecutor)driver).executeScript("return.document.readyState").equals("complete") 
						&& driver.getTitle().contains(expectedTitle);
			}			
		});
	}
	
	// wait for element to be visible, displayed & enabled
	public void shortWaitForElement(By by) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(this.driver);
		wait.withTimeout(Duration.ofSeconds(SHORT_WAIT_TIME))
		.pollingEvery(Duration.ofMillis(250))
		.ignoring(NoSuchElementException.class)
		.ignoring(StaleElementReferenceException.class)
		.until(new Function<WebDriver,Boolean>(){
			@Override
			public Boolean apply(WebDriver driver) {
				WebElement element = (WebElement) ExpectedConditions.visibilityOfElementLocated((by));
				return driver.findElements(by).size() > 0 &&  element.getSize().getHeight() > 0 && element.isDisplayed() && element.isEnabled();
								
			}			
		});
	}
	
	// wait for all elements to be visible, displayed & enabled
	public void shotWaitForElements(By by) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(this.driver);
		wait.withTimeout(Duration.ofSeconds(SHORT_WAIT_TIME))
		.pollingEvery(Duration.ofMillis(250))
		.ignoring(NoSuchElementException.class)
		.ignoring(StaleElementReferenceException.class)
		.until(new Function<WebDriver,Boolean>(){
			@Override
			public Boolean apply(WebDriver driver) {
				List<WebElement> elements = (List<WebElement>) ExpectedConditions.visibilityOfAllElementsLocatedBy((by));
				return elements.size() > 0;
								
			}			
		});
	}
	
	// wait for element to be visible, displayed & enabled
	public void waitForElement(By by) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(this.driver);
		wait.withTimeout(Duration.ofSeconds(WAIT_TIME))
		.pollingEvery(Duration.ofMillis(250))
		.ignoring(NoSuchElementException.class)
		.ignoring(StaleElementReferenceException.class)
		.until(new Function<WebDriver,Boolean>(){
			@Override
			public Boolean apply(WebDriver driver) {
				WebElement element = (WebElement) ExpectedConditions.visibilityOfElementLocated((by));
				return driver.findElements(by).size() > 0 &&  element.getSize().getHeight() > 0 && element.isDisplayed() && element.isEnabled();
								
			}			
		});
	}
	
	// wait for all elements to be visible, displayed & enabled
	public void waitForElements(By by) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(this.driver);
		wait.withTimeout(Duration.ofSeconds(WAIT_TIME))
		.pollingEvery(Duration.ofMillis(250))
		.ignoring(NoSuchElementException.class)
		.ignoring(StaleElementReferenceException.class)
		.until(new Function<WebDriver,Boolean>(){
			@Override
			public Boolean apply(WebDriver driver) {
				List<WebElement> elements = (List<WebElement>) ExpectedConditions.visibilityOfAllElementsLocatedBy((by));
				return elements.size() > 0;
								
			}			
		});
	}
	
	// wait for element to be disappear, i.e Size = 0
	public void waitForElementToDisappear(By by) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(this.driver);
		wait.withTimeout(Duration.ofSeconds(WAIT_TIME))
		.pollingEvery(Duration.ofMillis(250))
		.ignoring(NoSuchElementException.class)
		.ignoring(StaleElementReferenceException.class)
		.until(new Function<WebDriver,Boolean>(){
			@Override
			public Boolean apply(WebDriver driver) {
				ExpectedConditions.invisibilityOfElementLocated(by);
				return driver.findElements(by).size() <= 0;
			}			
		});
	}
	
	public void waitForElementClickable(By by) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(this.driver);
		wait.withTimeout(Duration.ofSeconds(WAIT_TIME))
		.pollingEvery(Duration.ofMillis(250))
		.ignoring(NoSuchElementException.class)
		.ignoring(StaleElementReferenceException.class)
		.until(new Function<WebDriver,Boolean>(){
			@Override
			public Boolean apply(WebDriver driver) {
				WebElement element = (WebElement) ExpectedConditions.visibilityOfElementLocated((by));
				ExpectedConditions.elementToBeClickable(by);
				return element.getSize().getHeight() > 0 && element.isDisplayed() && element.isEnabled();
			}			
		});
	}
	
	public void waitForCSSValue(By by, String cssField, String cssValue) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(this.driver);
		wait.withTimeout(Duration.ofSeconds(WAIT_TIME))
		.pollingEvery(Duration.ofMillis(250))
		.ignoring(NoSuchElementException.class)
		.ignoring(StaleElementReferenceException.class)
		.until(new Function<WebDriver,Boolean>(){
			@Override
			public Boolean apply(WebDriver driver) {
				WebElement element = (WebElement) ExpectedConditions.visibilityOfElementLocated((by));
				return driver.findElements(by).size() > 0 &&  element.getSize().getHeight() > 0 && element.isDisplayed() && element.isEnabled()
						&& element.getCssValue(cssField).contains(cssValue);
			}			
		});
	}	
	
	public void waitForElementUntilTextIsPresent(By by, String text) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(this.driver);
		wait.withTimeout(Duration.ofSeconds(WAIT_TIME))
		.pollingEvery(Duration.ofMillis(250))
		.ignoring(NoSuchElementException.class)
		.ignoring(StaleElementReferenceException.class)
		.until(new Function<WebDriver,Boolean>(){
			@Override
			public Boolean apply(WebDriver driver) {
				WebElement element = (WebElement) ExpectedConditions.textToBePresentInElementLocated(by, text);
				return driver.findElements(by).size() > 0 &&  element.getSize().getHeight() > 0 && element.isDisplayed() && element.isEnabled();
			}		
		});
	}
	
	public void waitForAndSwitchToFrame(String frameName) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(this.driver);
		wait.withTimeout(Duration.ofSeconds(WAIT_TIME))
		.pollingEvery(Duration.ofMillis(250))
		.ignoring(NoSuchElementException.class)
		.ignoring(StaleElementReferenceException.class)
		.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
	}
	
	public void pageReload() {
		driver.navigate().refresh();
		waitForPageLoading();		
	}
	
	
	
}
