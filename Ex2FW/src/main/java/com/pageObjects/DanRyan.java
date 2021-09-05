package com.pageObjects;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DanRyan {
	
	
	WebDriver driver;											// Local driver for this class
	
	public DanRyan(WebDriver driver){
		this.driver = driver;									// WebDriver is assigned to the local driver
		PageFactory.initElements(driver, this);		//Invoke the objects of this class
	}

	@FindBy (name="firstname")	WebElement firstname;
	@FindBy (xpath="//div[2]/div[1]/div/a/img") WebElement siteLogo;
	@FindBy (xpath="//a[@href='/communities/' and @aria-expanded='false']") WebElement communities_menuLink;
	@FindBy (xpath="//h1[. = 'Dan Ryan Builders New Home Communities']") WebElement DRCommunities_pageTitle;
	@FindBy (xpath="//h3/a[. = 'Anderson Grant']") WebElement AndersonGrantComm; 
	
	
	// Methods
	
	
	public void NavigateToCommPage(){
		communities_menuLink.click();
		String Actual = DRCommunities_pageTitle.getText();
		assertTrue(Actual.contains("Dan Ryan Builders New Home Communities"));
	}
	
	public void verifyWebsiteLogo() throws InterruptedException{
		assertTrue(siteLogo.isDisplayed());
	}
	
	public void OpenAndersonComm(){
		String actual = AndersonGrantComm.getText();
		assertTrue(actual.contains("Anderson Grant"));
		AndersonGrantComm.click();
	}
}
