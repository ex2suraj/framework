package org.Ex2FW;

import java.io.File;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import Utils.Config;

public class jiratickets extends base {

	@Test
	public void jira() throws Exception{
		
		
		driver = initDriver();
		
		String URL = "https://builderhomesite.atlassian.net/issues/?filter=29313";
		String user = "skumar@builderhomesite.com";
		String pass = "$Superman";
		String home = "https://builderhomesite.atlassian.net/wiki/home";
		
		driver.get(home);
		//driver.manage().timeouts().implicitlyWait(2, TimeUnit.MINUTES);
		WebElement name = driver.findElement(By.id("username"));
		name.sendKeys(user);
		name.submit();
		WebElement password = driver.findElement(By.id("password"));
		password.sendKeys(pass);
		password.submit();
		driver.manage().window().setSize(new Dimension(1024,768));
		WebDriverWait w = new WebDriverWait(driver,10);
		w.until(ExpectedConditions.urlToBe(home));
		driver.get(URL);
		String count = driver.findElement(By.xpath("//span[@class='results-count-end']")).getText();
		File src = driver.findElement(By.xpath("//div[@class='list-view']")).getScreenshotAs(OutputType.FILE);
		
		Date date = new Date();
		//String filename = date.toString().replace(":", "-").replace(" ", "_")+".png";
		//String filename1 = date.toString().replace(":", "-").replace(" ", "_")+".png";
		
		String FilePath = "./Results/Snapshots/IMG_JiraTickets.png";
		File DestFile=new File(FilePath);
		FileUtils.copyFile(src, DestFile);
		String Subject = count+" Pending tickets on "+date;
		System.out.println(Subject);
		String body = "<h3>Hello<h3><h4>Jira filter: "+URL+"<h4>";
		Config.sendEmail("ex2.suraj@gmail.com", "micro4max", "skumar@ex2india.com", Subject, body, FilePath);
		System.out.println("Email sent.. Thanks");
		driver.close();
	}
}