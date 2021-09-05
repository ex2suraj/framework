package org.Ex2FW;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class KioskStatus extends base {

	@Test
	public void testResponsecode() throws InterruptedException, IOException
	{
	
		String URL = "https://builderhomesite.atlassian.net/wiki/spaces/EX2PWEBAP/pages/409600414/Kiosk+Clients+-+All+instances";
		String user = "skumar@builderhomesite.com";
		String pass = "$Superman";
		String home = "https://builderhomesite.atlassian.net/wiki/home";
		String Loopurl = "";
		
		driver = initDriver();
		driver.get(home);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.MINUTES);
		WebElement name = driver.findElement(By.id("username"));
		name.sendKeys(user);
		name.submit();
		WebElement password = driver.findElement(By.id("password"));
		password.sendKeys(pass);
		password.submit();
		//driver.manage().window().setSize(new Dimension(1024,768));
		Thread.sleep(9000);
		driver.get(URL);
		
		List<WebElement> links = driver.findElements(By.tagName("a"));
        
        Iterator<WebElement> it = links.iterator();
        
        while(it.hasNext()){
            
            Loopurl = it.next().getAttribute("href");
            
            System.out.println(Loopurl);
        }  
    //System.out.println(" All links done --->>");
	Response resp = RestAssured.get(URL);
	int code = resp.getStatusCode();
	//String data = resp.getBody().prettyPrint();
	System.out.println("Status is "+code+" > "+URL);
	System.out.println("Response time in milisec :"+resp.getTime());
	Assert.assertEquals(code, 200);
	}
}
