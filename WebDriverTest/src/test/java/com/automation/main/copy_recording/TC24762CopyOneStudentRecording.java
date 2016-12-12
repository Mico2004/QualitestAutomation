package com.automation.main.copy_recording;

import java.awt.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.PlayerPage;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;

import java.text.DateFormat;
import java.util.Date;
import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import junit.framework.Assert;

public class TC24762CopyOneStudentRecording {
	// Set Property for ATU Reporter Configuration
		{
			System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

		}

		public LoginHelperPage tegrity;
		public CoursesHelperPage course;
		public RecordingHelperPage record;
		public PlayerPage player_page;
		WebDriver driver;
		WebDriverWait wait;
		public static WebDriver thread_driver;
		CopyMenu copy;
		DesiredCapabilities capability;
		String os;
		List recording_list_object;
		ConfirmationMenu confirm;
		MoveWindow move_menu;
		
		@BeforeClass
		public void setup() {
		

				System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
				driver = new ChromeDriver();///// MUST FOR TEST TO GET XML
				
				tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
				wait = new WebDriverWait(driver, 30);
				 Date curDate = new Date();
				 String DateToStr = DateFormat.getInstance().format(curDate);
				 System.out.println("Starting the test: TC24762CopyOneStudentRecording at " + DateToStr);
				 ATUReports.add("Message window.", "Starting the test: TC24762CopyOneStudentRecording at " + DateToStr,
				 "Starting the test: TC24762CopyOneStudentRecording at " + DateToStr, LogAs.PASSED, null);
			
			
			
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

		@Test (description="TC 24762 Copy One Student Recording")
		public void test24762() throws InterruptedException {
			// 1.load main page chrome
			tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
			// 2.login as instructor
			tegrity.loginCourses("User1");// log in courses page
			driver.navigate().to("https://" + driver.getCurrentUrl().split("/")[2] + "/api/courses/Active");
			Thread.sleep(1000);
			String xml_source_code = driver.findElement(By.tagName("body")).getText();
			driver.quit();

			// 1.load main page chrome
			System.setProperty("webdriver.edge.driver", "src/test/resources/MicrosoftWebDriver.exe");
			//		capability=DesiredCapabilities.internetExplorer();
			//		capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,false);
			//		
			driver=DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
			player_page = PageFactory.initElements(driver, PlayerPage.class);
			confirm = PageFactory.initElements(driver, ConfirmationMenu.class);
			tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
			
			// 2.login as instructor
			tegrity.loginCourses("User1");// log in courses page
			// initialize course
			initializeCourseObject();
			// 4.select first course
			course = PageFactory.initElements(driver, CoursesHelperPage.class);
			//course.selectFirstCourse(record);
			String course_name=course.selectCourseThatStartingWith("Ab");
				
			record.waitForVisibility(record.first_recording);
			//4.1  Click the 'Student Recording' tab
	    	record.clickOnStudentRecordingsTab();
			record.waitForVisibility(record.first_recording);
		
			//5.take recorder namme for later 
			String original_recorder_name = driver.findElement(By.id("RecordedBy1")).getText();/// take recorder namme for later 
			// 6.verify check box is selected and then load copy menu
			record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
        	record.verifyCopyMenu();// verify copy menu
       
			//7.return to recording page and than to course page
			copy.clickOnCancelButton(record);
			Thread.sleep(2000);
			
			record.returnToCourseListPage();
			//8. Verify Only courses where this USER signed as INSTRUCTOR are displayed in "Course List"

			course.first_course_button.click();
			record.waitForVisibility(record.student_recordings_tab);
			record.clickOnStudentRecordingsTab();
			Thread.sleep(3000);
			
			record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
			record.verifyCopyMenu();// verify copy menu
			Thread.sleep(3000);
			
			copy.copy_course_list = copy.getStringFromElement(copy.course_list);
			int course_number=copy.course_list.size(); 
			int count_instructors= course.patternAppearenceinString(xml_source_code,"<CurrentUserRole>Instructor</CurrentUserRole>");
			if(count_instructors==course_number)
			{
				for(int i=0;i<copy.copy_course_list.length;i++)
				{
					boolean result=copy.searchWordInString(copy.copy_course_list[i],xml_source_code);
					if(result==false)
					{
						Assert.assertTrue(false);
						System.out.println("xml courses name not equals move menu courses names");
						ATUReports.add("xml courses name not equals move menu courses names","xml file" ,"num of instructors:"+count_instructors,"num of instructors:"+course_number, LogAs.FAILED, null);         

					}
				}
				Assert.assertTrue(true);
				System.out.println("number Of count_instructors role equals Number Of courses");
				ATUReports.add("all courses assigned as instructors","xml file" ,"num of instructors:"+count_instructors,"num of instructors:"+course_number, LogAs.PASSED, null);        
				// driver.navigate().back();
				Thread.sleep(2000);

			}
			else
			{
				Assert.assertTrue(false);
				System.out.println("number Of count_instructors role not equals Number Of courses");
				ATUReports.add("all courses assigned as instructors","xml file" ,"num of instructors:"+count_instructors,"num of instructors:"+course_number, LogAs.FAILED, null);         

			}



			//9.take recorder name for later 
			String destination_course_name = copy.course_list.get(1).getText();
			System.out.println(copy.course_list.get(1).getText());
			
			//11.select target course
			copy.selectTargetCourseFromCourseList(copy.course_list.get(1).getText());
			
			//12.verify background color of copy menu
			System.out.println(copy.getBackGroundColor(driver.findElement(By.xpath("//*[@id=\"courseListSelect\"]/option[2]"))));
			copy.verifyBlueColor(copy.getBackGroundColor(driver.findElement(By.xpath("//*[@id=\"courseListSelect\"]/option[2]")))); 
			
			//13.Click the "Copy Recording(s)" button
			copy.clickOnCopyButton(record);// click copy button ConfirmationMenu
		
			//14.click on ok button
			confirm.clickOnOkButton();// click on ok button String
			
			//15.verify "being copy from" status
			record.waitForVisibility(record.course_being_copied_status);
			String status = record.course_being_copied_status.getText();
			confirm.verifyCopySourceRecordingStatus(status); ///

			record.checkStatusExistenceForMaxTTime(360);

			//16.retuen to course page
			record.returnToCourseListPage();/// return to course list page
	
			//17.click on course and go to recording page
			course.course_list.get(1).click();
			record.waitForVisibility(record.student_recordings_tab);
			
			///click on student tab
			record.clickOnStudentRecordingsTab();
			record.waitForVisibility(record.first_recording);
			String first_recording = driver.findElement(By.id("Recording1")).getText();
			
			
			//fill recording list with data :duration,date,names,recorder names
			record.convertRecordingsListToDuration();
			record.convertRecordingsListToDate();
			record.convertRecordingsListToNames();	
			record.convertRecordingsListToRecorderName();
		
			//18.Click on source recording's title
			record.verifyRecordingDisplayedCorrectly(first_recording);


			//19.verify recording is expandable
			Thread.sleep(3000);

			record.verifyFirstExpandableRecording();
			record.clickOnTheFirstCaptherWithOutTheExpand();
			//20.dispaly recording
			player_page.verifyTimeBufferStatusForXSec(15);// check source display

			///// to go back to crecording window handler
			for (String handler : driver.getWindowHandles()) {
				driver.switchTo().window(handler);
				// System.out.println("=========================================");
				// System.out.println(driver.getPageSource());
			}
			//21.Click "Courses" link at breadcrumbs
			player_page.returnToCoursesPage(course);
		
			//click on courses
			//course.course_list.get(1).click();
			course.selectCourseThatStartingWith(course_name);

            record.waitForVisibility(record.student_recordings_tab);
			record.clickOnStudentRecordingsTab();
            //22.verify date,duration,recorder name as original
        	
			//fill recording list with data :duration,date,names,recorder names
			record.convertRecordingsListToDuration();
			record.convertRecordingsListToDate();
			record.convertRecordingsListToNames();	
			record.convertRecordingsListToRecorderName();
			
			
			record.verifyDate(driver.findElement(By.id("RecordingDate1")).getText());
			String dur = driver.findElement(By.id("RecordingLength2")).getText();
			dur = record.trimDuration(dur);
			record.verifyTimeDuration(dur);
			record.VerifyRecorderNameAsOriginal(original_recorder_name);
			//23.bold font for recording title
			Thread.sleep(2000);
			record.recordingBoldFont(record.student_recordings_tab);/// verify bold style in recording title

			//24.verify sorting by date ,title and duration

			record.pressViewButtonAndSelect("Title");

			record.convertRecordingsListToNames();
			Thread.sleep(1000);
			record.verifyRecordingSortedByTitle(record.recording_list_names);
			record.pressViewButtonAndSelect("Date");
			Thread.sleep(1000);

			record.convertRecordingsListToDate();/// check sort by date
			record.verifyRecordingSortedByDate(record.recordings_list_date_string);
			record.pressViewButtonAndSelect("Duration");
			Thread.sleep(1000);

			record.convertRecordingsListToDuration();/// check sort by date
			record.verifyRecordingSortedByDuration(record.recording_list_duration_string);

			//25.sign-out
			record.signOut();// sign out
			//26.login courses as student
			tegrity.loginCourses("User2");// log in courses page

			//initialize course object
			initializeCourseObject();
			
			
			//27.Select destination course
			course.selectCourseByName(destination_course_name);
			record.waitForVisibility(record.student_recordings_tab);
			record.clickOnStudentRecordingsTab();
			
			//28.select recording by name
			record.convertRecordingsListToNames();
		///	record.selectRecordingByName(first_recording);
			
			//29."Recording Chapters" are expanded
			Thread.sleep(3000);
			String target_recording = record.getFirstRecordingTitle();
			record.clickOnTargetRecordingAndOpenItsPlayback(target_recording);
			
			///30.Click any chapter:verify plays correctly 
			player_page.verifyTimeBufferStatusForXSec(5);// check source display

			System.out.println("Done.");
			ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
			
		}

		// description = "get courses list"
		public void initializeCourseObject() throws InterruptedException {

			course = PageFactory.initElements(driver, CoursesHelperPage.class);
			course.courses = course.getCoursesListFromElement(course.course_list);
			course.size = course.course_list.size();
		}
}
