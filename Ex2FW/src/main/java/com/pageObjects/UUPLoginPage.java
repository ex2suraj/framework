package com.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import Utils.Config;

public class UUPLoginPage {

	WebDriver driver;

	public UUPLoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "signin")
	WebElement UUPLoginBtn;
	@FindBy(xpath = "//a[text()='Sign Up']")
	WebElement SignUptab;
	@FindBy(xpath = "//a[text()='Log In']")
	WebElement LogIntab;
	@FindBy(id = "1-FirstName")
	WebElement firstname;
	@FindBy(id = "1-LastName")
	WebElement lastname;
	@FindBy(name = "submit")
	WebElement submitBtn;
	@FindBy(id = "auth0-lock-error-msg-email")
	WebElement emailerror;
	@FindBy(id = "auth0-lock-error-msg-password")
	WebElement passwderror;
	@FindBy(xpath = "//small[@class='auth0-lock-terms']")
	WebElement terms;
	@FindBy(xpath = "//a[@class='auth0-lock-alternative-link']")
	WebElement resetpasswdLink;
	@FindBy(id = "1-email")
	WebElement email;
	@FindBy(name = "password")
	WebElement password;
	@FindBy(xpath = "//span[@class='auth0-label-submit']")
	WebElement sendEmailbtn;
	@FindBy(xpath = "//div[@class='auth0-global-message auth0-global-message-success']/span")
	WebElement resetSuccessMsg;
	@FindBy(id="signout") WebElement SignOutLink;

	
	@FindBy(id="login") WebElement yopemail;
	@FindBy(xpath="//button[@class='md but text f18 #6666CC']") WebElement showPictures;
	@FindBy(xpath="//a[text()='click here']") WebElement clickHereLink;
	
	public void CheckUUPSetUp() throws Exception {
		if (UUPLoginBtn.isEnabled() == true) {
			System.out.println("UUP Login is configured, OK");
		} else {
			Assert.fail();
		}
		UUPLoginBtn.click();
	}

	public void ValidateEmailandPasswd() {
		// Validation 1
		String expectedemailerrmsg = "Email can't be blank";
		String actualemailerrmsg = emailerror.getText();
		System.out.println(actualemailerrmsg);
		Assert.assertEquals(actualemailerrmsg, expectedemailerrmsg);

		// Validation 2
		String expectedpasswdrmsg = "Password can't be blank";
		String actualpasswdrmsg = passwderror.getText();
		System.out.println(actualpasswdrmsg);
		Assert.assertEquals(actualpasswdrmsg, expectedpasswdrmsg);
	}

	public void VerifySignUp() {
		SignUptab.click();
		firstname.clear();
		firstname.sendKeys("test");
		submitBtn.click();

		ValidateEmailandPasswd();

		// validation 3
		String termsAndC = terms.getText();
		System.out.println(termsAndC);
		String expectedTerms = "By signing up, you agree to our terms of service and privacy policy.";
		Assert.assertEquals(termsAndC, expectedTerms);
	}

	public void VerifyLogIN() {
		// Validation 1 + Validation 2
		LogIntab.click();
		submitBtn.click();
		ValidateEmailandPasswd();
	}

	public void resetpasswd(String resetemail) throws Exception {
		CheckUUPSetUp();
		// Switch to POPUP window
		String Main_window = driver.getWindowHandle();
		for (String popupwindow : driver.getWindowHandles()) {
			driver.switchTo().window(popupwindow);
		}
		resetpasswdLink.click();
		email.clear();
		email.sendKeys(resetemail);
		sendEmailbtn.click();
		Thread.sleep(4000);
		Config.takeSnapshot(driver, "ResetPasswd_Verfication");
		String expected = "WE'VE JUST SENT YOU AN EMAIL TO RESET YOUR PASSWORD.";
		Assert.assertEquals(resetSuccessMsg.getText(), expected);
		driver.switchTo().window(Main_window);
	}

	public void doResetYopmail(String email) throws Exception {
		yopemail.clear();
		yopemail.sendKeys(email);
		Thread.sleep(3000);
		showPictures.click();
		Config.takeSnapshot(driver, "ResetPasswdEmail_Verfication");
	}
	
	public void CreateNewUUPLogin(String fname, String lname, String Useremail, String Userpass) throws Exception {
		try {
			SignUptab.click();
		} catch (Exception s) {
			System.out.println("Already on the SignUP tab");
		}
		email.clear();
		email.sendKeys(Useremail);
		password.sendKeys(Userpass);
		firstname.sendKeys(fname);
		lastname.sendKeys(lname);
		Config.takeSnapshot(driver, "NewAccount_UUP_Verification");
		submitBtn.click();
	}

	public void UserUUPLogin(String Useremail, String Userpass) throws Exception {
		CheckUUPSetUp();
		// Switch to POPUP window
		String Main_window = driver.getWindowHandle();
		for (String popupwindow : driver.getWindowHandles()) {
			driver.switchTo().window(popupwindow);
		}
		try {
			LogIntab.click();
		} catch (Exception e) {
			System.out.println("Already on the login tab");
		}
		email.clear();
		email.sendKeys(Useremail);
		password.clear();
		password.sendKeys(Userpass);
		Config.takeSnapshot(driver, "UserLogin_UUP_Verification");
		submitBtn.click();
	}
	
	public void UserUUP_Signout() {
			SignOutLink.click();
	}
}
