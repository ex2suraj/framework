package Utils;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;

import org.Ex2FW.base;
import org.apache.commons.io.FileUtils;
import org.frontendtest.components.ImageComparison;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;


	public class ImageHelper extends base
	{
		public enum RespectiveToElement
		{
			None, AboveElement, BelowElement, LeftToElement, InsideAnElement
		}
		
		/**
		 * This function is used to verify if UI is distorted or not by comparing screenshot with passed image.
		 * @param testConfig
		 * @param originalImage - Image which is to be used as base image for comparison (Put it in folder : Project/Resources/ImageComparision folder)
		 */
		public static void verifyPageIsNotDistorted(String originalImage, boolean image, WebDriver driver)
		{
			System.out.println("Inside method");
			verifyUIDistortion(originalImage, null, RespectiveToElement.None, image);
		}
		
		public static void verifyPageIsNotDistorted(String originalImage)
		{
			verifyUIDistortion(originalImage, null, RespectiveToElement.None, true);
		}
		
		
		/**
		 * This function is used to verify if UI, below a given Element(including Element)
		 * @param testConfig
		 * @param originalImage - Image which is to be used as base image for comparison (Put it in folder : Project/Resources/ImageComparision folder)
		 */
		public static void verifyPageIsNotDistortedBelowAnElement( String originalImage, WebElement element)
		{
			verifyUIDistortion(originalImage, element, RespectiveToElement.BelowElement, true);
		}
		
		
		/**
		 * This function is used to verify if UI, below a given Element(excluding Element)
		 * @param testConfig
		 * @param originalImage - Image which is to be used as base image for comparison (Put it in folder : Project/Resources/ImageComparision folder)
		 */
		public static void verifyPageIsNotDistortedAboveAnElement( String originalImage, WebElement element)
		{
			verifyUIDistortion(originalImage, element, RespectiveToElement.AboveElement, true);
		}
		
		
		/**
		 * This function is used to verify if UI, left to a given Element(including Element)
		 * @param testConfig
		 * @param originalImage - Image which is to be used as base image for comparison (Put it in folder : Project/Resources/ImageComparision folder)
		 */
		public static void verifyPageIsNotDistortedLeftToElement( String originalImage, WebElement element)
		{
			verifyUIDistortion( originalImage, element, RespectiveToElement.LeftToElement, true);
		}
		
		
		/**
		 * This function is used to verify if UI, InsideAnElement with grace of 10 pixels
		 * @param testConfig
		 * @param originalImage - Image which is to be used as base image for comparison (Put it in folder : Project/Resources/ImageComparision folder)
		 */
		public static void verifyPageIsNotDistortedInsideAnElement( String originalImage, WebElement element)
		{
			verifyUIDistortion(originalImage, element, RespectiveToElement.InsideAnElement, true);
		}
		

		/**
		 * This function is used to compare screenshot below any specific Element (If Element=null then will compare full image)
		 * @param testConfig
		 * @param originalImage - Image which is to be used as base image for comparison (Put it in folder : Project/Resources/ImageComparision folder)
		 * @param element
		 * @param compareAboveThisElement - Pass true to compareAboveThisElement
		 */
		private static void verifyUIDistortion(String originalImage, WebElement element, RespectiveToElement respectiveToElement, boolean imageType)
		{
			boolean result = false;
			String randomString = randomAlphaNumeric(5);
			String ResultsDir = "C:\\framework\\Ex2FW\\Results";
			String originalImagePath = System.getProperty("user.dir") + File.separator + "Results" + File.separator + originalImage;
			
			/*
			 * value of expected path is./Ex2FW/Results\ImageComparision\7DCBA_expectedImage_C:\framework\Ex2FW\Results\map_background.jpg
				value of actual path is./Ex2FW/Results\ImageComparision\7DCBA_actualImage_C:\framework\Ex2FW\Results\map_background.jpg
				value of output path is./Ex2FW/Results\ImageComparision\7DCBA_outputImage_C:\framework\Ex2FW\Results\map_background.jpg
			 */
			
			String expectedImagePath = new String(ResultsDir + File.separator + "ImageComparision" + File.separator + randomString + "_expectedImage_" + originalImage.split("[.]")[0] + ".jpg");
			String actualImagePath = new String(ResultsDir + File.separator + "ImageComparision" + File.separator + randomString + "_actualImage_" + originalImage.split("[.]")[0] + ".jpg");
			String outputImagePath = new String(ResultsDir + File.separator + "ImageComparision" + File.separator + randomString + "_outputImage_" + originalImage.split("[.]")[0] + ".jpg");
			
			System.out.println("value of expected path is" +expectedImagePath);
			System.out.println("value of actual path is" +actualImagePath);
			System.out.println("value of output path is" +outputImagePath);
			System.out.println("value of original image is " +originalImagePath);
			
			try
			{
				if(imageType==true) {
					//Get Screenshot from Browser (Full or Partial)
				File screenshotFilePath = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				if(element != null)
				{
					switch(respectiveToElement)
					{
						case AboveElement:
							screenshotFilePath = cropScreenshotAboveAnElement(screenshotFilePath, element);
							break;
						case BelowElement:
							screenshotFilePath = cropScreenshotBelowAnElement( screenshotFilePath, element);
							break;
						case LeftToElement:
							screenshotFilePath = cropScreenshotLeftToAnElement( screenshotFilePath, element);
							break;
						case InsideAnElement:
							screenshotFilePath = cropScreenshotInsideAnElement( screenshotFilePath, element);
							break;
					default:
						break;
					}
				}
				FileUtils.copyFile(screenshotFilePath, new File(actualImagePath));
				}
				else if(imageType==false) {
					
					
				
					// Taking the Full page screenshot using Ashot Library function when paramter is passed as "false"
					//((JavascriptExecutor) driver).executeScript("jQuery('.stickyHeader,.sticky-header,.stayInformedFormStickyHeader12').css('position', 'static');");
					
					// Added JS method to fix the full page scroll issue
					JavascriptExecutor js = (JavascriptExecutor) driver;
					Long windowHeight = (Long) js.executeScript("return document.body.scrollHeight");
					int numberOfPixelsToDragTheScrollbarDown = 400, i = 0;
					do {
					js.executeScript("window.scrollTo( " + i + ", " + (i+numberOfPixelsToDragTheScrollbarDown) + ")");
					i += numberOfPixelsToDragTheScrollbarDown;
					} while (i <= windowHeight);

					System.out.println("Taking snapshot");
					BufferedImage img = Shutterbug.shootPage(driver, ScrollStrategy.BOTH_DIRECTIONS, 100).getImage();
					
					// Writing the captured Image to the file and Checking if the output directory exist or not
					File dir = new File(System.getProperty("user.dir") + File.separator +actualImagePath);
					if(dir.isDirectory()){
						//ImageIO.write(img,"PNG", new File(System.getProperty("user.dir") + File.separator +actualImagePath));
						ImageIO.write(img,"PNG", new File(File.separator +actualImagePath));
					}else{
						new File(ResultsDir + File.separator +"ImageComparision" + File.separator).mkdirs();
						ImageIO.write(img,"PNG", new File(File.separator +actualImagePath));
					}
				}
				
				//Compare both the images
				ImageComparison imageComparison = new ImageComparison(15, 15, 0.05);
				result = imageComparison.fuzzyEqual(originalImagePath, actualImagePath, outputImagePath);
				
	/*			What it does (Simplified Flow):
					1. First the two images that should be compared are divided in squares with the width and height (e.g. 10, 10 here), that you defined in the constructor.
					2. For every square in each image an average RGB-Value is calculated.
					3. If the average RGB-Values of the corresponding squares differ more than the threshold that you defined in the constructor (0.05 = 5%), the function fuzzyEqual(…) will return false.
					4. If you passed a path to save an image with the found differences a copy will be save at this path with all the differences marked with red squares.
				Note: The sensitivity of the fuzzy-equal-test will be influenced by the defined threshold as well as the size of the squares!
	*/
				
				if(result)
				{
					//testConfig.logPass("User Interface verified successfully. No Distortion found in : " + originalImage);
					//testConfig.logComment("<B>Verified Image</B>:- <a href=" + Browser.getResultsURLOnRunTime(testConfig, outputImagePath) + " target='_blank' >" + "click here to view Verified User Interface" + "</a>");
					FileUtils.forceDelete(new File(actualImagePath));
					System.out.println("PASS ,Images are matching");
				}
				else
				{
					System.out.println("FAIL, Images are not matching.. Plz check !!");
					//Log.failure("UI of CurrentPage is different from the " + originalImage + " Image", testConfig);
					//Copying expectedImagePath from local to Results folder(in order to make it visible in failure logs)
				
					try {	
						Files.copy(Paths.get(originalImagePath), Paths.get(expectedImagePath), StandardCopyOption.REPLACE_EXISTING);
							assert 1>2; 
							
					} catch (IOException e) {e.printStackTrace();}
					
					//testConfig.logComment("<B>UI Distortion Results</B>:- <a href=" + Browser.getResultsURLOnRunTime(testConfig, outputImagePath) + " target='_blank' >" + "click here to view Output Image of UI Distortion" + "</a>");
					//testConfig.logComment("<B>Expected Screenshot</B>:- <a href=" + Browser.getResultsURLOnRunTime(testConfig, expectedImagePath) + " target='_blank' >" + "click here to view Expected Image of User Interface" + "</a>");
					//testConfig.logComment("<B>Current Screenshot</B>:- <a href=" + Browser.getResultsURLOnRunTime(testConfig, actualImagePath) + " target='_blank' >" + "click here to view Actual Image of User Interface" + "</a>");
				}
			} 
			catch (IOException e) 
			{
				//testConfig.logFail("*******Exception while verifying the User Interface*******");
				e.printStackTrace();
			}
		
		}
		
		/**
		 * This function is used to crop the screenshot blow an element (including the element)
		 * @param testConfig
		 * @param screenshotFilePath
		 * @param element
		 * @return
		 */
		private static File cropScreenshotBelowAnElement( File screenshotFilePath, WebElement element)
		{
			try
			{
				BufferedImage  fullPagePhoto = ImageIO.read(screenshotFilePath);
				Point point = element.getLocation();
				
				int startPoint_X = 0;
				int widthFromStartPoint_X = fullPagePhoto.getWidth();
				
				int startPoint_Y = point.getY();
				int heightFromStartPoint_Y = fullPagePhoto.getHeight() - point.getY();
				
				BufferedImage croppedPhoto= fullPagePhoto.getSubimage(startPoint_X, startPoint_Y, widthFromStartPoint_X, heightFromStartPoint_Y);
				ImageIO.write(croppedPhoto, "png", screenshotFilePath);
				System.out.println("Image is cropped Successfully...");
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			return screenshotFilePath;
		}
		
		/**
		 * This function is used to crop the screenshot above an element (excluding the element)
		 * @param testConfig
		 * @param screenshotFilePath
		 * @param element
		 * @return
		 */
		private static File cropScreenshotAboveAnElement(File screenshotFilePath, WebElement element)
		{
			try
			{
				BufferedImage  fullPagePhoto = ImageIO.read(screenshotFilePath);
				Point point = element.getLocation();
				
				int startPoint_X = 0;
				int widthFromStartPoint_X = fullPagePhoto.getWidth();
				
				int startPoint_Y = 0;
				int heightFromStartPoint_Y = point.getY();
				
				BufferedImage croppedPhoto= fullPagePhoto.getSubimage(startPoint_X, startPoint_Y, widthFromStartPoint_X, heightFromStartPoint_Y);
				ImageIO.write(croppedPhoto, "png", screenshotFilePath);
				System.out.println("Image is cropped Successfully...");
			}
			catch (IOException e) 
			{
		
				e.printStackTrace();
			}
			return screenshotFilePath;
		}
		
		
		
		/**
		 * This function is used to crop the screenshot above an element (including the element)
		 * @param testConfig
		 * @param screenshotFilePath
		 * @param element
		 * @return
		 */
		private static File cropScreenshotLeftToAnElement(File screenshotFilePath, WebElement element)
		{
			try
			{
				BufferedImage  fullPagePhoto = ImageIO.read(screenshotFilePath);
				Point point = element.getLocation();
				int widthOfElement = element.getSize().width;
				
				int startPoint_X = 0;
				int widthFromStartPoint_X = point.getX() + widthOfElement + 20;
				
				int startPoint_Y = 0;
				int heightFromStartPoint_Y = fullPagePhoto.getHeight();
				
				BufferedImage croppedPhoto= fullPagePhoto.getSubimage(startPoint_X, startPoint_Y, widthFromStartPoint_X, heightFromStartPoint_Y);
				ImageIO.write(croppedPhoto, "png", screenshotFilePath);
				System.out.println("Image is cropped Successfully...");
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			return screenshotFilePath;
		}
		
		
		/**
		 * This function is used to crop the screenshot inside an element
		 * @param testConfig
		 * @param screenshotFilePath
		 * @param element
		 * @return
		 */
		private static File cropScreenshotInsideAnElement(File screenshotFilePath, WebElement element)
		{
			try
			{
				BufferedImage  fullPagePhoto = ImageIO.read(screenshotFilePath);
				Point point = element.getLocation();
				
				int startPoint_X = point.getX() - 10;
				int widthFromStartPoint_X = element.getSize().width + 20;
				
				int startPoint_Y = point.getY() - 10;
				int heightFromStartPoint_Y = element.getSize().height + 20;
				
				BufferedImage croppedPhoto= fullPagePhoto.getSubimage(startPoint_X, startPoint_Y, widthFromStartPoint_X, heightFromStartPoint_Y);
				ImageIO.write(croppedPhoto, "png", screenshotFilePath);
				System.out.println("Image is cropped Successfully...");
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			return screenshotFilePath;
		}
		
		public static void setWindowSize( int width, int height)
		{
			driver.manage().window().maximize();
			Dimension fullScreen = driver.manage().window().getSize();
			
			//testConfig.logComment("FullScreen Size of Browser : "+fullScreen.toString());
			
			if(height == -1)
				height = fullScreen.getHeight();
			
			if(width == -1)
				width = fullScreen.getWidth();
			
			driver.manage().window().setPosition(new Point(0,0));
			driver.manage().window().setSize(new Dimension(width, height));
			
			Dimension after = driver.manage().window().getSize();
			//testConfig.logComment("Custom Size of Browser : "+after.toString());
			System.out.println("After size is "+after.toString());
		}
		
		private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		public static String randomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
		int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
		builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
		}
	}

