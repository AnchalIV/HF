package Login;

import org.jboss.aerogear.security.otp.Totp;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;



import Generic.Generic_method;

public class Login_page {
	Generic_method gm;
	@FindBy(xpath = "//a[@class='btn admin_btn btn-sm']")
	private WebElement UserLogin;
	@FindBy(xpath = "//input[@type='text']")
	private WebElement Email;
	@FindBy(xpath = "//input[@type='password']")
	private WebElement Password;
	@FindBy(xpath = "//button[text()='Login']")
	private WebElement login;
	@FindBy(xpath="//*[@placeholder='Please enter your verification code.']")                                
	private WebElement FAKey;
	@FindBy(xpath="//*[contains(text(),'Verify') and contains(text(),'Proceed')]")
    private WebElement verifyProceed;
	public Login_page(Generic_method gm) {
		this.gm = gm;
		PageFactory.initElements(gm.getDriver(), this);
	}

	public void userLogin() {
		gm.click(UserLogin);
	}
	public void hfValidLogin() {
		
		gm.sendkeysFromProp(Email, "emailid");
		gm.sendkeysFromProp(Password, "password");
		gm.click(login);
		
		  Totp totp = new Totp(gm.getPropData("secretkey")); 
		  String twoFactorCode = totp.now();
		  gm.holdOn(3);
		  
		  gm.sendkeys(FAKey, twoFactorCode);
		  gm.click(verifyProceed);
		  gm.holdOn(2);
		  gm.ScreenShot();
		  System.out.println(gm.getTitle());
		  
		
		
	}
}






























//boolean actual = gm.isDisplayed(StaySignIn);
//if (actual == true) {
//	gm.click(StaySignIn);
