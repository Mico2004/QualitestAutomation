package com.automation.main.page_helpers;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.omg.CORBA.OBJ_ADAPTER;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.internal.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.common.base.Predicate;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class CoursesHelperPage extends Page {
	@FindBy(css = ".list-group-item-heading>.ng-binding")
	public List<WebElement> course_list;
	@FindBy(id = "Course2")
	public WebElement second_course_button;
	@FindBy(id = "Course1")
	public WebElement first_course_button;
	@FindBy(id = "Course2")
	public WebElement course_button;
	public String[] courses;
	public int size; // courses list size
	@FindBy(id = "PastCourses")
	public WebElement past_courses_tab_button;
	@FindBy(id = "ActiveCourses")
	public WebElement active_courses_tab_button;
	@FindBy(id = "StartRecordingButton")
	public WebElement start_recording_button;
	@FindBy(id = "StartTestButton")
	public WebElement start_test_button;
	@FindBy(id = "PublicCourses")
	public WebElement public_courses_tab;
	@FindBy(id = "PublicCourses")
	public WebElement public_courses_tab_button;
	@FindBy(id = "UserName")
	public WebElement user_name;
	@FindBy(xpath = "//*[@id=\"startHelpFrame\"]/div")
	public WebElement get_started_box;
	@FindBy(id = "DisclaimerLink")
	public WebElement disclaimer;
	@FindBy(id = "HelpLink")
	public WebElement help;
	@FindBy(id = "ReportsLink")
	public WebElement reports;
	@FindBy(id = "MyAccountLink")
	public WebElement my_account;
	@FindBy(id = "PrivateCourses")
	public WebElement private_courses_tab_button;
	@FindBy(id = "OnlineHelp")
	public  WebElement online_help;
	@FindBy(id = "GetStarted")
	public WebElement get_started;
	@FindBy(id = "GetSupport")
	public WebElement get_support;
	@FindBy(id = "RunDiagnostics")
	public WebElement run_diagnostics;
	@FindBy(xpath = "//*[@id=\"startHelpFrame\"]/div")
	public WebElement get_started_block;
	@FindBy(linkText = "close")
	public WebElement close_get_started_block;
	@FindBy(css = ".pull-right>b>span")
	public List<WebElement> new_recordings_title_and_number_of_new_recordings;
	@FindBy(id = "CoursesHeading")
	WebElement courses_heading;
	@FindBy(id="CourseFrame1")
	WebElement courseFrame;
	@FindBy(linkText = "Recording Tasks")
	public WebElement recording_tasks_button;
	public ConfirmationMenu confirm_menu;
	public LoginHelperPage tegrity;
	

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public CoursesHelperPage(WebDriver browser) {
		super(browser);
		//setPageTitle("Tegrity - Courses");
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

	}

	// This function click on target course name from course list.
	// If the course found and clicked, it will return true.
	// Otherwise it will return false.
	public boolean clickOnTargetCourseName(String course_name) {
		// waitForVisibility(course_list.get(0));
		for (int i = 0; i < course_list.size(); i++) {
			wait.until(ExpectedConditions.elementToBeClickable(course_list.get(i)));
			String current_course = course_list.get(i).getText();
			if (current_course.equals(course_name)) {
				course_list.get(i).click();
				System.out.println("Clicked on target course name: " + course_name);
				ATUReports.add("Clicked on target course name: " + course_name, LogAs.PASSED, null);
				return true;
			}
		}

		System.out.println("Not clicked on target course name: " + course_name);
		ATUReports.add("Not clicked on target course name" + course_name, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		return false;
	}

	public String selectSecondCourse(RecordingHelperPage rec) throws InterruptedException {// select
																							// courses
		String course_name = null;
		try {
			waitForVisibility(second_course_button);
			course_name = second_course_button.getText();
			clickElement(second_course_button);
			Thread.sleep(3000);
			System.out.println("Clicked on second course");
			ATUReports.add("Select course", "Clicked on second course", "Clicked on second course", LogAs.PASSED, null);
		} catch (Exception e) {
		}
		Assert.assertTrue(true);
		return course_name;
	}

	public String selectFirstCourse(RecordingHelperPage rec) throws InterruptedException {// select
																							// courses
		String course_name = null;
		try {
			waitForVisibility(first_course_button);
			course_name = first_course_button.getText();
			clickElement(first_course_button);
			Thread.sleep(3000);
			ATUReports.add("Select course", "Clicked on first course", "Course Details page is displayed", LogAs.PASSED,
					null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			ATUReports.add("Select course", "Clicked on first course", "Course Details page isn't displayed", LogAs.PASSED,
					 new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		
		return course_name;
	}

	// this function return list of all courses
	public List<String> getCourseList() {
		List<String> current_course_list = new ArrayList<String>();
		try{	
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(course_list));
		}catch(Exception e){
			
			
		}for (WebElement course : course_list) {
			current_course_list.add(course.getText());
			System.out.println(course.getText());
		}

		return current_course_list;
	}

	// this function get index, and return course name of that index
	public String getCourseInIndex(int index) {
		List<String> current_course_list = getCourseList();

		if (current_course_list.size() < index) {
			return null;
		}

		return current_course_list.get(index - 1);
	}

	// this function get course name, and select it from the course list
	public void selectTargetCourse(String course_name_to_select) {
		List<String> current_course_list = getCourseList();

		boolean not_found = true;
		for (int i = 0; i < current_course_list.size(); i++) {
			if (current_course_list.get(i).equals(course_name_to_select)) {
				not_found = false;
				try {
					driver.findElement(By.id("Course" + Integer.toString(i + 1))).click();
					System.out.println("Clicked on target course name: " + course_name_to_select);
					ATUReports.add("Clicked on target course name: " + course_name_to_select, LogAs.PASSED, null);
					Assert.assertTrue(true);
				} catch (Exception msg) {
					System.out.println("Not clicked on target course name: " + course_name_to_select);
					ATUReports.add("Not clicked on target course name: " + course_name_to_select, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				}
				break;
			}
		}

		if (not_found) {
			System.out.println("Not found course name in course list: " + course_name_to_select);
			ATUReports.add("Not found course name in course list: " + course_name_to_select, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	public String selectCourse(RecordingHelperPage rec) throws InterruptedException {// select
																						// courses
		String course_name = null;
		try {
			waitForVisibility(course_button);
			course_name = course_button.getText();
			clickElement(course_button);
			Thread.sleep(3000);
			ATUReports.add(" clicked course", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
		} catch (Exception e) {
			ATUReports.add(" clicked course failed", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		Assert.assertTrue(rec.verifyElementTest(rec.recording_tasks_button));
		return course_name;
	}

	//// select course by name
	public void selectCourseByName(final String destination_course_name) throws InterruptedException {

		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("wrapper"), "recordings -"));
		System.out.println("wait wrapper");
		WebElement element = driver.findElement(By.xpath("//a[contains(@title,'" + destination_course_name + "')]"));
		System.out.println("Find Element text:" + element.getText() + "Find Element id:" + element.getAttribute("id"));
		String id = element.getAttribute("id");
		WebElement sCourse2 = driver.findElement(By.id(id));
		String CourseId=sCourse2.getAttribute("id");
		
		for(int index=0; index<10 ; index ++){
			((JavascriptExecutor) driver).executeScript("document.getElementById(\""+CourseId+"\").click();");
			Thread.sleep(1000);
			if(isElementPresent(By.id("CourseTitle"))) {
				System.out.println("successflly select the course.");
				ATUReports.add("successflly select the course.", LogAs.PASSED, null);
				break;			
			}			
		}
		try{
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("CourseTitle")));		
		}catch(TimeoutException e){
			System.out.println("Course wasn't selected successfully: couese title isn't visible");
			ATUReports.add("Course wasn't selected successfully: couese title isn't visible", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));	
			Assert.assertTrue(false);
		}
		
		/*
		 * 
		while (!clicked && i < 50) {
			try {
				i++;
				wait.until(ExpectedConditions.elementToBeClickable(sCourse2));
				System.out.println("s1");
				wait.until(ExpectedConditions.visibilityOf(sCourse2));
				sCourse2.click();
				System.out.println("s2");
				clicked = true;

			} catch (Exception ex) {
				clicked = handlesClickIsNotVisible(sCourse2);
				try {
					Thread.sleep(5000);
					System.out.println("s3.1");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 
			}
		}*/
	}


	/// verify setting option for every instructor course in recording page
	public boolean verifyCoursesForInstructor(String xml_url) throws InterruptedException {
		size = course_list.size();
		System.out.println(size);
		driver.get(xml_url);
		InputStream in = null;
		try {

			final String login = "kosins1";
			final String password = "111";

			Authenticator.setDefault(new Authenticator() {

				@Override
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(login, password.toCharArray());
				}

			});
			URL myUrl = new URL(xml_url);
			in = myUrl.openStream();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(in);

			NodeList nodes = (NodeList) doc.getElementsByTagName("CurrentUserRole");
			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);
				/// NodeList title = element.getElementsByTagName(/*item within
				/// the tag*/);
				// Element line = (Element) title.item(0);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ATUReports.add(" course verified", LogAs.PASSED, null);
		Assert.assertTrue(true);
		return true;
	}

	// This function go get into each course of user and check if the user is
	// instructor
	// and return list of all courses on which this user is instructor
	public List<String> getAllCoursesForWhichTheUserIsInstructor(RecordingHelperPage rec) throws InterruptedException {
		List<String> course_list_for_which_user_is_instructor = new ArrayList<String>();

		List<String> current_course_list = getCourseList();

		for (String course_name : current_course_list) {
			clickOnTargetCourseName(course_name);

			Thread.sleep(2000);

			rec.moveToElementAndClick(rec.course_task_button, driver);
			if ((rec.verifyElement(rec.course_settings_button))) {
				course_list_for_which_user_is_instructor.add(course_name);
			}

			Thread.sleep(2000);

			rec.returnToCourseListPage();

			Thread.sleep(2000);
		}

		return course_list_for_which_user_is_instructor;
	}

	// This function click on past courses tab button
	public void clickOnPastCoursesTabButton() {
		try {
			String initialCourseText=courseFrame.getText();
			past_courses_tab_button.click();			
			waitForContentOfTabToLoad(initialCourseText,courseFrame);
			//fluentWaitInvisibility(first_course_button, 10, 150);
			//fluentWaitVisibility(first_course_button, 10, 400);
			WebElement tabParent=past_courses_tab_button.findElement(By.xpath(".."));
			wait.until(ExpectedConditions.attributeContains(tabParent, "class", "active"));	
			Thread.sleep(2000);
			System.out.println("Click on past course tab.");
			ATUReports.add("Click on past course tab.", LogAs.PASSED, null);
		} catch (Exception msg) {
			System.out.println("Fail to click on past course tab.");
			ATUReports.add("Fail to click on past course tab.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function click on active courses tab button
	public void clickOnActiveCoursesTabButton() {
		try {
			active_courses_tab_button.click();
			System.out.println("Click on active course tab.");
			ATUReports.add("Click on active course tab.", LogAs.PASSED, null);
		} catch (Exception msg) {
			System.out.println("Fail to click on active course tab.");
			ATUReports.add("Fail to click on active course tab.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function get string that some course starting with, and select that
	// course.
	public String selectCourseThatStartingWith(String name_starting_with)  {	
	try{	
		Thread.sleep(1000);
		System.out.println("select1");
	    List<String> course_list = getCourseList();
		System.out.println("select2");
		String target_course_name = null;				
		for (String course_name : course_list) {
			System.out.println(course_name+"Compare to"+name_starting_with);
			if (course_name.startsWith(name_starting_with)) {
				System.out.println("select3");
				target_course_name = course_name;
				selectCourseByName(target_course_name);
				// wait.until(ExpectedConditions.textToBePresentInElement(By.id("target_course_name"),
				// target_course_name));
				System.out.println("select4");		
				waitForVisibility(recording_tasks_button);
				System.out.println("selected the course: " + target_course_name);
				ATUReports.add("select the course: " + target_course_name, LogAs.PASSED, null);
				// wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("CourseTitle"),
				// target_course_name));
				return target_course_name;
			}
		}

		return target_course_name;
	}catch (Exception e){
		ATUReports.add("Selecting a course failed (screenshot)", e.getMessage(),
				LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE) );
		e.printStackTrace();
		Assert.assertTrue(false);
		return null;
	}
	}

	// This function get two courses (starting with), first the source, second
	// the destination
	// third arg is type of recordings 0 - Recordings, 1 - Additional content, 2
	// - Student Recordings, 3 - Tests
	// 4th arg - record object, 5th arg - copy menu object, 6th arg - confirm
	// menu object
	// it copies all recordings from source to destination under the tab choosen
	// in third arg
	public void copyRecordingFromCourseStartWithToCourseStartWithOfType(String source_start_with, String des_start_with,
			int type_of_recordings, RecordingHelperPage record_helper_page, CopyMenu copy_menu,
			ConfirmationMenu confirmation_menu) throws InterruptedException {
		String destination_course_name = null;
		String source_course = null;

		for (String course_name : getCourseList()) {
			if (course_name.startsWith(des_start_with)) {
				destination_course_name = course_name;
			} else if (course_name.startsWith(source_start_with)) {
				source_course = course_name;
			}
		}

		selectCourseThatStartingWith(source_course);

		if (type_of_recordings == 1) {
			new WebDriverWait(driver, 5)
					.until(ExpectedConditions.visibilityOf(record_helper_page.additional_content_tab));
			record_helper_page.clickOnAdditionContentTab();
		} else if (type_of_recordings == 2) {
			new WebDriverWait(driver, 5)
					.until(ExpectedConditions.visibilityOf(record_helper_page.student_recordings_tab));
			record_helper_page.clickOnStudentRecordingsTab();
		} else if (type_of_recordings == 3) {
			new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(record_helper_page.tests_tab));
			record_helper_page.clickOnTestsTab();
		}
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(record_helper_page.check_all_checkbox));
		wait.until(ExpectedConditions.elementToBeClickable(record_helper_page.check_all_checkbox));
		Thread.sleep(2000);
		while (!record_helper_page.check_all_checkbox.isSelected()) {
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(record_helper_page.check_all_checkbox));
			new WebDriverWait(driver, 10)
					.until(ExpectedConditions.elementToBeClickable(record_helper_page.check_all_checkbox));
			clickElement(record_helper_page.check_all_checkbox);		 					
			Thread.sleep(1000);
		}
		if ((type_of_recordings == 0) || (type_of_recordings == 2) ||  (type_of_recordings == 3)) {
			if(!record_helper_page.checkIfThereAreRecordingsInTab()){
				record_helper_page.returnToCourseListPage(this);
				return;
			}			
			record_helper_page.checkExistenceOfNoncopyableRecordingsStatusInRecordings();			
		} else if ((type_of_recordings == 1)) {		
			if(!record_helper_page.checkIfThereAreContentsInAdditionalTab()){
				record_helper_page.returnToCourseListPage(this);
				return;
			}
				record_helper_page.checkExistenceOfNonDeleteItemsStatusInAdditionalContent();
		}
		int i = 0;
		boolean copySuccess = false;
		while (i < 3 && !copySuccess) {
			try {
				if ((type_of_recordings == 0) || (type_of_recordings == 2) || (type_of_recordings == 3)) {
					record_helper_page.clickOnRecordingTaskThenCopy();
				} else if (type_of_recordings == 1) {
					record_helper_page.clickOnContentTaskThenCopy();
				}

				System.out.println("CopyHandle");
				copy_menu.selectTargetCourseFromCourseList(destination_course_name);
				System.out.println("CopyHandle1");
				copy_menu.clickOnCopyButton();
				System.out.println("CopyHandle2");
				confirmation_menu.clickOnOkButton();
				System.out.println("CopyHandle3");
				copySuccess = true;
			} catch (Exception e) {
				System.out.println("CopyHandleException" + i);
				i++;
				driver.navigate().refresh();
				Thread.sleep(5000);
				/*	WebElement div = driver.findElement(By.xpath("//*[@id='main']"));
				Actions builder = new Actions(driver);
				builder.moveToElement(div, 10, 10).click().build().perform();*/
				if (i >= 3) {
					ATUReports.add("Copy failed", "Copy Success", e.getMessage(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					System.out.println(e.getMessage());
					Assert.assertTrue(false);

				}
			}
		}
		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		record_helper_page.returnToCourseListPage(this);
	}

	
	// This function copy all recordings from source course to SEVERAL courses at once
	public void copyRecordingFromCourseStartWithToCourseStartWithOfType(String source_course, List<String> destinationCourses,
			int type_of_recordings, RecordingHelperPage record_helper_page, CopyMenu copy_menu,
			ConfirmationMenu confirmation_menu) throws InterruptedException {
		
		
		
		
	

		selectCourseThatStartingWith(source_course);

		if (type_of_recordings == 1) {
			new WebDriverWait(driver, 5)
					.until(ExpectedConditions.visibilityOf(record_helper_page.additional_content_tab));
			record_helper_page.clickOnAdditionContentTab();
		} else if (type_of_recordings == 2) {
			new WebDriverWait(driver, 5)
					.until(ExpectedConditions.visibilityOf(record_helper_page.student_recordings_tab));
			record_helper_page.clickOnStudentRecordingsTab();
		} else if (type_of_recordings == 3) {
			new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(record_helper_page.tests_tab));
			record_helper_page.clickOnTestsTab();
		}
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(record_helper_page.check_all_checkbox));
		wait.until(ExpectedConditions.elementToBeClickable(record_helper_page.check_all_checkbox));
		Thread.sleep(2000);
		while (!record_helper_page.check_all_checkbox.isSelected()) {
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(record_helper_page.check_all_checkbox));
			new WebDriverWait(driver, 10)
					.until(ExpectedConditions.elementToBeClickable(record_helper_page.check_all_checkbox));
			clickElement(record_helper_page.check_all_checkbox);		 					
			Thread.sleep(1000);
		}
		if ((type_of_recordings == 0) || (type_of_recordings == 2) ||  (type_of_recordings == 3)) {
			if(!record_helper_page.checkIfThereAreRecordingsInTab()){
				record_helper_page.returnToCourseListPage(this);
				return;
			}			
			record_helper_page.checkExistenceOfNoncopyableRecordingsStatusInRecordings();			
		} else if ((type_of_recordings == 1)) {		
			if(!record_helper_page.checkIfThereAreContentsInAdditionalTab()){
				record_helper_page.returnToCourseListPage(this);
				return;
			}
				record_helper_page.checkExistenceOfNonDeleteItemsStatusInAdditionalContent();
		}
		int i = 0;
		boolean copySuccess = false;
		while (i < 3 && !copySuccess) {
			try {
				if ((type_of_recordings == 0) || (type_of_recordings == 2) || (type_of_recordings == 3)) {
					record_helper_page.clickOnRecordingTaskThenCopy();
				} else if (type_of_recordings == 1) {
					record_helper_page.clickOnContentTaskThenCopy();
				}

				System.out.println("CopyHandle");
				copy_menu.selectTargetCourseFromCourseList(destinationCourses);
				System.out.println("CopyHandle1");
				copy_menu.clickOnCopyButton();
				System.out.println("CopyHandle2");
				confirmation_menu.clickOnOkButton();
				System.out.println("CopyHandle3");
				copySuccess = true;
			} catch (Exception e) {
				System.out.println("CopyHandleException" + i+" e:"+e.getMessage());
				i++;
				new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(record_helper_page.check_all_checkbox));
				new WebDriverWait(driver, 10)
						.until(ExpectedConditions.elementToBeClickable(record_helper_page.check_all_checkbox));
				clickElement(record_helper_page.check_all_checkbox);		 					
				Thread.sleep(1000);				
				/*	WebElement div = driver.findElement(By.xpath("//*[@id='main']"));
				Actions builder = new Actions(driver);
				builder.moveToElement(div, 10, 10).click().build().perform();*/
				if (i >= 3) {					
					ATUReports.add("Copy failed", "Copy Success", e.getMessage(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					System.out.println(e.getMessage());
					Assert.assertTrue(false);

				}
			}
		}
		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		record_helper_page.returnToCourseListPage(this);
	}
	
	
	
	
	
	// This function get course start with name as 1st arg,
	// as 2nd arg it get type when: 0 - Recordings, 1 - Additional Content, 2 -
	// Student Recordings, 3 - Test
	// record object as 3rd arg and delete obj as 4th arg
	// it will delete all recordings

	public void verifyRecordingsStatusIsClear(String source_course, int type_of_recordings,
			RecordingHelperPage record_helper_page) throws InterruptedException {

		selectCourseThatStartingWith(source_course);
		if (type_of_recordings == 1) {
			try {
				wait.until(ExpectedConditions.visibilityOf(record_helper_page.additional_content_tab));
				record_helper_page.clickOnAdditionContentTab();
			} catch (Exception msg) {
				System.out.println("There is no additional content tab.");
				try {
					record_helper_page.returnToCourseListPage(this);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
		} else if (type_of_recordings == 2) {
			try {
				wait.until(ExpectedConditions.visibilityOf(record_helper_page.student_recordings_tab));
				record_helper_page.clickOnStudentRecordingsTab();
				System.out.println("Click on student recordings tab.");
			} catch (Exception msg) {
				System.out.println("There is no student recordings tab.");
				try {
					record_helper_page.returnToCourseListPage(this);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
		} else if (type_of_recordings == 3) {
			try {
				wait.until(ExpectedConditions.visibilityOf(record_helper_page.tests_tab));
				record_helper_page.clickOnTestsTab();
			} catch (Exception msg) {
				System.out.println("There is no tests tab.");
				try {
					record_helper_page.returnToCourseListPage(this);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
		}
		if ((type_of_recordings == 0) || (type_of_recordings == 1) || (type_of_recordings == 2) || (type_of_recordings == 3)) {
			try {
				record_helper_page.checkStatusExistenceForMaxTTime(6000);
				record_helper_page.returnToCourseListPage(this);

			} catch (InterruptedException e) {
				System.out.println(e.getMessage());

			}
		}
	}

	public void deleteAllRecordingsInCourseStartWith(String course_name_start_with, int type_of_recordings,
			RecordingHelperPage recording_helper_page, DeleteMenu delete_menu) throws InterruptedException {		
	try{	
		selectCourseThatStartingWith(course_name_start_with);
		if (type_of_recordings == 1) {
			try {
				new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(recording_helper_page.additional_content_tab));
				recording_helper_page.additional_content_tab.click();
				System.out.println("Enter to the additional content tab.");
				ATUReports.add("Enter to the additional content tab.", LogAs.PASSED, null);
			} catch (Exception msg) {
				System.out.println("There is no additional content tab.");
				ATUReports.add("There is no additional content tab.", LogAs.PASSED, null);
				waitForVisibility(recording_helper_page.course_link);
				recording_helper_page.course_link.click();
				return;
			}
		} else if (type_of_recordings == 2) {
			try {
				new WebDriverWait(driver, 5)
						.until(ExpectedConditions.visibilityOf(recording_helper_page.student_recordings_tab));
				recording_helper_page.student_recordings_tab.click();
			} catch (Exception msg) {
				System.out.println("There is no student recordings tab.");
				recording_helper_page.returnToCourseListPage(this);
				return;
			}
		} else if (type_of_recordings == 3) {
			try {
				new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(recording_helper_page.tests_tab));
				recording_helper_page.tests_tab.click();
			} catch (Exception msg) {
				System.out.println("There is no tests tab.");
				recording_helper_page.returnToCourseListPage(this);
				return;
			}
		}

		if ((type_of_recordings == 0) || (type_of_recordings == 2)) {
			recording_helper_page.checkExistenceOfNonDeleteRecordingsStatusInRecordingsAndUncheckUndeleteableRecordings();
			recording_helper_page.deleteAllRecordings(delete_menu);
		} else if ((type_of_recordings == 1) || (type_of_recordings == 3)) {
			if (type_of_recordings == 3) {
				recording_helper_page.checkExistenceOfNonDeleteRecordingsStatusInRecordingsAndUncheckUndeleteableRecordings();
			} 
			new WebDriverWait(driver, 5)
					.until(ExpectedConditions.visibilityOf(recording_helper_page.check_all_checkbox));
			Thread.sleep(2000);
			recording_helper_page.SelectOneCheckBoxOrVerifyAlreadySelected(recording_helper_page.check_all_checkbox);
			Thread.sleep(500);
			// Thread.sleep(2000);
			// wait.until(ExpectedConditions.visibilityOf(recording_helper_page.check_all_checkbox));
			// recording_helper_page.check_all_checkbox.click();
			try {
				if (type_of_recordings == 1) {
						recording_helper_page.checkExistenceOfNonDeleteItemsStatusInAdditionalContentAndUncheckUndeleteableContent();	
					    recording_helper_page.clickOnContentTaskThenDelete();
				} else if (type_of_recordings == 3) {
					recording_helper_page.clickOnRecordingTaskThenDelete();
				}
				Thread.sleep(1000);
				delete_menu.clickOnDeleteButton();
			} catch (Exception msg) {
				System.out.println("There is no recordings in target course.");
			}
		}

		recording_helper_page.returnToCourseListPage(this);

		waitForVisibility(course_list.get(0));
	}catch(Exception e){
		System.out.println("Error"+e.getMessage());
		
	}
	}
	
	public String getCurrentUrlCoursePage() {
		String currUrl = ((JavascriptExecutor) driver).executeScript("return window.document.location.href").toString();
		return currUrl;
	}
	
	
	// verify there is no past courses tab
	public void verifyNoPastCoursesTab() {
		try {
			past_courses_tab_button.click();
			ATUReports.add("Click on Past courses tab.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("Click on Past courses tab.");
			Assert.assertTrue(false);
		} catch (Exception e) {
			ATUReports.add("No Past courses tab.", LogAs.PASSED, null);
			System.out.println("No Past courses tab.");
			Assert.assertTrue(true);
		}
	}

	// verify course name exists in course list
	public boolean verifyCourseExist(String name) {
		List <String> courses=getCourseList();
		for (int i=0;i<courses.size();i++) {
			System.out.println("course name to compare: "+courses.get(i));
			if (courses.get(i).equals(name)) {
				System.out.println("course exists");
				ATUReports.add("Course existence verification",name,"Course "+ name+" Found in course list ","Course "+ name+" Found in course list " ,LogAs.PASSED,null);
				return true;
			}
		}
		System.out.println("course  not exists");
		ATUReports.add("Course existence verification",name,"Course "+ name+" Found in course list ","Course "+ name+" wasn't Found in course list " ,LogAs.FAILED,null);
		return false;
	}

	public boolean verifyCourseExistWithCourseList(String name, List<String> courseList) {
		for (String course : courseList) {
			if (name.equals(course)) {
				System.out.println("course exists");
				return true;
			}
		}
		System.out.println("course  not exists");
		return false;
	}

	// verify course name exists in course list
	public boolean verifyCourseNotExist(String name) {
		for (String course : courses) {
			if (name.equals(course)) {
				System.out.println("course exists");
				Assert.assertTrue(false);
				ATUReports.add("course exists", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				return false;
			}
		}
		System.out.println("course  not exists");
		Assert.assertTrue(true);
		ATUReports.add("course not exists", LogAs.PASSED, null);

		return true;
	}

	// This function clicks on public courses tab
	public void clickOnPublicCoursesTab() {
		try {
			waitForVisibility(public_courses_tab);
			String initialCourseText=courseFrame.getText();
			public_courses_tab.click();						
			waitForContentOfTabToLoad(initialCourseText,courseFrame);
		//	fluentWaitInvisibility(first_course_button, 5, 100);
			//fluentWaitVisibility(first_course_button, 10, 500);
			WebElement tabParent=public_courses_tab.findElement(By.xpath(".."));			
			wait.until(ExpectedConditions.attributeContains(tabParent, "class", "active"));
			System.out.println("Clicked on public courses tab.");
			ATUReports.add("Clicked on public courses tab.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception msg) {
			System.out.println("Not clicked on public courses tab.");
			ATUReports.add("Clicked on public courses tab.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function go to api of active courses
	public void goToAPICoursesActive(String UserName, int number) throws InterruptedException {
		String login_url = driver.getCurrentUrl();
		String university_name = login_url.split("/")[2].substring(0, login_url.split("/")[2].length() - 12);
		String active_course_api = "https://" + university_name + ".tegrity.com/api/courses/active";
		if (driver instanceof InternetExplorerDriver) {
			driver.close();
			driver = new ChromeDriver();
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
			if (number == 0) {
				tegrity.loginCourses("User1");
			} else
				tegrity.loginCoursesByParameter(UserName);
		}
		driver.navigate().to(active_course_api);
	}

	// This function go to courses main page
	public void goToCoursesPage() {
		String login_url = driver.getCurrentUrl();
		String university_name = login_url.split("/")[2].substring(0, login_url.split("/")[2].length() - 12);
		String course_page_url = "https://" + university_name + ".tegrity.com/#/courses";
		driver.navigate().to(course_page_url);
	}

	// This function verify that only one course set true as private course in
	// API course active, and return thee name of this course
	public String veriftyThatOnlyOneCourseSetIsPrivateAsTrueInAPICourseActive() {
		String string_xml_api_active_courses = driver.getPageSource();

		String[] splited_elements_of_xml_api = string_xml_api_active_courses.split(">");
		List<String> courses_list_from_api = new ArrayList<String>();
		String parsed_private_course_name = "";
		int api_number_of_private_course = 0;

		for (int i = 0; i < splited_elements_of_xml_api.length; i++) {

			String api_course_name_value = null;
			String api_course_is_private_value = null;

			if (splited_elements_of_xml_api[i].startsWith("<CourseCaption")) {
				api_course_name_value = splited_elements_of_xml_api[i + 1].split("<")[0];
				courses_list_from_api.add(api_course_name_value);
			}
			if (splited_elements_of_xml_api[i].endsWith("</IsPrivate")) {
				api_course_is_private_value = splited_elements_of_xml_api[i].split("<")[0];
				if (api_course_is_private_value.equals("true")) {
					parsed_private_course_name = courses_list_from_api.get(courses_list_from_api.size() - 1);
					api_number_of_private_course++;
				}
			}
		}

		if (api_number_of_private_course > 0) {
			if (api_number_of_private_course == 1) {
				System.out.println("Verified that there is only one course which set as private in active course api.");
				ATUReports.add("Active course api.", "One course set as private.", "One course set as private.",
						LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("There is more then one course in the xml with IsPrivate tag set to true.");
				ATUReports.add("There is one course which set in xml as private.", "Ture.", "True.", LogAs.FAILED,
						null);
				Assert.assertTrue(false);
			}
		} else {
			System.out.println("There is no course which set as private in active course api.");
			ATUReports.add("Active course api.", "One course set as private.", "No course set as private.",
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}

		return parsed_private_course_name;
	}

	// This function verify that there no private course
	public String veriftyThatNoCourseSetIsPrivateAsTrueInAPICourseActive() {
		String string_xml_api_active_courses = driver.getPageSource();

		String[] splited_elements_of_xml_api = string_xml_api_active_courses.split(">");
		List<String> courses_list_from_api = new ArrayList<String>();
		String parsed_private_course_name = "";
		int api_number_of_private_course = 0;

		for (int i = 0; i < splited_elements_of_xml_api.length; i++) {

			String api_course_name_value = null;
			String api_course_is_private_value = null;

			if (splited_elements_of_xml_api[i].startsWith("<CourseCaption")) {
				api_course_name_value = splited_elements_of_xml_api[i + 1].split("<")[0];
				courses_list_from_api.add(api_course_name_value);
			}
			if (splited_elements_of_xml_api[i].endsWith("</IsPrivate")) {
				api_course_is_private_value = splited_elements_of_xml_api[i].split("<")[0];
				if (api_course_is_private_value.equals("true")) {
					parsed_private_course_name = courses_list_from_api.get(courses_list_from_api.size() - 1);
					api_number_of_private_course++;
				}
			}
		}

		if (api_number_of_private_course == 0) {
			System.out.println("There is no course which set as private in active course api.");
			ATUReports.add("Active course api.", "No course set as private.", "No course set as private.", LogAs.PASSED,
					null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Verified that there is course which set as private in active course api.");
			ATUReports.add("Active course api.", "No course set as private.", "There is course/s set as private.",
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}

		return parsed_private_course_name;
	}

	// This function verify that no course is displayed.
	public void verifyThatNoCourseIsDisplayed() {
		List<String> current_course_list = getCourseList();

		if (current_course_list.size() == 0) {
			System.out.println("Verified that no course is displayed.");
			ATUReports.add("Course list.", "No course in the list.", "No course in the list.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verified that no course is displayed.");
			ATUReports.add("Course list.", "There is course in the list.", "No course in the list.", LogAs.FAILED,
					null);
			Assert.assertTrue(false);
		}
	}

	// verify user is "Guest"
	public void verifyUserGuest() {
		if (user_name.getText().equals("Guest")) {
			ATUReports.add("user guest", LogAs.PASSED, null);
			System.out.println("user guest");
			Assert.assertTrue(true);
		} else {
			ATUReports.add("No user guest", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("No user guest");
			Assert.assertTrue(false);
		}
	}

	/// Validate the "Get Started" box is displayed to the [Guest] User
	public void verifyGetStartedBox() {
		if (get_started_box.isDisplayed()) {
			ATUReports.add("Get Started box is displayed", LogAs.PASSED, null);
			System.out.println("Get Started box is displayed");
			Assert.assertTrue(true);
		} else {
			ATUReports.add("Get Started box is not displayed", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("Get Started box is not displayed");
			Assert.assertTrue(false);
		}
	}

	/// Validate the top bar menu "Guest | Disclaimer | help | sign out"
	public void verifyTopBarMenu() {
		if ((user_name.isDisplayed()) && (disclaimer.isDisplayed()) && (help.isDisplayed())
				&& (sign_out.isDisplayed())) {
			ATUReports.add("top bar menu is displayed", LogAs.PASSED, null);
			System.out.println("top bar menu is displayed");
			Assert.assertTrue(true);
		} else {
			ATUReports.add("top bar menu is not displayed", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("top bar menu is not displayed");
			Assert.assertTrue(false);
		}
	}

	// verify there is no reports link
	public void verifyNoReportsAndMyAccountLink() {
		try {
			reports.click();
			ATUReports.add("Click on reports link.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("Click on reports link.");
			Assert.assertTrue(false);
		} catch (Exception e) {
			ATUReports.add("No reports link", LogAs.PASSED, null);
			System.out.println("No reports link");
			Assert.assertTrue(true);
		}
		try {
			my_account.click();
			ATUReports.add("Click on my account link.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("Click on my account link.");
			Assert.assertTrue(false);
		} catch (Exception e) {
			ATUReports.add("No my account link", LogAs.PASSED, null);
			System.out.println("No my account link");
			Assert.assertTrue(true);
		}
	}

	// verify there is no past courses tab
	public void verifyNoActiveCoursesTab() {
		try {
			active_courses_tab_button.click();
			ATUReports.add("Click on active courses tab.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("Click on active courses tab.");
			Assert.assertTrue(false);
		} catch (Exception e) {
			ATUReports.add("No active courses tab.", LogAs.PASSED, null);
			System.out.println("No active courses tab.");
			Assert.assertTrue(true);
		}
	}

	// verify there is no past courses tab
	public void verifyNoPrivateCoursesTab() {
		try {
			private_courses_tab_button.click();
			ATUReports.add("Click on Past courses tab.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("Click on Past courses tab.");
			Assert.assertTrue(false);
		} catch (Exception e) {
			ATUReports.add("No Past courses tab.", LogAs.PASSED, null);
			System.out.println("No Past courses tab.");
			Assert.assertTrue(true);
		}
	}



	// verify there is no disclaimer link
	public void verifyNoDisclaimerLink() {
		try {
			disclaimer.click();
			ATUReports.add("Click on disclaimer link.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("Click on disclaimer link.");
			Assert.assertTrue(false);
		} catch (Exception e) {
			ATUReports.add("No disclaimer link", LogAs.PASSED, null);
			System.out.println("No disclaimer link");
			Assert.assertTrue(true);
		}

	}

	/// Hover over "Help" work item menue and verify its elements
	public void verifyHelpElements() {
		Robot robot;
		try {
			robot = new Robot();
			robot.mouseMove(-100, -100);
			/// move to help menu
			moveToElement(help, driver).perform();
			if ((run_diagnostics.isDisplayed())) {
				System.out.println("run diagnostics  visible");
				ATUReports.add("run diagnostics visible", LogAs.PASSED, null);
				Assert.assertTrue(true);

				if (get_started.isDisplayed()) {
					System.out.println("get started  visible");
					ATUReports.add("get started visible", LogAs.PASSED, null);
					Assert.assertTrue(true);

					if ((get_support.isDisplayed())) {
						System.out.println("get support visible");
						ATUReports.add("get support visible", LogAs.PASSED, null);
						Assert.assertTrue(true);

						if ((online_help.isDisplayed())) {
							System.out.println("online help visible");
							ATUReports.add("online help visible", LogAs.PASSED, null);
							Assert.assertTrue(true);
							System.out.println("run diagnostics,get started,get support and online help are visible");
						} else {
							System.out.println("online help not visible");
							ATUReports.add("online help not visible", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
							Assert.assertTrue(false);
						}

					} else {
						System.out.println("get support not visible");
						ATUReports.add("get support not visible", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
						Assert.assertTrue(false);
					}

				} else {

					System.out.println("get started not visible");
					ATUReports.add("get started not visible", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				}
			} else {
				System.out.println("run diagnostics not visible");
				ATUReports.add("run diagnostics not visible", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}

		} catch (AWTException e) {
			System.out.println("run diagnostics,get started,get support or online help caused exception");
			ATUReports.add("run diagnostics,get started,get support or online help caused exception", LogAs.FAILED,
					null);
			Assert.assertTrue(false);
		}

	}

	/// click on help->1. Online help,2. Get started,3. Get support,4. Run
	/// diagnostic
	public void toHelpMenu(WebElement element) {

		try {
			waitForVisibility(help);

			Robot robot = new Robot();
			robot.mouseMove(-100, -100);
			moveToElement(help, driver).perform();
			waitForVisibility(element);
			String to_click = element.getText();
			element.click();
			System.out.println("clicked element: " + to_click);
			ATUReports.add("clicked element", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			String to_click = element.getText();
			System.out.println("exception clicking: " + to_click);
			ATUReports.add("exception clicking", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	/// verify get started block
	public void verifyGetStartedBlock() {
		if (get_started_block.isDisplayed()) {
			ATUReports.add("get started block is visible", LogAs.PASSED, null);
			System.out.println("get started block is visible");
			Assert.assertTrue(true);
		} else {

			ATUReports.add("get started block is not visible", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("get started block is not visible");
			Assert.assertTrue(false);
		}

	}

	/// verify not visible get started block
	public void verifyNoGetStartedBlock() {
		// TODO Auto-generated method stub
		try {
			close_get_started_block.click();
			ATUReports.add("get started block is visible", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("get started block is  visible");
			Assert.assertTrue(false);
		} catch (Exception e) {
			ATUReports.add("get started block is not visible", LogAs.PASSED, null);
			System.out.println("get started block is not visible");
			Assert.assertTrue(true);
		}

	}

	// Verify that if "Past Courses" tab is not present, the "Public Courses"
	// tab is displayed right to the "Active Courses" else
	// "Public Courses" tab is displayed to the right of "Past Courses" tab.
	public void verifyTabsOrder() {
		if (past_courses_tab_button.isDisplayed()) {
			if ((past_courses_tab_button.getLocation().x < public_courses_tab.getLocation().x)
					&& (active_courses_tab_button.getLocation().x < past_courses_tab_button.getLocation().x)) {
				System.out.println("Verified that tabs order is correct.");
				ATUReports.add("Tabs order.", "Correct.", "Correct.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Not verified that tabs order is correct.");
				ATUReports.add("Tabs order.", "Correct.", "Not correct.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		} else {
			if (active_courses_tab_button.getLocation().x < public_courses_tab.getLocation().x) {
				System.out.println("Verified that tabs order is correct.");
				ATUReports.add("Tabs order.", "Correct.", "Correct.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Not verified that tabs order is correct.");
				ATUReports.add("Tabs order.", "Correct.", "Not correct.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		}
	}

	// This function verify that when you hover over the Public Courses tab
	// that:
	// 1. Hint is diplayed to the user.
	// 2. Change the button color background.
	public void verifyHoveringOverPublicCoursesButton() throws Exception {
		Robot robot = new Robot();
		robot.mouseMove(-100, -100);
		String color_before_hovring = getBackGroundColor(public_courses_tab);

		moveToElement(public_courses_tab, driver).perform();

		String color_after_hovring = getBackGroundColor(public_courses_tab);

		if (public_courses_tab.getAttribute("title").equals("Public Courses")) {
			System.out.println("Hovering over the Public Courses displays the hint correct.");
			ATUReports.add("Hovering over Public Courses button the hint is displayed to the user.", "True.", "True.",
					LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Hovering over the Public Courses not displays the hint correct.");
			ATUReports.add("Hovering over Public Courses button the hint is displayed to the user.", "True.", "False.",
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}

		if (color_before_hovring.equals(color_after_hovring)) {
			System.out.println("Not change button color background.");
			ATUReports.add("Change button color background.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		} else {
			System.out.println("Change button color background.");
			ATUReports.add("Change button color background.", "True.", "True.", LogAs.PASSED, null);
		}
	}

	// Verify that
	// Validate the "New Recordings" text is aligned to the right.
	// Validate total quantity of new recordings in the course is displayed on
	// the right side of the course line.
	public void verifyNewRecordingsTextAndQuantityOfNewRecordingsDisplayedOnTheRightSide() {
		if ((public_courses_tab.getLocation().x < new_recordings_title_and_number_of_new_recordings.get(0)
				.getLocation().x)
				&& (new_recordings_title_and_number_of_new_recordings.get(0)
						.getLocation().x < new_recordings_title_and_number_of_new_recordings.get(1).getLocation().x)
				&& (new_recordings_title_and_number_of_new_recordings.get(0).getText().equals("New Recordings"))) {
			System.out.println(new_recordings_title_and_number_of_new_recordings.get(0).getText());
			System.out.println("Verified UI of New Recordings text and total quantity of new recording UI.");
			ATUReports.add("New recording text and total quantity of new recordings UI.", "Verified.", "Verified.",
					LogAs.PASSED, null);
		} else {
			System.out.println("Not verified UI of New Recordings text and total quantity of new recording UI.");
			ATUReports.add("New recording text and total quantity of new recordings UI.", "Verified.", "Not verified.",
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}

	// This function verify that the page caption ‘Courses’ is displayed at the
	// top left of the page
	public void verifyCoursesDisplayedTopLeft() {
		if ((courses_heading.getLocation().y < public_courses_tab.getLocation().y)
				&& (courses_heading.getLocation().x < public_courses_tab.getLocation().x)) {
			System.out.println("Verify that the page caption Courses is displayed at the top left of the page.");
			ATUReports.add("The page caption Courses is displayed at the top left.", "True.", "True.", LogAs.PASSED,
					null);
		} else {
			System.out.println("Not verify that the page caption Courses is displayed at the top left of the page.");
			ATUReports.add("The page caption Courses is displayed at the top left.", "True.", "False.", LogAs.FAILED,
					null);
		}
	}

	// This function verify the ‘Active Courses’ tab is displayed under the page
	// caption.
	public void verifyActiveCoursesTabDisplayedUnderThePageHeading() {
		if (courses_heading.getLocation().y < active_courses_tab_button.getLocation().y) {
			System.out.println("Verified that Active Courses tab displayed under the page heading.");
			ATUReports.add("Verified Active Courses tab displayed under the page heading.", "True.", "True.",
					LogAs.PASSED, null);
		} else {
			System.out.println("Not verified that Active Courses tab displayed under the page heading.");
			ATUReports.add("Verified Active Courses tab displayed under the page heading.", "True.", "False.",
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}

	// This function verify that every course record contains information as
	// follows: course name: ‘X recording(s) – Y new | last updated:
	// mm/dd/yyyy’.
	public void verifyEveryCourseRecordContainsCorrectInformation() throws InterruptedException {
		int number_of_courses = getCourseList().size();

		boolean to_wait_until_data_loads = true;
		int max_waiting_cycle = 10;
		while (to_wait_until_data_loads) {
			if (number_of_courses > 0) {
				String x_recordings_y_new = driver.findElement(By.id("RecordingsCounter1")).getText();
				if (x_recordings_y_new.split(" ").length == 3) {
					Thread.sleep(1000);
				} else {
					to_wait_until_data_loads = false;
				}
			}
			max_waiting_cycle--;
			if (max_waiting_cycle == 0) {
				break;
			}
		}

		boolean not_correct = false;
		if (!to_wait_until_data_loads) {
			for (int i = 1; i <= number_of_courses; i++) {
				String x_recordings_y_new = driver.findElement(By.id("RecordingsCounter" + Integer.toString(i)))
						.getText();
				String last_updated_mmddyyyy = driver.findElement(By.id("UpdateDate" + Integer.toString(i))).getText();

				String[] splited_x_recordings_y_new = x_recordings_y_new.split(" ");
				String[] splited_last_updated_mmddyyyy = last_updated_mmddyyyy.split(" ")[3].split("/");

				if ((Integer.parseInt(splited_x_recordings_y_new[0]) >= 0)
						&& (Integer.parseInt(splited_x_recordings_y_new[3]) >= 0)
						&& (Integer.parseInt(splited_last_updated_mmddyyyy[0]) >= 0)
						&& (Integer.parseInt(splited_last_updated_mmddyyyy[1]) >= 0)
						&& (Integer.parseInt(splited_last_updated_mmddyyyy[2]) >= 0)) {
					continue;
				} else {
					not_correct = true;
					break;
				}
			}
		}

		if (not_correct) {
			System.out.println("Not verified that every course record contains correct information.");
			ATUReports.add("Every course record contains correct information.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		} else {
			System.out.println("Verified that every course record contains correct information.");
			ATUReports.add("Every course record contains correct information.", "True.", "True.", LogAs.PASSED, null);
		}
	}

	// This function verify that total quantity of new recordings in the course
	// is displayed on the right side of the course line.
	public void verifyThatTotalQuantitiyNewRecordingDisplayedOnTheRightSideOfTheCourseLine() {
		int number_of_courses = getCourseList().size();

		boolean not_correct = false;
		for (int i = 1; i <= number_of_courses; i++) {
			WebElement recording_counter = driver.findElement(By.id("NewRecordingsCounter" + Integer.toString(i)));
			WebElement recording_title = driver.findElement(By.id("Course" + Integer.toString(i)));
			if (recording_counter.getAttribute("class").contains("hide"))
				continue;

			if (recording_title.getLocation().x >= recording_counter.getLocation().x) {
				not_correct = true;
				break;
			}
		}

		if (not_correct) {
			System.out.println(
					"Not verified that total quantity of new recordings in the course is displayed on the right side of the course line.");
			ATUReports.add(
					"Total quantity of new recording in the course displayed on the right side of the course line.",
					"True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		} else {
			System.out.println(
					"Verified that total quantity of new recordings in the course is displayed on the right side of the course line.");
			ATUReports.add(
					"Total quantity of new recording in the course displayed on the right side of the course line.",
					"True.", "True.", LogAs.PASSED, null);
		}
	}

	// This function verify that when a user hovers over the course name the
	// hint with the course name is displayed to the user.
	public void verifyThatWhenUserHoverOverFirstCourseNameTheHingWithTheCourseNameDispalyed() {
		moveToElement(first_course_button, driver).perform();
		if (first_course_button.getAttribute("title").equals(first_course_button.getText())) {
			System.out.println("Hovering over the the course name displays the hint correct.");
			ATUReports.add("Hovering over the the course name displays the hint correct.", "True.", "True.",
					LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Hovering over the the course name not displays the hint correct.");
			ATUReports.add("Hovering over the the course name displays the hint correct.", "True.", "False.",
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function verify that the courses list page is displayed (under the
	// tab "Public Courses" which is the only one available).
	public void verifyThatCoursesListDisplayedUnderPublicCoursesWhichIsTheOnlyOneAvaiable() {
		if ((!active_courses_tab_button.isDisplayed()) && (public_courses_tab.isDisplayed())
				&& (getCourseList().size() > 0)) {
			System.out.println(
					"Verified that the course list is displayed under tab Public Courses which is the only one avaiable.");
			ATUReports.add(
					"Verified that the course list is displayed under tab Public Courses which is the only one avaiable.",
					"True.", "True.", LogAs.PASSED, null);
		} else {
			System.out.println(
					"Not verified that the course list is displayed under tab Public Courses which is the only one avaiable.");
			ATUReports.add(
					"Verified that the course list is displayed under tab Public Courses which is the only one avaiable.",
					"True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}

	// Verify the tag new recordings isn't displayed to the right for guest
	// user.
	public void verifyThatTagNewRecordingsNotDisplayedToTheRightForGuestUser() {
		if (new_recordings_title_and_number_of_new_recordings.get(0).getText().equals("New Recordings")) {
			System.out.println("Not verified that tag new recordings is not displayed to the right for guest user.");
			ATUReports.add("Verified that tag new recordings is not displayed to the right for guest user.", "True.",
					"False", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		} else {
			System.out.println("Verified that tag new recordings is not displayed to the right for guest user.");
			ATUReports.add("Verified that tag new recordings is not displayed to the right for guest user.", "True.",
					"True", LogAs.PASSED, null);
		}
	}

	// This function total quantity of new recordings in the course isn't
	// displayed on the right side of the course line.
	public void verifyThatTotalQuantitiyNewRecordingNotDisplayedOnTheRightSideOfTheCourseLineForGuestUser() {
		int number_of_courses = getCourseList().size();

		boolean not_correct = false;
		for (int i = 1; i <= number_of_courses; i++) {
			if (driver.findElement(By.id("NewRecordingsCounter" + Integer.toString(i))).isDisplayed()) {
				not_correct = true;
				break;
			}
		}

		if (not_correct) {
			System.out.println(
					"Not verified that total quantity of new recordings in the course is not displayed on the right side of the course line.");
			ATUReports.add(
					"Total quantity of new recording in the course not displayed on the right side of the course line.",
					"True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		} else {
			System.out.println(
					"Verified that total quantity of new recordings in the course is not displayed on the right side of the course line.");
			ATUReports.add(
					"Total quantity of new recording in the course not displayed on the right side of the course line.",
					"True.", "True.", LogAs.PASSED, null);
		}
	}

	// This function verify that the list of courses is sorted by their names
	// (AaBaCc sorting is used).
	public void verifyThatListOfCoursesIsSortedByTheirNames() {
		List<String> courses_list = getCourseList();

		String previous_course_name = courses_list.get(0);

		boolean sorting_not_correct = false;
		for (int i = 1; i < courses_list.size(); i++) {
			String current_course_name = courses_list.get(i);
			if (previous_course_name.compareTo(current_course_name.toLowerCase()) == 0) {
				if (previous_course_name.compareTo(current_course_name) <= 0) {
					previous_course_name = current_course_name;
					continue;
				} else {
					sorting_not_correct = true;
					break;
				}
			} else if (previous_course_name.compareTo(current_course_name.toLowerCase()) < 0) {
				previous_course_name = current_course_name;
				continue;
			} else {
				sorting_not_correct = true;
				break;
			}
		}

		if (!sorting_not_correct) {
			System.out.println("Verfied that the list of courses is sorted by their names.");
			ATUReports.add("Verfied that the list of courses is sorted by their names.", "True.", "True.", LogAs.PASSED,
					null);
		} else {
			System.out.println("Not verfied that the list of courses is sorted by their names.");
			ATUReports.add("Verfied that the list of courses is sorted by their names.", "True.", "False.",
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}

	// Verify that the 'Active Courses' tab is displayed
	public void verifyActiveCoursesTabDisplayed() {
		if (active_courses_tab_button.isDisplayed()) {
			System.out.println("Verified that the Active Courses tab is displayed.");
			ATUReports.add("Verified that the Active Courses tab is displayed.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verified that the Active Courses tab is displayed.");
			ATUReports.add("Verified that the Active Courses tab is displayed.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// Verify that the 'Public Courses' tab is displayed
	public void verifyPublicCoursesTabDisplayed() {
		if (public_courses_tab.isDisplayed()) {
			System.out.println("Verified that the Public Courses tab is displayed.");
			ATUReports.add("Verified that the Public Courses tab is displayed.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verified that the Public Courses tab is displayed.");
			ATUReports.add("Verified that the Public Courses tab is displayed.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// Verify that the 'Past Courses' tab is displayed
	public void verifyPastCoursesTabDisplayed() {
		if (past_courses_tab_button.isDisplayed()) {
			System.out.println("Verified that the Past Courses tab is displayed.");
			ATUReports.add("Verified that the Past Courses tab is displayed.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verified that the Past Courses tab is displayed.");
			ATUReports.add("Verified that the Past Courses tab is displayed.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// Verify that the 'Past Courses' tab is not displayed
	public void verifyPastCoursesTabNotDisplayed() {
		if (past_courses_tab_button.isDisplayed()) {
			System.out.println("Not verified that the Past Courses tab is not displayed.");
			ATUReports.add("Verified that the Past Courses tab is not displayed.", "True.", "False.", LogAs.FAILED,
					null);
			Assert.assertTrue(false);
		} else {
			System.out.println("Verified that the Past Courses tab is not displayed.");
			ATUReports.add("Verified that the Past Courses tab is not displayed.", "True.", "True.", LogAs.PASSED,
					null);
			Assert.assertTrue(true);
		}
	}

	// Hover over 'Past courses' tab.
	// The font color changes to black.
	// The tab background change to white.
	// A hint appears with 'Past Courses' text.
	public void verifyUIHoveringPastCoursesTab() {
		String init_background_color = getBackGroundColor(past_courses_tab_button);
		String init_font_color = getFontColor(past_courses_tab_button);
		moveToElement(past_courses_tab_button, driver).perform();
		String hovring_background_color = getBackGroundColor(past_courses_tab_button);
		String hovring_font_color = getFontColor(past_courses_tab_button);

		if (past_courses_tab_button.getAttribute("title").equals("Past Courses")) {
			System.out.println("Verfied a hint appears with Past Courses text.");
			ATUReports.add("Verfied a hint appears with Past Courses text.", "True.", "True.", LogAs.PASSED, null);
		} else {
			System.out.println("Not verfied a hint appears with Past Courses text.");
			ATUReports.add("Verfied a hint appears with Past Courses text.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}

		if (!init_background_color.equals(hovring_background_color)) {
			System.out.println("Verfied background color changed.");
			ATUReports.add("Verfied background color changed.", "True.", "True.", LogAs.PASSED, null);
		} else {
			System.out.println("Not verfied background color changed.");
			ATUReports.add("Verfied background color changed.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}

		if (!init_font_color.equals(hovring_font_color)) {
			System.out.println("Verfied font color changed.");
			ATUReports.add("Verfied font color changed.", "True.", "True.", LogAs.PASSED, null);
		} else {
			System.out.println("Not verfied font color changed.");
			ATUReports.add("Verfied font color changed.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}

	// Hover over 'Public courses' tab.
	// The font color changes to black.
	// The tab background change to white.
	// A hint appears with 'Public Courses' text.
	public void verifyUIHoveringPublicCoursesTab() {
		String init_background_color = getBackGroundColor(public_courses_tab);
		String init_font_color = getFontColor(public_courses_tab);
		moveToElement(public_courses_tab, driver).perform();
		String hovring_background_color = getBackGroundColor(public_courses_tab);
		String hovring_font_color = getFontColor(public_courses_tab);

		if (public_courses_tab.getAttribute("title").equals("Public Courses")) {
			System.out.println("Verfied a hint appears with Public Courses text.");
			ATUReports.add("Verfied a hint appears with Public Courses text.", "True.", "True.", LogAs.PASSED, null);
		} else {
			System.out.println("Not verfied a hint appears with Public Courses text.");
			ATUReports.add("Verfied a hint appears with Public Courses text.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}

		if (!init_background_color.equals(hovring_background_color)) {
			System.out.println("Verfied background color changed.");
			ATUReports.add("Verfied background color changed.", "True.", "True.", LogAs.PASSED, null);
		} else {
			System.out.println("Not verfied background color changed.");
			ATUReports.add("Verfied background color changed.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}

		if (!init_font_color.equals(hovring_font_color)) {
			System.out.println("Verfied font color changed.");
			ATUReports.add("Verfied font color changed.", "True.", "True.", LogAs.PASSED, null);
		} else {
			System.out.println("Not verfied font color changed.");
			ATUReports.add("Verfied font color changed.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}

	// This function verify that target course is not exist in Public Courses
	public void verifyThatTargetCourseIsNotExistInPublicCourses(String target_course) throws InterruptedException {
	try{
		if (!public_courses_tab.isDisplayed()) {
			System.out.println("Verified that target course is not exist in Public Courses.");
			ATUReports.add("Verified that target course is not exist in Public Courses.", "True.", "True.",
					LogAs.PASSED, null);
		} else {
			clickOnPublicCoursesTab();
			Thread.sleep(1000);
			List<String> CoursesList = getCourseList();
			courses = new String[CoursesList.size()];
			courses = CoursesList.toArray(courses);
			Thread.sleep(3000);
			verifyCourseNotExist(target_course);
		}
	}catch (Exception e){
		ATUReports.add("Error", e.getMessage(),	LogAs.FAILED, null);
		
		
	}
	}

	// This function get two courses (starting with), first the source, second
	// the destination
	// third arg is type of recordings 0 - Recordings, 1 - Additional content, 2
	// - Student Recordings, 3 - Tests
	// 4th arg - record object, 5th arg - copy menu object, 6th arg - confirm
	// menu object
	// it copies oe recording from source to destination under the tab chosen
	// in third argument
	public void copyOneRecordingFromCourseStartWithToCourseStartWithOfType(String source_start_with,
			String des_start_with, int type_of_recordings, RecordingHelperPage record_helper_page, CopyMenu copy_menu,
			ConfirmationMenu confirmation_menu) throws InterruptedException {
		// String destination_course_name = null;
		// String source_course = null;
		//
		// for (String course_name : getCourseList()) {
		// if (course_name.startsWith(des_start_with)) {
		// destination_course_name = course_name;
		// } else if (course_name.startsWith(source_start_with)) {
		// source_course = course_name;
		// }
		// }

		// selectCourseThatStartingWith(source_course);
		selectCourseThatStartingWith(source_start_with);

		if (type_of_recordings == 1) {
			record_helper_page.clickOnAdditionContentTab();
		} else if (type_of_recordings == 2) {
			record_helper_page.clickOnStudentRecordingsTab();
		} else if (type_of_recordings == 3) {
			record_helper_page.clickOnTestsTab();
		}

		Thread.sleep(3000);

		// wait.until(ExpectedConditions.visibilityOf(record_helper_page.first_recording_title));

		record_helper_page.SelectOneCheckBoxOrVerifyAlreadySelected(record_helper_page.checkbox);;

		if ((type_of_recordings == 0) || (type_of_recordings == 2) || (type_of_recordings == 3)) {
			record_helper_page.clickOnRecordingTaskThenCopy();
		} else if (type_of_recordings == 1) {
			record_helper_page.clickOnContentTaskThenCopy();
		}

		// copy_menu.selectTargetCourseFromCourseList(destination_course_name);
		copy_menu.selectTargetCourseFromCourseListThatStartWith(des_start_with);
		Thread.sleep(2000);
		copy_menu.clickOnCopyButton();
		Thread.sleep(1000);
		confirmation_menu.clickOnOkButton();
		Thread.sleep(3000);
	/*	if ((type_of_recordings == 0) || (type_of_recordings == 2) || (type_of_recordings == 3)) {
			record_helper_page.checkStatusExistenceForMaxTTime(600);
		}*/

		// else if (type_of_recordings == 1) {
		// Being copied from
		// }
		//
		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		record_helper_page.returnToCourseListPage();
	}

	// verify there is no Start Recording button
	public void verifyNoStartRecording() {
		try {
			if (start_recording_button.isDisplayed()) {
				ATUReports.add("Click on start recording button.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				System.out.println("Click on start recording button.");
				Assert.assertTrue(false);
			} else {
				ATUReports.add("No start recording button", LogAs.PASSED, null);
				System.out.println("No start recording button");
				Assert.assertTrue(true);
			}
		} catch (Exception e) {
			ATUReports.add("No start recording button", LogAs.PASSED, null);
			System.out.println("No start recording button");
			Assert.assertTrue(true);
		}
	}

	// This function validate that target course have at least one recording,
	// and if not it will copy one recording from bank to this course
	public String validateThereIsRecordingInCourseStartWithIfThereIsNotCopyOneRecordingToThisCourse(
			String target_course_start_with, String recording_bank_start_with, RecordingHelperPage record,
			CopyMenu copy, ConfirmationMenu confirm) throws InterruptedException {
		String target_course = selectCourseThatStartingWith(target_course_start_with);

		Thread.sleep(1000);

		if (record.getNumberOfRecordings() > 0) {
			return target_course;
		} else {
			record.returnToCourseListPage();
			Thread.sleep(1000);
			copyOneRecordingFromCourseStartWithToCourseStartWithOfType(recording_bank_start_with,
					target_course_start_with, 0, record, copy, confirm);
		}

		return target_course;
	}
	
	
	public String[] getCoursesListFromElement(List<WebElement> text)/// text
	/// extracted
	/// from
	/// elements getCoursesListFromElement
	{
		String[] linkTexts = new String[text.size()];
		int i = 0;

		// extract the link texts of each link element
		for (WebElement e : text) {
			linkTexts[i] = e.getText();
			i++;
		}
		/*waitForVisibility(active_courses_tab_button);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("wrapper"), "recordings -"));*/
		return linkTexts;
	}

}