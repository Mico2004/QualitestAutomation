package com.automation.main.appium;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.swing.plaf.basic.BasicSliderUI.ActionScroller;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.ScrollAction;
import org.openqa.selenium.interactions.touch.TouchActions;
//import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.automation.main.AddAdditionalContentFileWindow;
import com.automation.main.AdminDashboardPage;
import com.automation.main.AdvancedServiceSettingsPage;
import com.automation.main.ConfirmationMenu;
import com.automation.main.CopyMenu;
import com.automation.main.CourseSettingsPage;
import com.automation.main.CoursesHelperPage;
import com.automation.main.CreateNewCourseWindow;
import com.automation.main.CreateNewUserWindow;
import com.automation.main.DeleteMenu;
import com.automation.main.DriverSelector;
import com.automation.main.EditRecordinPropertiesWindow;
import com.automation.main.EmailAndConnectionSettingsPage;
import com.automation.main.EmailInboxPage;
import com.automation.main.EmailLoginPage;
import com.automation.main.EulaPage;
import com.automation.main.GetSupprtWindow;
import com.automation.main.HelpPage;
import com.automation.main.LoginHelperPage;
import com.automation.main.ManageAdHocCoursesMembershipWindow;
import com.automation.main.ManageAdhocCoursesEnrollmentsPage;
import com.automation.main.ManageAdhocUsersPage;
import com.automation.main.MoveWindow;
import com.automation.main.PlayerPage;
import com.automation.main.PublishWindow;
import com.automation.main.RecordingHelperPage;
import com.automation.main.RunDiagnosticsPage;
import com.gargoylesoftware.htmlunit.html.Keyboard;
import com.thoughtworks.selenium.condition.ConditionRunner.Context;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

public class TegrityAppiumPoc {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public EditRecordinPropertiesWindow erp_window;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	public DeleteMenu delete_menu;
	public MoveWindow move_window;
	WebDriver driver;
	AndroidDriver<WebElement> driver2;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	String currentCourse;
	String targetCourse;
	String clickedRecording;

	public AdminDashboardPage admin_dashboard_page;
	public ManageAdhocCoursesEnrollmentsPage mange_adhoc_course_enrollments;
	public CreateNewCourseWindow create_new_course_window;
	public ManageAdhocUsersPage mange_adhoc_users_page;
	public CreateNewUserWindow create_new_user_window;
	public ManageAdHocCoursesMembershipWindow mangage_adhoc_courses_membership_window;
	public AddAdditionalContentFileWindow add_additional_content_window;
	public AdvancedServiceSettingsPage advanced_services_setting_page;
	public HelpPage help_page;
	public CourseSettingsPage course_settings;
	public EmailAndConnectionSettingsPage email_setting;
	public EulaPage eula_page;
	public GetSupprtWindow support_window;
	public EmailLoginPage email_login;
	public EmailInboxPage email_inbox;
	public RunDiagnosticsPage run_diagnostics;
	public PlayerPage player_page;
	public PublishWindow publish_window;
	String instructor1;
	String instructor2;
	List<String> for_enroll;
	DesiredCapabilities capabilities;


	List<WebElement> list_duration ;
	List<WebElement> list_title;
	List<String> list_duration_names;
	List<String> list_title_names;
	List<String> list_date_recordrd_by_name;
	List<String> list_courses_names_string;
	List<WebElement> list_date_recorded_by ;
	List<WebElement> list_courses_name;
	List<String> list_courses_names;
	List<String> list_recording__names;




	@BeforeClass
	public void setUp() throws MalformedURLException {
		// Set up desired capabilities and pass the Android app-activity and
		// app-package to Appium
		capabilities = new DesiredCapabilities();
		capabilities.setCapability("BROWSER_NAME", "Android");
		capabilities.setCapability("VERSION", "5.0");
		capabilities.setCapability("deviceName", "nexus5_1");
		capabilities.setCapability("platformName", "Android");
		/// capabilities.setCapability("app",
		/// "/data/app/com.tegrity.gui-1/base.apk");
		capabilities.setCapability("resetKeyboard", true);
		capabilities.setCapability("unicodeKeyboard", true);
		capabilities.setCapability("appPackage", "com.tegrity.gui");
		// This package name of your app (you can get it from apk info app)
		capabilities.setCapability("appActivity", "com.tegrity.gui.SplashActivit"); // This
		// is
		// Launcher
		// activity
		// of
		// your
		// app
		// (you
		// can
		// get
		// it
		// from
		// apk
		// info
		// app)

	}

	@BeforeClass
	public void setup() {

		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		

		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		wait = new WebDriverWait(driver, 30);
		add_additional_content_window = PageFactory.initElements(driver,
				AddAdditionalContentFileWindow.class);
		email_setting = PageFactory.initElements(driver,
				EmailAndConnectionSettingsPage.class);
		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		course_settings = PageFactory.initElements(driver,
				CourseSettingsPage.class);
		wait = new WebDriverWait(driver, 30);
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		erp_window = PageFactory.initElements(driver,
				EditRecordinPropertiesWindow.class);
		admin_dashboard_page = PageFactory.initElements(driver,
				AdminDashboardPage.class);
		advanced_services_setting_page = PageFactory.initElements(driver,
				AdvancedServiceSettingsPage.class);
		mange_adhoc_course_enrollments = PageFactory.initElements(driver,
				ManageAdhocCoursesEnrollmentsPage.class);
		eula_page = PageFactory.initElements(driver, EulaPage.class);
		create_new_course_window = PageFactory.initElements(driver,
				CreateNewCourseWindow.class);
		support_window = PageFactory.initElements(driver, GetSupprtWindow.class);
		mange_adhoc_users_page = PageFactory.initElements(driver,
				ManageAdhocUsersPage.class);
		email_login = PageFactory.initElements(driver, EmailLoginPage.class);
		create_new_user_window = PageFactory.initElements(driver,
				CreateNewUserWindow.class);
		email_inbox = PageFactory.initElements(driver, EmailInboxPage.class);
		mangage_adhoc_courses_membership_window =
				PageFactory.initElements(driver,
						ManageAdHocCoursesMembershipWindow.class);
		help_page = PageFactory.initElements(driver, HelpPage.class);
		run_diagnostics = PageFactory.initElements(driver,
				RunDiagnosticsPage.class);
		player_page = PageFactory.initElements(driver, PlayerPage.class);
		publish_window = PageFactory.initElements(driver, PublishWindow.class);
	}

	@Test
	public void test21598() throws Exception {
		/// pre condition

		// 1.load page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		/// 2.login as user1
		String mobile = "doronins";
		tegrity.loginCoursesByParameter(mobile);
		course.waitForVisibility(course.active_courses_tab_button);
		//3.get all courses names
		list_courses_names=new ArrayList<String>();
		String[] courses=initializeCourseObject();

		Collections.addAll(list_courses_names, courses);
		///4.print courses names
		System.out.println("courses of user are:");
		for(String e:courses)
		{
			System.out.println(e);

		}

		List<Object>group = new ArrayList<Object>();		///5.explore recording in each course name
		initializeCourseObject();
		for(int i=0;i<courses.length;i++)
		{
			initializeCourseObject();
			course.selectCourseByName(list_courses_names.get(i));
			record.waitForVisibility(record.recordings_tab);
			Thread.sleep(4000);
			record.convertRecordingsListToNames();
			///	for(String add:record.recording_list_names)
			HashSet<String> list=new 	HashSet<String>();
			list.addAll(record.recording_list_names);
			
			if(record.student_recordings_tab.isDisplayed())
			{
		      record.clickOnStudentRecordingsTab();
			System.out.println("clicked on students tab");
			}
			else{
				System.out.println("no recordings in student tab");
			}
			Thread.sleep(3000);
		    record.convertRecordingsListToNames(); 
			///		    
			list.addAll(record.recording_list_names);																											
			group.add(list);
			driver.navigate().back();		
			course.waitForVisibility(course.first_course_button);
		}

		for(int i=0;i<3;i++)
			for(Object li:(HashSet<Object>)group.get(i))
			{  
				if(li!=null)
					System.out.println((String)li);
			}

		System.out.println("took all recording from each course for later compare");

		// 3.click on my account
		driver.findElement(By.id("MyAccountLink")).click();
		// 4.generate code
		Thread.sleep(2000);
		course.waitForVisibility(driver.findElement(By.id("GenerateCodeButton")));
		driver.findElement(By.id("GenerateCodeButton")).click();
		// 5.get string code
		Thread.sleep(2000);
		String code =
				driver.findElement(By.id("ConnectionCodeContainer")).getAttribute("value").toLowerCase();
		System.out.println(code);
		driver.quit();

		// // 6. This is Launcher activity of your app (you can get it from apk
		// // info app)
		// // Create RemoteWebDriver instance and connect to the Appium server
		// // It will launch the Calculator App in Android Device using the
		// // configurations specified in Desired Capabilities
		//		driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

		driver2 = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		wait = new WebDriverWait(driver2, 30);
		Thread.sleep(3000);
		Robot robot = new Robot();
		robot.mouseMove(500, 500);
		Thread.sleep(3000);
		/// MobileElement e = (MobileElement)
		driver2.findElement(By.id("decor_content_parent"));
		// e.swipe(SwipeElementDirection.LEFT, 2000);
		/// driver.findElement(By.id("connect_pager"));
		for (int i = 0; i < 10; i++) {

			try {
				//		 robot.keyPress(KeyEvent.VK_RIGHT);
				//		 Thread.sleep(200);
				//		 robot.keyRelease(KeyEvent.VK_RIGHT);
				driver2.pressKeyCode(22);
				Thread.sleep(200);// solution
				if (driver2.findElement(By.id("connect_edit_text")).isDisplayed())// check if visible
				{

					System.out.println("Clicked on right");
					Assert.assertTrue(true);
					break;
				}
			} catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		Thread.sleep(2000);
		/// 7.click on edit text and enter code to login
		driver2.findElement(By.id("connect_edit_text")).sendKeys(code);
		Thread.sleep(2000);
		///
		/// driver2.findElement(By.id("connect_edit_text")).sendKeys(Keys.ENTER);
		driver2.pressKeyCode(66);
		Thread.sleep(1000);
		// 8.verify "loading" appears and later dissappears
		WebElement message = driver2.findElement(By.id("message"));
		wait.until(ExpectedConditions.visibilityOf(message));
		if (message.getText().contains("Loading. Please wait…")) {
			System.out.println("message is visible");
			Assert.assertTrue(true);
		} else {
			System.out.println("message is not visible");
			Assert.assertTrue(false);
		}


		/// 9.click on viewed tab
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("message")));
		WebElement viewed_tab = driver2.findElement(By.name("VIEWED"));
		wait.until(ExpectedConditions.visibilityOf(viewed_tab));
		viewed_tab.click();
		System.out.println("clicked on viewed tab");
		WebElement first_video = driver2.findElement(By.id("SessionUnitImageView1"));
		wait.until(ExpectedConditions.visibilityOf(first_video));
		if (viewed_tab.isSelected()) {
			System.out.println("recordings are visible");
			Assert.assertTrue(true);
		} else {
			System.out.println("recordings are not visible");
			Assert.assertTrue(false);
		}
		/// 10.list all recordings
		list_duration = driver2.findElements(By.id("SessionDurationTextView1"));
		list_title = driver2.findElements(By.id("RecordingTitleTextView1"));
		list_duration_names = new ArrayList<String>();
		list_title_names = new ArrayList<String>();
		list_date_recordrd_by_name = new ArrayList<String>();
		list_courses_names_string = new ArrayList<String>();
		list_date_recorded_by = driver2.findElements(By.id("InstructorNameTextView1"));
		list_courses_name = driver2.findElements(By.id("CourseNameTextView1"));

		///verify recording displayed correctly
		veridyRecordingDisplayed(10);






		/// 9.click on New tab
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("message")));
		viewed_tab = driver2.findElement(By.name("NEW"));
		wait.until(ExpectedConditions.visibilityOf(viewed_tab));
		viewed_tab.click();
		System.out.println("clicked on NEW tab");
		first_video = driver2.findElement(By.id("SessionUnitImageView1"));
		wait.until(ExpectedConditions.visibilityOf(first_video));
		if (viewed_tab.isSelected()) {
			System.out.println("recordings are visible");
			Assert.assertTrue(true);
		} else {
			System.out.println("recordings are not visible");
			Assert.assertTrue(false);
		}
		//////lists for later
		list_duration = driver2.findElements(By.id("SessionDurationTextView1"));
		list_title = driver2.findElements(By.id("RecordingTitleTextView1"));
		list_duration_names = new ArrayList<String>();
		list_title_names = new ArrayList<String>();
		list_date_recordrd_by_name = new ArrayList<String>();
		list_courses_names_string = new ArrayList<String>();
		list_date_recorded_by = driver2.findElements(By.id("InstructorNameTextView1"));
		list_courses_name = driver2.findElements(By.id("CourseNameTextView1"));

		///verify recording displayed correctly

		veridyRecordingDisplayed(40);





		/// 9.click on New tab
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("message")));
		viewed_tab = driver2.findElement(By.name("POPULAR"));
		wait.until(ExpectedConditions.visibilityOf(viewed_tab));
		viewed_tab.click();
		System.out.println("clicked on POPULAR tab");
		first_video = driver2.findElement(By.id("SessionUnitImageView1"));
		wait.until(ExpectedConditions.visibilityOf(first_video));
		if (viewed_tab.isSelected()) {
			System.out.println("recordings are visible");
			Assert.assertTrue(true);
		} else {
			System.out.println("recordings are not visible");
			Assert.assertTrue(false);
		}
		//////lists for later
		list_duration = driver2.findElements(By.id("SessionDurationTextView1"));
		list_title = driver2.findElements(By.id("RecordingTitleTextView1"));
		list_duration_names = new ArrayList<String>();
		list_title_names = new ArrayList<String>();
		list_date_recordrd_by_name = new ArrayList<String>();
		list_courses_names_string = new ArrayList<String>();
		list_date_recorded_by = driver2.findElements(By.id("InstructorNameTextView1"));
		list_courses_name = driver2.findElements(By.id("CourseNameTextView1"));

		///verify recording displayed correctly

		veridyRecordingDisplayed(40);



		////click on button to courses menu

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("up")));
		driver2.findElementById("up").click();

		///verify courses menu title
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("coursesTitleTextView")));
		String course_menu_title=driver2.findElement(By.id("coursesTitleTextView")).getText();
		if(course_menu_title.equals("COURSES"))
		{
			System.out.println("COURSES is the courses menu title");
			Assert.assertTrue(true);
		}
		else
		{
			System.out.println("bad courses menu title");
			Assert.assertTrue(false);

		}


		List <WebElement> course_list=driver2.findElementsByClassName("android.widget.RelativeLayout");
		
		for(int i=0;i<course_list.size();i++)
		{
			System.out.println("Checking recording in course : "+courses[i]);
		Thread.sleep(1000);
        ///select course from menu
		clickAppiumCourseByName(courses[i],driver2);
		
		verifyAllRecordingExistInCourse(group.get(i),100);
		////click on button to courses menu

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("up")));
		driver2.findElementById("up").click();

		///verify courses menu title
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("coursesTitleTextView")));
		
		}

		///click on button to settings
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("android.widget.ImageButton")));
		driver2.findElementByClassName("android.widget.ImageButton").click();
		System.out.println("to setting menu");
		//click on setting
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("android.widget.LinearLayout")));
		driver2.findElementByClassName("android.widget.LinearLayout").click();
		System.out.println("clicked on setting");
		//click on sign out
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Sign Out")));
		driver2.findElementByName("Sign Out").click();
		System.out.println("clicked on sign-out");
		///click on ok
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("button1")));
		driver2.findElementById("button1").click();
	    System.out.println("clicked on ok");
		Thread.sleep(2000);
		
		///quit
		driver2.quit();
		System.out.println("Thank you!!!!!!!!!!!!!!!!!!");
		/////helper functions

	}

	@AfterClass
	public void teardown() {
		// close the app
		// driver.quit();
	}
/////helper functions
	public void veridyRecordingDisplayed(int num) throws Exception
	{
		String title="";
		String title_before="";
		String course="";
		String course_before="";
		String time="";
		String time_before="";
		if((driver2.findElementById("InstructorNameTextView1")==null)||(driver2.findElementById("RecordingTitleTextView1")==null)||(driver2.findElementById("CourseNameTextView1")==null)||(driver2.findElementById("SessionDurationTextView1")==null))
		{ System.out.println("list is empty....");
		Assert.assertTrue(true);
			return;
		}
		else
		{
		for(int i=0;i<num;i++)
		{




			///1.check recorder name
			if(driver2.findElementById("InstructorNameTextView1").isDisplayed())
			{
				System.out.println("recorder name is displayed");
				Assert.assertTrue(true);
			}
			else
			{
				System.out.println("recorder name is not displayed");
				Assert.assertTrue(false);
			}

			///2.check recording title name
			if(driver2.findElementById("RecordingTitleTextView1").isDisplayed())
			{
				System.out.println("recording title name is displayed");
				Assert.assertTrue(true);
			}
			else
			{
				System.out.println("recording title name is not displayed");
				Assert.assertTrue(false);
			}

			///3.check course name
			if(driver2.findElementById("CourseNameTextView1").isDisplayed())
			{
				System.out.println("course name is displayed");
				Assert.assertTrue(true);
			}
			else
			{
				System.out.println("course name is not displayed");
				Assert.assertTrue(false);
			}

			///check recorder name
			if(driver2.findElementById("SessionDurationTextView1").isDisplayed())
			{
				System.out.println("duration is displayed");
				Assert.assertTrue(true);
			}
			else
			{
				System.out.println("duration is not displayed");
				Assert.assertTrue(false);
			}

			list_date_recordrd_by_name.add(driver2.findElementById("InstructorNameTextView1").getText());

			list_title_names.add(driver2.findElementById("RecordingTitleTextView1").getText());
			title=driver2.findElementById("RecordingTitleTextView1").getText();


			list_courses_names_string.add(driver2.findElementById("CourseNameTextView1").getText());
			course=driver2.findElementById("CourseNameTextView1").getText();

			list_duration_names.add(driver2.findElementById("SessionDurationTextView1").getText());
			time=driver2.findElementById("SessionDurationTextView1").getText();

			///make it before refernce and current 
			if((title.equals(title_before))&&(course.equals(course_before))&&(time.equals(time_before)))
				return;
			else
			{
				if((i!=0)&&(i!=1))
				{
					title_before=title;
					course_before=course;
					time_before=time;
				}
			}
			driver2.pressKeyCode(20);
			Thread.sleep(500);
			driver2.pressKeyCode(20);


		}

		///last recording check 

		list_date_recordrd_by_name.add(driver2.findElementsById("InstructorNameTextView1").get(1).getText());

		list_title_names.add(driver2.findElementsById("RecordingTitleTextView1").get(1).getText());

		list_courses_names_string.add(driver2.findElementsById("CourseNameTextView1").get(1).getText());

		list_duration_names.add(driver2.findElementsById("SessionDurationTextView1").get(0).getText());
		///1.check recorder name
		if(driver2.findElementsById("InstructorNameTextView1").get(1).isDisplayed())
		{
			System.out.println("recorder name is displayed");
			Assert.assertTrue(true);
		}
		else
		{
			System.out.println("recorder name is not displayed");
			Assert.assertTrue(false);
		}

		///2.check recording title name
		if(driver2.findElementsById("RecordingTitleTextView1").get(1).isDisplayed())
		{
			System.out.println("recording title name is displayed");
			Assert.assertTrue(true);
		}
		else
		{
			System.out.println("recording title name is not displayed");
			Assert.assertTrue(false);
		}

		///3.check course name
		if(driver2.findElementsById("CourseNameTextView1").get(1).isDisplayed())
		{
			System.out.println("course name is displayed");
			Assert.assertTrue(true);
		}
		else
		{
			System.out.println("course name is not displayed");
			Assert.assertTrue(false);
		}

		///check recorder name
		if(driver2.findElementsById("SessionDurationTextView1").get(0).isDisplayed())
		{
			System.out.println("duration is displayed");
			Assert.assertTrue(true);
		}
		else
		{
			System.out.println("duration is not displayed");
			Assert.assertTrue(false);
		}



		System.out.println("All recordings are correct!!!!!!!");
		}
	}
	// description = "get courses list"
	public String[] initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getStringFromElement(course.course_list);
		return course.courses;
	}
	///clicks on courses's menu by it name
	public void clickAppiumCourseByName(String name,AndroidDriver driver)
	{
		
		if((driver!=null)&&(name!=null))
		{

			List <WebElement> course_list=driver.findElementsByClassName("android.widget.RelativeLayout");
			for(WebElement e:course_list)
			{   System.out.println("course:"+e.findElement(By.id("courseTextView")).getText());
				if(e.findElement(By.id("courseTextView")).getText().toLowerCase().equals(name.toLowerCase()))
			{
				try
				{
				e.findElement(By.id("courseIdView")).click();
			    System.out.println("click succeded!!!");
				Assert.assertTrue(true);

				}
			catch(Exception exception)
			{
				System.out.println("click failed...");
			
			}
				System.out.println("course found: "+name);
			    return;
			}
			}
		}
		else{
		System.out.println("failed finding course by name and clicking on it");
		Assert.assertTrue(false);
		}	
	}
	//verify all recording appear in the mobile course apperar in recordings
	 public void verifyAllRecordingExistInCourse(Object recordings,int num) throws Exception
	 {
			String title="";
			String title_before="";
			String recording;
			HashSet<String> list_title =new HashSet<String>();
			int counter=0;
		if(!((HashSet<String>)recordings).isEmpty())
		{	
			for(int i=0;i<num;i++)
			{



				recording=driver2.findElementById("RecordingTitleTextView1").getText();
			 ///  System.out.println(recording);
				if(!(list_title).contains(recording))
			        {
			        	list_title.add(recording);
					System.out.println("recording added successfully: "+recording);
			        
			        }
				////list_title_names.add(driver2.findElementById("RecordingTitleTextView1").getText());
				title=driver2.findElementById("RecordingTitleTextView1").getText();

				///make it before refernce and current 
				if((title.equals(title_before)))
						{
					     counter++;
						if((counter>=9))
						{
							break;
						}
						
						}
				else
				{
					counter=0;
					if((i!=0)&&(i!=1))
					{
						title_before=title;
					
					}
				}
				driver2.pressKeyCode(20);
				Thread.sleep(1000);
			//	driver2.pressKeyCode(20);


			}
		
	
        recording=driver2.findElementsById("RecordingTitleTextView1").get(1).getText();
        if(!list_title.contains(recording))
        {
        	list_title.add(recording);
		System.out.println("recording added successfully: "+recording);
        
        }

		
		////verify if recordings exist or not
			if(list_title.containsAll(((HashSet<String>) recordings)))
			{
				System.out.println("all recording exist for course");
			    Assert.assertTrue(true);
			}
			else
			{
				System.out.println("Not all recording exist for course!!!!!");	
			    Assert.assertTrue(false);
			}
			System.out.println(list_title.size());
		
		}
		else
		{
		try {
					if(driver2.findElementById("emptyList1").isDisplayed())
					{
						System.out.println("list is empty in mobile and website");
					if((driver2.findElementById("emptyList1").getText()).equals("There are no recordings in this course"))
					{
						System.out.println("correct message:"+driver2.findElementById("emptyList1").getText());
						 Assert.assertTrue(true);
					}
					else {
						System.out.println("bad message");
						 Assert.assertTrue(false);
					}
					}
					else
					{
						
						System.out.println("mobile has no courses while web course has!!!!!");	
						 Assert.assertTrue(false);
					}
					System.out.println("recordings number: "+list_title.size());	
		}
		catch(Exception exception)
		{
			System.out.println("There are no recordings in this course is not displayed");
	        Assert.assertTrue(false);
		}
		
		
		
		}

	 }

}
