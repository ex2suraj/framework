package org.Ex2FW;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.annotations.Test;


public class Test4 extends base{

	//Testcase for MI Homes - Kiosk //
	@Test
	public void FirstRun() throws IOException, InterruptedException{
		driver = initializeDriver();
		System.out.println("value of my name is "+urlname);
		String url = "https://charlotte-harlowscrossing.mikiosks.com/";
		driver.get(url);
		driver.manage().window().maximize();
		driver.findElement(By.xpath("//body/a/img")).isDisplayed();
		System.out.println("Check image");
		driver.findElement(By.xpath("//li[2]//span[2]")).click();
		Thread.sleep(9000);
	}
}
