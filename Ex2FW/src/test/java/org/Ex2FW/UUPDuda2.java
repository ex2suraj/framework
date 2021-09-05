package org.Ex2FW;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import com.pageObjects.UUPLoginPage;

import Utils.Config;

public class UUPDuda2 extends base {

	@Test
	public void CheckUUP_service() throws Exception {

		// Configure the URL
		String UUPPage = "https://www.allenedwin.com/";
		String Useremail = "skumar@ex2india.com";
		String Userpass = "hello@123"; // After passwd change --> test@123

		driver = initDriver();
		driver.get(UUPPage);
		System.out.println(driver.getTitle() + "____" + driver.getCurrentUrl());

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		UUPLoginPage UUP = new UUPLoginPage(driver);
		UUP.CheckUUPSetUp();

		// Switch to POPUP window
		// String Main_window = driver.getWindowHandle();
	//	for (String popupwindow : driver.getWindowHandles()) {
	//		driver.switchTo().window(popupwindow);
	//	}
		System.out.println(driver.getTitle() + "__>>__" + driver.getCurrentUrl());
//		UUP.UserUUPLogin(Useremail, Userpass);
//		Thread.sleep(3000);
//		Config.takeSnapshot(driver, "Migrate_Userlogin_UUP_Verification");
//		Thread.sleep(8000);
//		UUP.UserUUP_Signout();

		/// 2nd part reset password - Migrated User --
		Thread.sleep(5000);
		UUP.resetpasswd(Useremail);
	}
}
