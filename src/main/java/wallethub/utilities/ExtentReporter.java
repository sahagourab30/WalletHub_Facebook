package wallethub.utilities;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.NetworkMode;


public class ExtentReporter{

	protected static ExtentTest test;
	protected static ExtentReports extent;
	private static DateFormat dateFormat;
	private static Date date;
	private static String dt;
	public static String extentReportPath;
	private static String extentReport, reportFolder;
	private static HashMap testStatusDetail;
	static Map extentTestMap = new HashMap();
	static Map testMap = new HashMap();
	
	public synchronized static ExtentReports startExtentReport(String suiteName) {
		try {	
			
				dateFormat = new SimpleDateFormat("MMM-dd_HH-mm");
				date = new Date();
				dt = dateFormat.format(date);				
				reportFolder = suiteName + "_" + dt;
				extentReportPath = System.getProperty("user.dir") +"/test-output/";
				extentReport= reportFolder + "/" + suiteName + "_Report_" + dt + ".html";
				System.out.println(extentReportPath+"reports/extent" + "/" + reportFolder);
				extent = new ExtentReports(extentReportPath+"/reports/extent" + "/" + extentReport, false, NetworkMode.ONLINE);			
				testStatusDetail = new HashMap();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return extent;
	}
	
	public synchronized ExtentTest startTestCase(String testName) {

		test = getReporter().startTest(testName, testName);
		return test;
	}


	@SuppressWarnings("unchecked")
	public synchronized static void reportStep(WebDriver driver, String desc, String status, int scFlag){
		try{
			if (status.toUpperCase().equals("PASS")) {
				if (scFlag > 0) {
					getTest().log(LogStatus.PASS, desc + getTest().addScreenCapture("Screenshots/" + getScreenShot(driver) + ".jpg"));
					testStatusDetail.put(getTestName(), "-");
				}
				else {
					getTest().log(LogStatus.PASS, desc);
					testStatusDetail.put(getTestName(), "-");
				}
			} else if (status.toUpperCase().equals("FAIL")) {
				if (scFlag > 0) {
					getTest().log(LogStatus.FAIL, desc + getTest().addScreenCapture("Screenshots/" + getScreenShot(driver) + ".jpg"));
					testStatusDetail.put(getTestName(), desc);
				}
				else
				{
					getTest().log(LogStatus.FAIL, desc);
					testStatusDetail.put(getTestName(), "-");
				}
			}else if (status.toUpperCase().equals("INFO")) {
				if (scFlag > 0) {
					getTest().log(LogStatus.INFO, desc + getTest().addScreenCapture("Screenshots/" +getScreenShot(driver) + ".jpg"));
					testStatusDetail.put(getTestName(), "-");
				}
				else
				{
					getTest().log(LogStatus.INFO, desc);
					testStatusDetail.put(getTestName(), "-");
				}	
			}
		} catch (Exception e) {
			try {
				throw (e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	private static long getScreenShot(WebDriver driver) throws IOException{
		long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L;
		try {
			FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
			 new File(extentReportPath+"reports/extent" + "/" + reportFolder+ "/Screenshots/" + number + ".jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return number;
	}
	
	public synchronized static void endExtentReport() {      
	        endTest();
	        getReporter().flush();
	}

	private synchronized static ExtentReports getReporter() {
		if(extent==null)
			extent=startExtentReport("WalletHub");
		return extent;
	}
	
	private static synchronized ExtentTest getTest() {
        return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    }
	private static synchronized String getTestName() {
        String testName= (String) testMap.get((int) (long) (Thread.currentThread().getId()));
        return testName;
    }
	
	 private synchronized static void endTest() {
		 getReporter().endTest((ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId())));
	    }

	    @SuppressWarnings("unchecked")
		public synchronized static ExtentTest startExtentTestReport(String testName, String desc) {
	        test = getReporter().startTest(testName, desc);
	        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
	        testMap.put((int) (long) (Thread.currentThread().getId()), testName);
	        
	        return test;
	    }
}