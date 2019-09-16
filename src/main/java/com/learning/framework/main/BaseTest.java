package com.learning.framework.main;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseTest {
	public String dataSheetPath = System.getProperty("user.dir") + "//testData//";
	public String fileName = "DataSheet.xlsx";
	String browser = null;
	static String testScenario = null;
	static String testCase = null;
	static ExtentTest extent = null;
	ExtentReports logger = null;
	LinkedHashMap<String, String> mapDataSheet = null;
	static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd.MM.YYYY-HH.mm.ss");
	static Date reportStamp = new Date();
	static String reportTimeStamp = dateTimeFormat.format(reportStamp);
	public static String reportScreenshot_path = null;
	public WebDriver driver=null;
	public static int counter=0;

	public BaseTest() {

	}

	public BaseTest(String testScenario, String testCase, String browser, LinkedHashMap<String, String> mapDataSheet) {
		this.testScenario = testScenario;
		this.testCase = testCase;
		this.browser = browser;
		this.mapDataSheet = mapDataSheet;
	}

	@BeforeMethod
	public void configureTests() {
		logger = new ExtentReports(System.getProperty("user.dir") + "\\ExtentReports\\" + testScenario + "\\" + testCase
				+ "\\" + reportTimeStamp + "\\ExtentReports.html");
		
		
		extent=logger.startTest(browser+"-"+testScenario+":"+testCase);
		
		if(browser.equalsIgnoreCase("Chrome")) {
			try {
				ChromeOptions o=new ChromeOptions();
				o.addArguments("disable-extensions");
				o.addArguments("disable-infobars");
				o.addArguments("--no-sandbox");
				o.addArguments("--disable-dev-shm-usage");
				
				DesiredCapabilities driverCapability=DesiredCapabilities.chrome();
				driverCapability.setCapability(ChromeOptions.CAPABILITY, o);
				driverCapability.setCapability("browserName", "chrome");
				driverCapability.setCapability("acceptSslCerts", "true");
				driverCapability.setCapability("javascriptEnabled", "true");
				
				System.setProperty("webdriver.chrome.driver", "C:\\Users\\jogey kristen\\Desktop\\study\\chromedriver_win32\\chromedriver.exe");
				driver=new ChromeDriver(driverCapability);
				
			}catch(Exception e) {
				
			}
		}
	}

	@AfterMethod
	public void afterTests(ITestResult testResult) {
		if(testResult.getStatus()==ITestResult.SUCCESS) {
			takeScreenshotPass(testScenario+":"+testCase+":Passed Successfully");
		}else if(testResult.getStatus()==ITestResult.FAILURE){
			takeScreenshotFail(testResult.getThrowable().getMessage());
		}
		
		logger.flush();
		logger.endTest(extent);
		logger.close();
		driver.quit();
	}

	private void takeScreenshotFail(String message) {
		captureScreenshot();
		String image=extent.addScreenCapture(reportScreenshot_path);
		extent.log(LogStatus.FAIL, message, image);
		
	}

	private void takeScreenshotPass(String message) {
		reportScreenshot_path = System.getProperty("user.dir") + "\\ExtentReports\\" + testScenario + "\\" + testCase
				+ "\\" + reportTimeStamp + "\\Screenshots\\"+counter+".jpg";
		captureScreenshot();
		String image=extent.addScreenCapture(reportScreenshot_path);
		extent.log(LogStatus.PASS, message, image);
		counter++;
		
	}

	public String getValue(String key) {
		return this.mapDataSheet.get(key);
	}
	
	public WebDriver getDriver() {
		return this.driver;
	}
	
	public void captureScreenshot() {
		try {
			TakesScreenshot ts=(TakesScreenshot)driver;
			File source=ts.getScreenshotAs(OutputType.FILE);
			File destination=new File(reportScreenshot_path);
			FileUtils.copyFile(source, destination);
			System.out.println("Screenshot Taken");
			}catch(Exception e) {
				e.printStackTrace();
			}
	}

}
