package com.learning.framework.tests;

import java.util.LinkedHashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.learning.framework.main.BaseTest;
import com.learning.framework.pages.Login;
import com.learning.framework.utilities.DataSheet;

public class Test1 extends BaseTest {

	public Test1() {
		super();
		
	}

	public Test1(String testScenario,String testCase,String browser, LinkedHashMap<String, String> mapDataSheet) {
		super(testScenario,testCase,browser, mapDataSheet);
	}

	@Factory(dataProvider = "dataSheet")
	public Object[] createInstance(LinkedHashMap<String, String> mapDataSheet) {
		return new Object[] { new Test1(this.getClass().getSimpleName(),mapDataSheet.get("Test Case"),mapDataSheet.get("browser"), mapDataSheet) };
	}

	@DataProvider(name = "dataSheet")
	public Object[][] getDataFromSheet() {
		String sheetName = this.getClass().getSimpleName();
		DataSheet sheet = new DataSheet();
		
		Object[][] dataArray = sheet.readDataFromExcel(dataSheetPath, fileName, sheetName);
		return dataArray;

	}

	@Test
	public void testing() {
		Login login=new Login(getDriver());
		login.loadApplication(getValue("URL"));
		login.loginToApplication(getValue("Username"), getValue("Password"));
	}

}
