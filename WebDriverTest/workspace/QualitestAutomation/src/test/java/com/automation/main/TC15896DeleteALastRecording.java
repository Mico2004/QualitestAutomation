package com.automation.main;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import junit.framework.Assert;

public class TC15896DeleteALastRecording {


	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
   public DeleteMenu delete;
	public MoveWindow move_Window;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	DesiredCapabilities capability;
    String os;

    public List <String>  recording_for_delete;
	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
      @BeforeClass
	public void setup() {
		try {


		///	System.setProperty("webdriver.ie.driver", "src/test/resources/chromedriver.exe");
			
			
		    driver=DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			///ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
			driver.manage().window().maximize();
			recording_for_delete=new ArrayList<String>();
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			  delete=PageFactory.initElements(driver,DeleteMenu.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
		    move_Window= PageFactory.initElements(driver, MoveWindow.class);
		} catch (Exception e) {
			ATUReports.add("Fail Step", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
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

		@Test
		public void deleteALastRecording() throws InterruptedException {
		//presetting
			//1.load page
			tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
			///tegrity.loginCourses("SuperUser");// log in courses page
			
	
			//2.login as instructor
			tegrity.loginCourses("User1");// log in courses page
			initializeCourseObject();
			//3.Click "Recording Tasks" button (without selecting a recording)
			course.selectCourseThatStartingWith("Ab");
            Thread.sleep(2000);
			//4.to delete menu
			record.checkbox.click();//first
			record.checkboxlist.get(1).click();//second
			//list all recording names to check which one is selected and should appear in delete menu
			record.convertRecordingsListToNames();
		    int counter=0;
			for(WebElement element:record.checkboxlist)
			{ 
				if(element.isSelected())
				{
					recording_for_delete.add(record.recording_list_names.get(counter));
				}
			counter++;
			}
			record.toDeleteMenu();
			
		
		   //5.verify has same lists of courses in move menu and course page
		     delete.verifySelectedRecordingsInDeleteMenu(recording_for_delete);
		     //6.click ond delete
		     delete.clickOnDeleteButton();
		     //7.initialize new recording list to find if selected recording were deleted
		     record.recording_list_names=null;
		     record.convertRecordingsListToNames();
		     ///8.check recordings were deleted
		     for(String recorded:recording_for_delete)
		     {
		    	 if(record.recording_list_names.contains(recorded))
		    	 {
		    		 System.out.println("recording_for_delete was not deleted ");
		    	   	 ATUReports.add("check if selected recording was deleted","select checkboxes","recording dont appear","recording appears", LogAs.FAILED, null);
		    		 Assert.assertTrue(false);
		    	 return;
		    	 }
		    	 
		    	
		    		
		    		 
				}
		            System.out.println("recording for delete were deleted successfully");
    	   	        ATUReports.add("check if selected recording was deleted","select checkboxes","recording dont appear","recording dont appear", LogAs.PASSED, null);
			        Assert.assertTrue(true); 
		}
		     
		
		     // description = "get courses list"
			public void initializeCourseObject() throws InterruptedException {

				course = PageFactory.initElements(driver, CoursesHelperPage.class);
				course.courses = course.getStringFromElement(course.course_list);
			}

}