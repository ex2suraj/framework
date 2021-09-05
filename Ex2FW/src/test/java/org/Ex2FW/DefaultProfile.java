package org.Ex2FW;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

public class DefaultProfile extends base {
	
	
	// Start a default user profile in chrome
	@Test
	public void StartDefault() throws IOException {
	System.setProperty("webdriver.chrome.driver", "./exefiles/chromedriver.exe");
	ChromeOptions option = new ChromeOptions();
	option.addArguments("user-data-dir=C:/Users/skumar/AppData/Local/Google/Chrome/User Data");
	WebDriver driver = new ChromeDriver(option);
	driver.get("https://my.duda.co/home/dashboard/sites");
	
	}
}
