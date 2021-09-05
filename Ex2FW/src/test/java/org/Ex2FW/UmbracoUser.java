/**
 * 
 */
package org.Ex2FW;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.Test;

import Utils.Config;

/**
 * @author skumar
 *
 */
public class UmbracoUser extends base {

	/**
	 * @param args
	 * @throws Exception 
	 */
	
@Test
public void createMigrationUser() throws Exception
{
	
	String url = "https://www.allenedwin.com/";
	String fname = "Test";
	String lname = "user";
	String useremail = "miguser2@yopmail.com";
	
	driver = initDriver();
	driver.get(url);
	
	driver.findElement(By.id("sign-in")).click();

	Thread.sleep(3000);
	// Switch to POPUP window
	String Main_window = driver.getWindowHandle();
	for (String popupwindow : driver.getWindowHandles()) {
		driver.switchTo().window(popupwindow);
	}
	driver.findElement(By.id("FirstName")).sendKeys(fname);
	driver.findElement(By.id("LastName")).sendKeys(lname);
	driver.findElement(By.id("EmailAddress")).sendKeys(useremail);
	driver.findElement(By.id("ConfirmEmailAddress")).sendKeys(useremail);
	driver.findElement(By.id("Password")).sendKeys("hello@123");
	driver.findElement(By.id("ConfirmPassword")).sendKeys("hello@123");
	System.out.println("Creating user for "+useremail);
	Config.takeSnapshot(driver, "MigratedUser_duda");
	Thread.sleep(3000);
	driver.findElement(By.id("create-user")).click();
}		
	}


