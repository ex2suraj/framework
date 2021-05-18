package org.Ex2FW;

import java.io.IOException;
import org.testng.annotations.Test;

import Utils.ImageHelper;

public class Test3 extends base{

	@Test
	public void FirstRun() throws IOException, InterruptedException{
		driver = initializeDriver();
		System.out.println("value of my name is "+urlname);
		driver.get("https://www.google.com");
		driver.manage().window().maximize();
		String originalImage = "map_background.jpg";
		ImageHelper.verifyPageIsNotDistorted(originalImage,false,driver);
		
	}
}
