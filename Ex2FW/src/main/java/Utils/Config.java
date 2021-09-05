package Utils;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import javax.mail.*;  
import javax.mail.internet.*;  
import javax.activation.*;
import javax.imageio.ImageIO;

import org.Ex2FW.base;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Config extends base {
	
	public static void takeSnapshot(WebDriver driver, String SnapName) throws Exception{
        //Convert web driver object to TakeScreenshot
		
		Thread.sleep(1000);
		TakesScreenshot scrShot =((TakesScreenshot)driver);
		Date date = new Date();
		String datename = date.toString().replace(":", "-").replace(" ", "_");
		//Call getScreenshotAs method to create image file
		File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
		//Move image file to new destination
		File DestFile=new File("./Results/Snapshots/"+SnapName+"__"+datename+".png");
		//Copy file at destination
		FileUtils.copyFile(SrcFile, DestFile);

    }

	public static void captureSnap(File src) throws IOException {
		Date date = new Date();
		String filename = date.toString().replace(":", "-").replace(" ", "_")+".png";
		//String filename1 = date.toString().replace(":", "-").replace(" ", "_")+".png";
		
		String FilePath = "./Results/Snapshots/IMG_"+filename;
		File DestFile=new File(FilePath);
		FileUtils.copyFile(src, DestFile);
	}
	
	
	public static void FullPageScreenshot(String filepath) throws HeadlessException, AWTException, IOException
	{
		// This code will capture screenshot of current screen		
		BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		
		// This will store screenshot on Specific location
		ImageIO.write(image, "png", new File(filepath)); 
	}

/*	public static void takeSnapshot(WebDriver driver, String SnapName,WebElement locater) throws Exception{
		//Convert web driver object to TakeScreenshot

		TakesScreenshot scrShot =((TakesScreenshot)driver);

		//Call getScreenshotAs method to create image file
		TakesScreenshot locator = null;
		File SrcFile = locator.getScreenshotAs(OutputType.FILE);
		//Move image file to new destination
		File DestFile=new File("./Results/Snapshots/"+SnapName+".png");
		//Copy file at destination
		FileUtils.copyFile(SrcFile, DestFile);

    }*/

	public static void sendEmail(final String from,final String passwd, String To,String Subject, String HtmlBody, String filePath) throws MessagingException, IOException{
		
		String host = "smtp.gmail.com";
		//1) get the session object     
		Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host );
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.trust", host);
        props.put("mail.smtp.port", "587");
		  
		  Session session = Session.getDefaultInstance(props,  
		   new javax.mail.Authenticator() {  
		   protected PasswordAuthentication getPasswordAuthentication() {  
		   return new PasswordAuthentication(from,passwd);  
		   }  
		  });  
		     
		  //2) compose message     
		    MimeMessage message = new MimeMessage(session);  
		    message.setFrom(new InternetAddress(from));  
		    message.addRecipient(Message.RecipientType.TO,new InternetAddress(To));  
		    message.setSubject(Subject);  
		      
		    // This mail has 2 part, the BODY and the embedded image
	         MimeMultipart multipart = new MimeMultipart("related");
	        
	         // first part (the html)
	         BodyPart messageBodyPart = new MimeBodyPart();
	         String htmlText = HtmlBody+"<img src='cid:image'>";
	         messageBodyPart.setContent(htmlText, "text/html");
	         // add it
	         multipart.addBodyPart(messageBodyPart);
	        
	         
	        // String [] file = filePath;
	        // for (String i:file)
	         
	         // 2nd part (the image)
	         messageBodyPart = new MimeBodyPart();
	         DataSource fds = new FileDataSource(filePath);
	         messageBodyPart.setDataHandler(new DataHandler(fds));
	         messageBodyPart.setHeader("Content-ID", "<image>");
	         
	         // add image/images to the multipart
	         multipart.addBodyPart(messageBodyPart);
	         // put everything together
	    
	 
	        message.setContent(multipart);
	 
	        Transport.send(message);
		
		
	    }
}