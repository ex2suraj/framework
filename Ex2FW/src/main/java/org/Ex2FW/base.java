package org.Ex2FW;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class base {

	public static WebDriver driver;
	public Properties prop;
	public String urlname;
	
	
@Test
public WebDriver initializeDriver() throws IOException
{
	Properties prop = new Properties();
	FileInputStream fis = new FileInputStream("C:/framework/Ex2FW/src/main/java/org/Ex2FW/input.properties");
	
	
	prop.load(fis);
	String browserName=prop.getProperty("browser");
	urlname = prop.getProperty("url");
	System.out.println("URL is "+urlname);
	
	// Chrome
	if(browserName.equalsIgnoreCase("chrome"))
	{
	System.setProperty("webdriver.chrome.driver", "./exefiles/chromedriver.exe");
	driver = new ChromeDriver();
	}
	// Firefox
	else if (browserName.equalsIgnoreCase("firefox"))
	{
	System.setProperty("webdriver.gecko.driver", "./setup/geckodriver.exe");
	driver = new FirefoxDriver();
	}
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	return driver;
}

public void getScreenshot() throws IOException
{
	 try {
		 int No =(int) Math.random();
         Robot robot = new Robot();
      // It saves screenshot to desired path 
         String path = "C://BACKUP//ShotFail"+No+".png"; 
         
         String format = "jpg";
          
         Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
         BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
         ImageIO.write(screenFullImage, format, new File(path));
          
         System.out.println("A full screenshot saved!");
     } catch (Exception ex) {
         System.err.println(ex);
     }
}


@AfterTest
public void destroyDriver(){
	driver.quit();
}
}
