package wallethub.tests;

import java.lang.reflect.Method;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import wallethub.utilities.ExtentReporter;
import wallethub.utilities.PageUtils;
import wallethub.base.Base_Facebook;
import wallethub.pages.FB_HomePage;
import wallethub.pages.FB_LoginPage;

public class HomePageTests extends Base_Facebook {
	
	FB_HomePage homepage;
	FB_LoginPage loginPage;
	
	@BeforeMethod(alwaysRun = true)
	public void setUp(Method method) {
		ExtentReporter.startExtentTestReport((this.getClass().getSimpleName() + "::" + method.getName()),
				"Test Result");
		//initialization();
		homepage = new FB_HomePage(driver);
		loginPage = new FB_LoginPage(driver);
	}
	
	@Test(groups = {"HomePage"})
	public void loginTest() {
		String emailId = PageUtils.readData("Credentials", "EmailId");
		String password = PageUtils.readData("Credentials", "Password");
		String postDetails = PageUtils.readData("Texts", "PostMessage");
		loginPage.insertEmailID(emailId);
		loginPage.insertPassword(password);
		loginPage.clickOnLogin();
		homepage.postMessage(postDetails);
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}
}
