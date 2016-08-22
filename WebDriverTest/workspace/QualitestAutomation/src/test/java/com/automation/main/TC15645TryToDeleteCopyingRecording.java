package com.automation.main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
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
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC15645TryToDeleteCopyingRecording {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	public MoveWindow move_window;
	public DeleteMenu delete_menu;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	String currentCourse;
	String targetCourse;
	String clickedRecording;
    DesiredCapabilities capability;
	
    
    @BeforeClass
	public void setup() throws InterruptedException {

		
		 driver=DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		 ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

		driver.manage().window().maximize();
		ATUReports.setWebDriver(driver);
	
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		course=PageFactory.initElements(driver, CoursesHelperPage.class);
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);

		

	
    
    }
	
	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getStringFromElement(course.course_list);
	}

	
	@Test( description = "Login course page")
	public void loginCourses() throws InterruptedException
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		// 1. Login as INSTRUCTOR.
		tegrity.loginCourses("User1");// log in courses page
		Thread.sleep(2000);
	

		// 2. Select course.
		currentCourse = course.selectCourseThatStartingWith("Ab");
		System.out.println("Current course: " + currentCourse);
		//course.selectCourse(record);
		
		// 3. Select one recording.
	 	record.waitForVisibility(record.checkbox);
	    int index=record.clickCheckBoxByName("Anatomy Head & neck 3");
	 	String recording_for_delete="Anatomy Head & neck 3";
	 	//4.to copy window
	 	copy.verifyCopyMenu(record);
	 	
	 	//5.select destination course
	 	copy.selectTargetCourseFromCourseListThatStartWith("abc");
	 	
	 	//6.click on copy button
	 	 copy.clickOnCopyButton();
	 	
	 	//7.click on ok button
	 	 confirm_menu.clickOnOkButtonAfterConfirmCopyRecording();
	 	 
	 	 //8.Selected recording has a "Being copied from" status
	 	
	 	 boolean status=record.hasBeingcopiedfromStatus(driver.findElement(By.id("RecordingStatus"+Integer.toString(index))));
	 	
	 	 //9.While recording is being copied, select "Recording Tasks -> Delete" menu item
	 	 if(status==true)
	 	 {
	 		 record.toDeleteMenu();
	 	
	 		//10.Message box "The following recording(s) could not be deleted:" is displayed
	 		 if(delete_menu.info_text.getText().equals("The following recording(s) cannot be deleted:"))
	 		 {
	 			System.out.println("The following recording(s) could not be deleted info appears" );
	 			 Assert.assertTrue(true);
	 	    	ATUReports.add(" check info text","text","The following recording(s) could not be deleted info appears",delete_menu.info_text.getText(),LogAs.PASSED,null);
	 		 }
	 		 else {
		 			System.out.println("bad info status" );
		 	    	ATUReports.add(" check info text","text","The following recording(s) could not be deleted info appears",delete_menu.info_text.getText(),LogAs.FAILED,null);

	 			 Assert.assertTrue(false);
	 		
	 		 }
	 	 }	 
	 		//11.click cancel
	 		 delete_menu.clickOnCancelButton();
	 		 
	 		 //12.Verify that selected recording isn't deleted
             String source_record=record.recording_list_names.get(index-1);
            Assert.assertEquals(source_record, recording_for_delete);
            if(source_record.equals(recording_for_delete))
            {
            	System.out.println("recording wasnt deleted");
	 	    	ATUReports.add("check if recording was deleted","recording name","recording wasnt deleted","recording wasnt deleted",LogAs.PASSED,null);
            }
            else {
            	System.out.println("recording was deleted");
	 	    	ATUReports.add("check if recording was  deleted","recording name","recording wasnt deleted","recording was deleted",LogAs.PASSED,null);

            }
           //13.Click "Courses" link in the breadcrumbs
            record.returnToCourseListPage();
            //14.Select destination course
            course.selectCourseThatStartingWith("abc");
            //15.While destination recording is being copied, select "Recording Tasks -> Delete" menu item	Message box "The following recording(s) could not be deleted:" is displayed
            record.waitForVisibility(record.checkbox);
    		record.checkbox.click();
    		 boolean status2=record.hasMovingCopyingStatus(record.first_recording_status);
    		 	
    	 	 //16.While recording is moving/copying, select "Recording Tasks -> Delete" menu item
    	 	 if(status2==true)
    	 	 {
    	 		 record.toDeleteMenu();
    	 	String val=driver.findElement(By.xpath("//*[@id=\"alertWindow\"]/div[1]/p")).getText();
    	 		//17.Message box "The following recording(s) could not be deleted:" is displayed
    	 		 if(val.contains("The following recording(s) could not be deleted: Anatomy Head & neck 3"))
    	 		 {
    	 			System.out.println("The following recording(s) could not be deleted: Anatomy Head & neck 3" );
    	 			 Assert.assertTrue(true);
    	 	    	ATUReports.add("check info text","text","The following recording(s) could not be deleted: Anatomy Head & neck 3","The following recording(s) could not be deleted: Anatomy Head & neck 3",LogAs.PASSED,null);
    	 		 }
    	 		 else {
    		 			System.out.println("bad info status" );
    		 	    	ATUReports.add(" check info text","text","The following recording(s) could not be deleted: Anatomy Head & neck 3",val,LogAs.FAILED,null);

    	 			 Assert.assertTrue(false);
    	 		
    	 		 }
    	 	 }	 
		
	 	driver.quit(); 
	}
}
