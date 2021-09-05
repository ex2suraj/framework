package org.Ex2FW;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.pageObjects.UUPLoginPage;

import Utils.Config;

public class UUPLogin extends base {

	@Test
	public void CheckUUP_service() throws Exception {

		// Configure the URL
		String UUPPage = "https://www.laxhomes.com";
		//String yopmail = "https://yopmail.com/en/";
		String Useremail = "dudauser1@yopmail.com";
		String Userpass = "Cisco@123"; // After passwd change --> test@123
		
		driver = initDriver();
		driver.get(UUPPage);
		System.out.println(driver.getTitle() + "____" + driver.getCurrentUrl());

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		UUPLoginPage UUP1 = new UUPLoginPage(driver);
		UUP1.UserUUPLogin(Useremail,Userpass);
		Config.takeSnapshot(driver, "Userlogin_UUP_Verification");
		System.out.println("Login done --");
		System.out.println(driver.getTitle() + "__>>__" + driver.getCurrentUrl());
		Thread.sleep(5000);
		Config.takeSnapshot(driver, "Userlogin_UUP_Success");
		driver.findElement(By.xpath("//a[@class='fav-acc-txt btn' and text()='Sign Out']")).click();
		//UUP.UserUUP_Signout();
		System.out.println("Sign Out Done");
	}
}
