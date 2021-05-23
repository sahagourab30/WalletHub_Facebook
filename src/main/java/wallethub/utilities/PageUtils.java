package wallethub.utilities;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import wallethub.utilities.ExtentReporter;

import wallethub.base.Base_Facebook;

public class PageUtils extends Base_Facebook {

	public static long PAGE_LOAD_TIMEOUT = 20;
	public static long IMPLICIT_WAIT = 20;

	public static String readXMLValues(String filePath, String root, String tagName) {
		String value = null;
		try {
			File file = new File(filePath);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName(root);
			for (int itr = 0; itr < nodeList.getLength(); itr++) {
				Node node = nodeList.item(itr);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) node;
					value = eElement.getElementsByTagName(tagName).item(0).getTextContent();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public static String readData(String rootName, String tagName) {
        String filePath = "";
        filePath = System.getProperty("user.dir") + "//src//main//java//wallethub//testdata//TestData_WalletHub.xml";
        return readXMLValues(filePath, rootName, tagName);
    }
	
	public static boolean waitForVisible(WebElement webElement, int timeOut) {
		boolean status = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeOut);
			wait.until(ExpectedConditions.visibilityOf(webElement));
			status = webElement.isDisplayed();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return status;
		}
		return status;
	}

	public static boolean click(WebElement webElement) {
		boolean status = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(webElement));
			webElement.click();
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
			return status;
		}
		return status;
	}
	
	public static boolean sendKeys(WebElement webElement, String text) {
		boolean status = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(webElement));
			webElement.clear();
			webElement.sendKeys(text);
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static void hardAssertion(boolean status, String message) {
		if (status) {
			ExtentReporter.reportStep(driver, message, "Pass", 1);
		} else {
			ExtentReporter.reportStep(driver, message, "Fail", 1);
			Assert.assertTrue(status);
		}
	}
	
	public static boolean isPresent(WebElement webElement, int timeOut) {
		boolean status = false;
		waitForVisible(webElement, timeOut);
		try {
			status = webElement.isDisplayed();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public static void waitForLoad(int timeUnits) {
		driver.manage().timeouts().implicitlyWait(timeUnits, TimeUnit.SECONDS);
	}
}
