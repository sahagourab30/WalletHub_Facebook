package wallethub.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import wallethub.base.Base_Facebook;
import wallethub.utilities.ExtentReporter;
import wallethub.utilities.PageUtils;

public class FB_HomePage extends Base_Facebook {
	
	@SuppressWarnings("static-access")
	public FB_HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//span[contains(text(),\"What's on your mind\")]")
	WebElement postTextField;
	
	@FindBy(xpath = "//div[contains(@aria-label,\"What's on your mind\")]")
	WebElement postTextArea;
	
	@FindBy(xpath = "//span[text()='Post']")
	WebElement postButton;
	
	@FindBy (xpath = "//a[@aria-label='Home'][1]")
	WebElement homeIcon;
	
	
	public void postMessage(String postDetails) {
		try {
		PageUtils.waitForVisible(postTextField, 10);
		PageUtils.click(postTextField);
		PageUtils.waitForVisible(postTextArea, 10);
		PageUtils.sendKeys(postTextArea, postDetails);
		PageUtils.click(postButton);
		ExtentReporter.reportStep(driver, "Entered message '"+postDetails+"' and clicked on Post button.", "Pass", 1);
		PageUtils.waitForLoad(15);
		PageUtils.hardAssertion(PageUtils.isPresent(postTextField, 5), "Message posted successfully");
		}catch (Exception e) {
			ExtentReporter.reportStep(driver, "Post unsuccessful", "Fail", 1);
			ExtentReporter.reportStep(driver, e.getMessage(), "Fail", 0);
		}
	}
}
