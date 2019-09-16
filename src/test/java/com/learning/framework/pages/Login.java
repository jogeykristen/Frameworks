package com.learning.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.learning.framework.main.BasePage;

public class Login extends BasePage{
	By txt_userName=getLocator("//span[text()='Enter Email/Mobile number']/parent::label/preceding-sibling::input", BY_TYPE.BY_XPATH);
	By txt_password=getLocator("//span[text()='Enter Password']/parent::label/preceding-sibling::input", BY_TYPE.BY_XPATH);
	By btn_login=getLocator("//span[text()='Login']/parent::button", BY_TYPE.BY_XPATH);
	public Login(WebDriver driver) {
		super(driver);
		
	}
	
	public void loadApplication(String url) {
		loadUrl(url);
	}
	
	public void loginToApplication(String userName, String password) {
		
		click(txt_userName, "txt_userName in Login Page");
		set(txt_userName, userName, "txt_userName in Login Page");
		click(txt_password, "txt_password in Login Page");
		set(txt_password,password , "txt_password in Login Page");
		click(btn_login, "btn_login in Login Page");
	}

}
