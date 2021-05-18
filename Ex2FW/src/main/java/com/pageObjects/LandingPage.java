package com.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LandingPage {

	public WebDriver driver;
	
	By firstname = By.name("firstname");
	By lastname = By.name("lastname");
	

	public LandingPage(WebDriver driverObj) {
		// TODO Auto-generated constructor stub
		this.driver=driverObj;
	}


	public WebElement enterFirstname(){
		return driver.findElement(firstname);
	}

	public WebElement enterLastname(){
		return driver.findElement(lastname);
	}

}
