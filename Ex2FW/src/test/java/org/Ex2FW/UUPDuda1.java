package org.Ex2FW;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import com.pageObjects.UUPLoginPage;

import Utils.Config;

public class UUPDuda1 extends base {

	@Test
	public void CheckUUP_service() throws Exception {

		// Configure the URL
		String UUPPage = "https://www.laxhomes.com/";
		String Useremail = "dudauser3@yopmail.com";
		String Userpass = "Cisco@123"; // After passwd change --> test@123
		
		driver = initDriver();
		driver.get(UUPPage);
		System.out.println(driver.getTitle() + "____" + driver.getCurrentUrl());

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		UUPLoginPage UUP = new UUPLoginPage(driver);
		UUP.CheckUUPSetUp();

		// Switch to POPUP window
		String Main_window = driver.getWindowHandle();
		for (String popupwindow : driver.getWindowHandles()) {
			driver.switchTo().window(popupwindow);
		}
		System.out.println(driver.getTitle() + "__>>__" + driver.getCurrentUrl());
		UUP.VerifySignUp();
		Config.takeSnapshot(driver, "SignUP_Validation");
		Thread.sleep(3000);

		// LogIn page validation
		UUP.VerifyLogIN();
		Config.takeSnapshot(driver, "LogIn_Validation");

		Thread.sleep(3000);
		driver.switchTo().window(Main_window);
		System.out.println(driver.getTitle() + "____" + driver.getCurrentUrl());

		UUP.CheckUUPSetUp();

		// Switch to POPUP window
		for (String popupwindow1 : driver.getWindowHandles()) {
			driver.switchTo().window(popupwindow1);
		}
		System.out.println(driver.getTitle() + "__>>__" + driver.getCurrentUrl());
		Thread.sleep(3000);
		UUP.CreateNewUUPLogin("test", "duda", Useremail, Userpass);
		System.out.println("New User created : "+Useremail+" __ "+Userpass);
		Thread.sleep(9000);
		driver.quit();
		//driver.navigate().to(UUPPage);
		//UUP.resetpasswd(Useremail);
		//System.out.println("Reset done");
		//driver.navigate().to(yopmail);
		//Thread.sleep(5000);
		//UUP.doResetYopmail(Useremail);
		}
}
