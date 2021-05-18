package org.Ex2FW;

import java.io.IOException;
import org.testng.annotations.Test;

import com.pageObjects.LandingPage;

public class Test1 extends base{

	@Test
	public void FirstRun() throws IOException{
		driver = initializeDriver();
		System.out.println("value of my name is "+urlname);
		driver.get(urlname);
		LandingPage l = new LandingPage(driver);
		l.enterFirstname().sendKeys("hello");
		l.enterLastname().sendKeys("lastword");
	}
}
