package org.Ex2FW;

import java.util.Date;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import Utils.Config;

public class getWeather extends base {

	@Test
	public void jira() throws Exception{


		driver = initDriver();

		String home = "https://weather.com/en-IN/weather/today/l/28.49,77.31";
		String Reciver = "skumar@ex2india.com";

		driver.get(home);
		driver.manage().window().maximize();
		String Temp = driver.findElement(By.xpath("//span[@data-testid='TemperatureValue']")).getText();
		System.out.println("Getting data");
		String Filename = "./Results/Snapshots/NWeather.png";
		Config.FullPageScreenshot(Filename);
		
		Date date = new Date();
		String Subject = date+ "- Temperature is "+Temp+"C";
		String body = "<h3>Hello<h3><h4>Weather report link is: "+home+"<h4>";
		Config.sendEmail("ex2.suraj@gmail.com", "micro4max", Reciver, Subject, body, "./Results/Snapshots/NWeather.png" );
		System.out.println("Email sent.. Thanks");
	}
}