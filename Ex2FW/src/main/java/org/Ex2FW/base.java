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

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class base {

	public static WebDriver driver;
	public Properties prop;
	public static String urlname;
	
	
@Test
public static WebDriver initDriver() throws IOException
{
	Properties prop = new Properties();
	FileInputStream fis = new FileInputStream("./src/main/java/org/Ex2FW/input.properties");
	
	
	prop.load(fis);
	String browserName=prop.getProperty("browser");
	try {
		urlname = prop.getProperty("url");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//System.out.println("URL is "+urlname);
	
	// Chrome
	if(browserName.equalsIgnoreCase("chrome"))
	{
	WebDriverManager.chromedriver().setup();
	//System.setProperty("webdriver.chrome.driver", "./exefiles/chromedriver.exe");
	driver = new ChromeDriver();
	}
	// Firefox
	else if (browserName.equalsIgnoreCase("firefox"))
	{
	WebDriverManager.firefoxdriver().setup();
	//System.setProperty("webdriver.gecko.driver", "./exefiles/geckodriver.exe");
	driver = new FirefoxDriver();
	}
	// Microsoft Edge
	else if (browserName.equalsIgnoreCase("edge"))
	{
	WebDriverManager.edgedriver().setup();
	//System.setProperty("webdriver.gecko.driver", "./exefiles/edgedriver.exe");
	driver = new EdgeDriver();
	}
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	System.out.println("Browser is launched, OK");
	return driver;
}

public void CaptureScreenshot(String path) throws IOException
{
	 try {
		 //int No =(int) Math.random();
         Robot robot = new Robot();
         // It saves screenshot to desired path 
         //String path = "./Results/Snapshots/"+name+".png"; 
         
         String format = "jpg";
          
         Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
         BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
         ImageIO.write(screenFullImage, format, new File(path));
          
         System.out.println("A full screenshot saved! at "+path);
     } catch (Exception ex) {
         System.err.println(ex);
     }
}

public void ScrollPagedown(String value){
	 ((JavascriptExecutor)driver).executeScript("window.scrollBy(0,"+value+")","");
}


@AfterTest
public void destroyDriver() {
	//driver.quit();
}
}
