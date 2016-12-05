    package com.automation.main.delete_recordings;
    
   import java.util.List;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.internal.junit.ArrayAsserts;

import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;
import com.beust.jcommander.Strings;
import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

	public class TC15624ExistenceOfUIItemsDelete {

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

	      @BeforeClass
		public void setup() {
			try {


			///	System.setProperty("webdriver.ie.driver", "src/test/resources/chromedriver.exe");
				
				//driver=new FirefoxDriver();
			     driver=DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
				///ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
				
				recording_for_delete=new ArrayList<String>();
				tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
				  delete=PageFactory.initElements(driver,DeleteMenu.class);
				record = PageFactory.initElements(driver, RecordingHelperPage.class);
				copy = PageFactory.initElements(driver, CopyMenu.class);
			    move_Window= PageFactory.initElements(driver, MoveWindow.class);
			} catch (Exception e) {
				ATUReports.add("Fail Step", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			}
			
			 Date curDate = new Date();
			 String DateToStr = DateFormat.getInstance().format(curDate);
			 System.out.println("Starting the test: TC15624ExistenceOfUIItemsDelete at " + DateToStr);
			 ATUReports.add("Message window.", "Starting the test: TC15624ExistenceOfUIItemsDelete at " + DateToStr,
					 "Starting the test: TC15624ExistenceOfUIItemsDelete " + DateToStr, LogAs.PASSED, null);
			
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

	  	@AfterClass
		public void closeBroswer() {
			this.driver.quit();
		}

		// @Parameters({"web","title"}) in the future
		@Test (description="TC 15624 Existence Of UI Items Delete")
		public void test15624() throws InterruptedException {
			//1.load page
			tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
			
			//2.login as instructor
			tegrity.loginCourses("User1");// log in courses page
			initializeCourseObject();
			
			//3.Click "Recording Tasks" button (without selecting a recording)
			course.selectCourseThatStartingWith("Ab");
			
			//4.a.verify move button is grey
			record.verifyRecordingMenuColor(record.delete_button);
			
			//4.b.verify move button is disabled
			record.verifyDisabledMenu();
			
			//5.click one check box
			record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
			
			//6.un-check one check box
			record.unClickOneCheckBoxOrVerifyNotSelected(record.checkbox);
		
			//7.click all check box
			record.SelectOneCheckBoxOrVerifyAlreadySelected(record.check_all_checkbox);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			record.verifyAllCheckedboxSelected();
			
			//8.verify all check box not selected
			record.checkall.click();// make all checkboxes unmarked
			record.verifyAllCheckedboxNotSelected();
			
			//9.to delete menu
			record.getCheckbox().click();
			
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
			
			//10.verify Delete title        
			delete.verifyDeleteMenuTitle();
			
			//11.verify menu color
		    delete.verifyDeleteColor(record);
			
		    ///12.verify text
		    delete.verifyInfoText();
		   
		    //13.verify has same lists of courses in move menu and course page
		    delete.verifySelectedRecordingsInDeleteMenu(recording_for_delete);
			
		   //14.verify button locations
		    delete.verifyDeleteMenuElementsLocation();
		}

		// description = "get courses list"
		public void initializeCourseObject() throws InterruptedException {

			course = PageFactory.initElements(driver, CoursesHelperPage.class);
			course.courses = course.getCoursesListFromElement(course.course_list);
		}
	}

	
