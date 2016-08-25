package com.automation.main;

import java.awt.AWTException;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;



import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import junitx.util.PropertyManager;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TestUIExixtencePage1 {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	DesiredCapabilities capability;
	@BeforeClass
	public void setup() {
try{
	
	///capability=DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());///create remote driver 
//	capability.setPlatform(Platform.WIN8_1);
	///driver=DriverSelector.setDriverOs(PropertyManager.getProperty("OS"),capability);//choose os
///	
	driver=new FirefoxDriver();
	ATUReports.add("selected browser type", LogAs.PASSED,
				  new CaptureScreen( ScreenshotOf.DESKTOP));
		
		
		
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
}
catch(Exception e)
{
	ATUReports.add("Fail Step", LogAs.FAILED,
			  new CaptureScreen( ScreenshotOf.DESKTOP));
}

}

	/*
	 * @Test public void testNewLogs() throws AWTException, IOException {
	 * 
	 * ATUReports.add("INfo Step", LogAs.INFO, new CaptureScreen(
	 * ScreenshotOf.BROWSER_PAGE)); ATUReports.add("Pass Step", LogAs.PASSED,
	 * new CaptureScreen( ScreenshotOf.DESKTOP)); /// WebElement element =
	 * driver /// .findElement(By.xpath("/html/body/div/h1/a")); ATUReports.add(
	 * "Warning Step", LogAs.WARNING, new CaptureScreen(element));
	 * ATUReports.add("Fail step","" ,"",LogAs.FAILED, new CaptureScreen(
	 * ScreenshotOf.DESKTOP)); }
	 */
	private void setAuthorInfoForReports() {
		ATUReports.setAuthorInfo("McGrawHill Automation ", Utils.getCurrentTime(), "1.0");
	}

	private void setIndexPageDescription() {
		ATUReports.indexPageDescription = "Mcgeawhill Verify <br/> <b> UI existence</b>";
		
	}

	@Test
	public void testME() {
		setAuthorInfoForReports();
		setIndexPageDescription();
	}

	// @Parameters({"web","title"}) in the future
	@Test
	public void loadPage() throws InterruptedException {
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

	}

	@Test(dependsOnMethods = "loadPage", description = "Login course page")
	public void loginCourses() throws InterruptedException//
	{
		tegrity.loginCourses("Instructor");// log in courses page
		initializeCourseObject();
	}

	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getStringFromElement(course.course_list);
	}

	@Test(dependsOnMethods = "loginCourses", description = "login recording page")
	public void selectCourse() throws InterruptedException {
		course.selectSecondCourse(record);
	}

	@Test(dependsOnMethods = "selectCourse", description = "verify copy button color is grey") //
	public void verifyRecordingMenuColor() throws InterruptedException {

		record.verifyRecordingMenuColor();

	}

	@Test(dependsOnMethods = "verifyRecordingMenuColor", description = "verify copy button  is disabled") //
	public void verifyDisabledMenu() throws InterruptedException {

		record.verifyDisabledMenu();
	}

	@Test(dependsOnMethods = "verifyDisabledMenu", description = "verify  1 check boxes checkboxes are selected")
	public void verifyCheckedboxSelected() throws InterruptedException {

		record.ClickOneCheckedboxSelected(record.getCheckbox());

	}

	@Test(dependsOnMethods = "verifyCheckedboxSelected", description = "verify  many check boxes checkboxes are selected")
	public void verifyCheckedboxNotSelected() throws InterruptedException {

		record.ClickOneCheckedboxNotSelected(record.getCheckbox());
	}

	@Test(dependsOnMethods = "verifyCheckedboxNotSelected", description = "verify  many check boxes checkboxes are selected") //
	public void verifyAllCheckedboxSelected() throws InterruptedException {

		record.checkall.click();// make all checkboxes marked
		Thread.sleep(2000);
		record = PageFactory.initElements(driver, RecordingHelperPage.class);

		record.verifyAllCheckedboxSelected();

	}

	@Test(dependsOnMethods = "verifyAllCheckedboxSelected", description = "verify  many check boxes checkboxes are selected")
	public void verifyAllCheckedboxNotSelected() {
		/// driver.navigate().refresh();
		record.checkall.click();// make all checkboxes unmarked

		/// record.checkall.click();// make all checkboxes unmarked
		record.verifyAllCheckedboxNotSelected();

	}

	@Test(dependsOnMethods = "verifyAllCheckedboxNotSelected", description = "verify  copy windows is displayed")
	public void verifyCopyMenu() throws InterruptedException {

		copy.verifyCopyMenu(record);

	}

	@Test(dependsOnMethods = "verifyCopyMenu", description = "verify  copy title")
	public void verifyCopyMenuTitle() throws InterruptedException {
		copy.verifyCopyMenuTitle();

	}

	@Test(dependsOnMethods = "verifyCopyMenuTitle", description = "verify background color is as university color")
	public void verifyMenuColor() throws InterruptedException {
		Thread.sleep(2000);
        copy.verifyMenuColor(record);

	}

	@Test(dependsOnMethods = "verifyMenuColor", description = "verify info text")
	public void verifyInfoText() throws InterruptedException {
		copy.verifyInfoText();

	}

	@Test(dependsOnMethods = "verifyInfoText", description = "verify courses in course page are also in copy menu")
	public void verifyCoursesInCopyMenu() throws InterruptedException {
		copy.verifyCoursesInCopyMenu(course);

	}

	@Test(dependsOnMethods = "verifyCoursesInCopyMenu", description = "verify  course search box ")
	public void verifySearchCourseBox() {

		copy.verifySearchCourseBox();
	}

	@Test(dependsOnMethods = "verifySearchCourseBox", description = "verify course search box text ")
	public void verifySearchCourseBoxText() {

		copy.verifySearchCourseBoxText();
	}


}
