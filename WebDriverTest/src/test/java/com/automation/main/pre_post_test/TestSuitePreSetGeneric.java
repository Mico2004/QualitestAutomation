package com.automation.main.pre_post_test;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.CreateNewCourseWindow;
import com.automation.main.page_helpers.CreateNewUserWindow;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.EditRecordinPropertiesWindow;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.ManageAdHocCoursesMembershipWindow;
import com.automation.main.page_helpers.ManageAdhocCoursesEnrollmentsPage;
import com.automation.main.page_helpers.ManageAdhocUsersPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import junitx.util.PropertyManager;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TestSuitePreSetGeneric {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	public DeleteMenu delete_menu;
	public MoveWindow move_window;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	String currentCourse;
	String targetCourse;
	String clickedRecording;
	DesiredCapabilities capability;
	public AdminDashboardPage admin_dashboard_page;
	public ManageAdhocCoursesEnrollmentsPage mange_adhoc_course_enrollments;
	public CreateNewCourseWindow create_new_course_window;
	public ManageAdhocUsersPage mange_adhoc_users_page;
	public CreateNewUserWindow create_new_user_window;
	public ManageAdHocCoursesMembershipWindow mangage_adhoc_courses_membership_window;
	public EditRecordinPropertiesWindow erp_window;
	

	
	public  TestSuitePreSetGeneric(WebDriver driver) {
		
		//ATUReports.setWebDriver(driver);
		System.out.println("Start");
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		mange_adhoc_course_enrollments = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);
		create_new_course_window = PageFactory.initElements(driver, CreateNewCourseWindow.class);
		mange_adhoc_users_page = PageFactory.initElements(driver, ManageAdhocUsersPage.class);
		create_new_user_window = PageFactory.initElements(driver, CreateNewUserWindow.class);
		admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);
		mangage_adhoc_courses_membership_window = PageFactory.initElements(driver,ManageAdHocCoursesMembershipWindow.class);
		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		wait = new WebDriverWait(driver, 30);
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		erp_window = PageFactory.initElements(driver, EditRecordinPropertiesWindow.class);
		System.out.println("End");
	}

	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		
		course.courses = course.getCoursesListFromElement(course.course_list);
	}

	
	public void GenricPreset(Map<String,List<Integer>> destinationCoursesAndTypeOfRecording ) throws InterruptedException {
		System.out.println("Start1");
			String currentCourse="";
			List <Integer> contentTypes=new ArrayList<Integer>();
			List<String> CoursesForRegularRecordings=new ArrayList<String>();
			List<String> CoursesForStudentRecordings=new ArrayList<String>();
			List<String> CoursesForProcRecordings=new ArrayList<String>();
			List<String> CoursesForAdditionalContent=new ArrayList<String>();
		
			System.out.println("Start2");
			tegrity.loginCourses("SuperUser");// log in courses page
			System.out.println("Start3");
			//initializeCourseObject();
			System.out.println("Start4");
			Iterator itForClearingCourseContent = destinationCoursesAndTypeOfRecording.entrySet().iterator();
			
			 // Iterating the course\courses list, deleting their content and determine which type of content should be copied to each course
		    while (itForClearingCourseContent.hasNext()) {
		    	Map.Entry pair = (Map.Entry) itForClearingCourseContent.next();
		    	System.out.println(pair.getKey() + " = " + pair.getValue());
		    	currentCourse = pair.getKey().toString();
				contentTypes = (List<Integer>) pair.getValue();				
				course.selectCourseThatStartingWith(currentCourse);				
				boolean additionalExist = record.tabExists(1);
				boolean StudentExist = record.tabExists(2);
				boolean TestExist = record.tabExists(3);
				System.out.println("AdditionalExist: "+additionalExist + " StudentExist: " + StudentExist + " TestExist: " + TestExist);
				record.returnToCourseListPage(course);
				System.out.println("GenricPreset1");
				course.deleteAllRecordingsInCourseStartWith(currentCourse, 0, record, delete_menu);
				System.out.println("GenricPreset2");
				if (additionalExist)
						course.deleteAllRecordingsInCourseStartWith(currentCourse, 1, record, delete_menu);
				System.out.println("GenricPreset3");
				if (StudentExist)
					course.deleteAllRecordingsInCourseStartWith(currentCourse, 2, record, delete_menu);
				System.out.println("GenricPreset4");
				if (TestExist)
					course.deleteAllRecordingsInCourseStartWith(currentCourse, 3, record, delete_menu);
				System.out.println("GenricPreset5");	
				
				
				/*currentCourse = pair.getKey().toString();
				contentTypes = (List<Integer>) pair.getValue();*/
				System.out.println("Key: "+currentCourse);
				/*	System.out.println("value: "+contentTypes.get(0)+" "+contentTypes.get(1)+" "+contentTypes.get(2));*/
				if (contentTypes.contains(0))
					CoursesForRegularRecordings.add(currentCourse);				
				if (contentTypes.contains(1))
					CoursesForAdditionalContent.add(currentCourse);			
				if (contentTypes.contains(2))
					CoursesForStudentRecordings.add(currentCourse);				
				if (contentTypes.contains(3))
					CoursesForProcRecordings.add(currentCourse);
				
				itForClearingCourseContent.remove(); // avoids a ConcurrentModificationException
		}
	
		 // Copy neccessary recordings from bank to course\courses
			System.out.println("gen1");
			if(CoursesForRegularRecordings.size()!=0)
				course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValidRecording", CoursesForRegularRecordings, 0, record,copy, confirm_menu);
			Thread.sleep(4000);
			System.out.println("a6");
			if(CoursesForAdditionalContent.size()!=0)
			course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValidRecording", CoursesForAdditionalContent, 1, record,copy, confirm_menu);
			Thread.sleep(4000);
			System.out.println("a7");
			if(CoursesForStudentRecordings.size()!=0)
			course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValidRecording", CoursesForStudentRecordings, 2, record,copy, confirm_menu);
			Thread.sleep(4000);
			System.out.println("a7");
			if(CoursesForProcRecordings.size()!=0)
			course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValidRecording", CoursesForProcRecordings, 3, record,copy, confirm_menu);
			Thread.sleep(4000);	
			
			// verify recordings status is clear from the bank
			System.out.println("0");
			if(CoursesForRegularRecordings!=null)
			course.verifyRecordingsStatusIsClear("BankValidRecording", 0,record);
			System.out.println("1"); Thread.sleep(4000);
			if(CoursesForStudentRecordings!=null)
			course.verifyRecordingsStatusIsClear("BankValidRecording", 2,record); 
			System.out.println("3"); Thread.sleep(4000);
			if(CoursesForProcRecordings!=null)
			course.verifyRecordingsStatusIsClear("BankValidRecording", 3,record); 
			System.out.println("4"); Thread.sleep(4000);			
			    
	

	}
}
