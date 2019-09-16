package com.learning.framework.main;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.LogStatus;

public class BasePage {
	WebDriver driver=null;
	
	public BasePage(WebDriver driver) {
		this.driver=driver;	
	}
	
	protected enum BY_TYPE{
		BY_ID,
		BY_CLASS,
		BY_LINKTEXT,
		BY_XPATH
	}
	
	public By getLocator(String locator,BY_TYPE type) {
		switch(type) {
		case BY_ID:
			return By.id(locator);
		case BY_CLASS:
			return By.className(locator);
		case BY_LINKTEXT:
			return By.linkText(locator);
		case BY_XPATH:
			return By.xpath(locator);
		}
		return null;
		
		
	}
	
	public void click(By locator, String message) {
		try
		{
			driver.findElement(locator).click();
			takeScreenshotPass("Button "+message+" is successfully clicked");
		}catch(Exception e) {
			takeScreenshotFail(e.getMessage());
		}
	}
	
	public void set(By locator,String data, String message) {
		try
		{
			driver.findElement(locator).sendKeys(data);
			takeScreenshotPass(data+" successfully set to field "+message);
		}catch(Exception e) {
			takeScreenshotFail(e.getMessage());
		}
	}
	
	public void loadUrl(String url) {
		try
		{
			driver.get(url);
			takeScreenshotPass(url + "successfully loaded");
		}catch(Exception e) {
			takeScreenshotFail(e.getMessage());
		}
	}
	
	private void takeScreenshotFail(String message) {
		BaseTest.reportScreenshot_path = System.getProperty("user.dir") + "\\ExtentReports\\" + BaseTest.testScenario + "\\" + BaseTest.testCase
				+ "\\" + BaseTest.reportTimeStamp + "\\Screenshots\\"+BaseTest.counter+".jpg";
		captureScreenshot();
		String image=BaseTest.extent.addScreenCapture(BaseTest.reportScreenshot_path);
		BaseTest.extent.log(LogStatus.FAIL, message, image);
		
		
	}

	private void takeScreenshotPass(String message) {
		BaseTest.reportScreenshot_path = System.getProperty("user.dir") + "\\ExtentReports\\" + BaseTest.testScenario + "\\" + BaseTest.testCase
				+ "\\" + BaseTest.reportTimeStamp + "\\Screenshots\\"+BaseTest.counter+".jpg";
		captureScreenshot();
		String image=BaseTest.extent.addScreenCapture(BaseTest.reportScreenshot_path);
		BaseTest.extent.log(LogStatus.PASS, message, image);
		BaseTest.counter++;
	}

	
	
	public void captureScreenshot() {
		try {
			TakesScreenshot ts=(TakesScreenshot)driver;
			File source=ts.getScreenshotAs(OutputType.FILE);
			File destination=new File(BaseTest.reportScreenshot_path);
			FileUtils.copyFile(source, destination);
			System.out.println("Screenshot Taken");
			}catch(Exception e) {
				e.printStackTrace();
			}
	}


}
