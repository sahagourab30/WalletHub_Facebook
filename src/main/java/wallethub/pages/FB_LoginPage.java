package wallethub.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import wallethub.base.Base_Facebook;
import wallethub.utilities.ExtentReporter;
import wallethub.utilities.PageUtils;

public class FB_LoginPage extends Base_Facebook {
	
	@SuppressWarnings("static-access")
	public FB_LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id = "email")
	WebElement emailTextField;
	
	@FindBy (id = "pass")
	WebElement passwordTextField;
	
	@FindBy(xpath = "//button[@name='login']")
	WebElement loginButton;
	
	@FindBy (xpath = "//a[@aria-label='Home'][1]")
	WebElement homeIcon;
	
	public void insertEmailID(String email) {
		try {
		PageUtils.waitForVisible(emailTextField, 10);
		PageUtils.sendKeys(emailTextField, email);
		ExtentReporter.reportStep(driver, "Email is inserted", "Pass", 1);
		}catch (Exception e) {
			ExtentReporter.reportStep(driver, "Email is not inserted", "Fail", 1);
			ExtentReporter.reportStep(driver, e.getMessage(), "Fail", 0);
		}
	}
	
	public void insertPassword(String password) {
		try {
		PageUtils.waitForVisible(passwordTextField, 10);
		PageUtils.sendKeys(passwordTextField, password);
		ExtentReporter.reportStep(driver, "Password is inserted", "Pass", 1);
		}catch (Exception e) {
			ExtentReporter.reportStep(driver, "Password is not inserted", "Fail", 1);
			ExtentReporter.reportStep(driver, e.getMessage(), "Fail", 0);
		}
	}
	
	public void clickOnLogin() {
		try {
			PageUtils.waitForVisible(loginButton, 10);
			PageUtils.click(loginButton);
			if(PageUtils.isPresent(homeIcon, 20))
				PageUtils.hardAssertion(true, "Login Successfull");
			else
				PageUtils.hardAssertion(false, "Username or Password is incorrect");
		}catch(Exception e) {
			ExtentReporter.reportStep(driver, "Clicking on Login Button Failed", "Fail", 1);
			ExtentReporter.reportStep(driver, e.getMessage(), "Fail", 0);
		}
	}

}
