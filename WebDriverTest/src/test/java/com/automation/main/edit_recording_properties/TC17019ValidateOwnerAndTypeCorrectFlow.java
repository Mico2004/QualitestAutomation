package com.automation.main.edit_recording_properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.AdminDashboardViewCourseList;
import com.automation.main.page_helpers.CalendarPage;
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.EditRecordinPropertiesWindow;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.ManageAdHocCoursesMembershipWindow;
import com.automation.main.page_helpers.ManageAdhocCoursesEnrollmentsPage;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import junitx.util.PropertyManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC17019ValidateOwnerAndTypeCorrectFlow  {


	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
	public EditRecordinPropertiesWindow edit_recording_properties_window;
	public AdminDashboardViewCourseList admin_dashboard_view_course_list;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	public CalendarPage calendarPage;
	public AdminDashboardPage admin_dash_board_page;
	public ManageAdhocCoursesEnrollmentsPage manage_adhoc_courses_enrollments_page;
	public ManageAdHocCoursesMembershipWindow manage_AdHoc_courses_membership_window;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	DesiredCapabilities capability;
    
	@BeforeClass
	public void setup() {

			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			calendarPage = PageFactory.initElements(driver, CalendarPage.class);
			admin_dash_board_page = PageFactory.initElements(driver, AdminDashboardPage.class);
			admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
			manage_adhoc_courses_enrollments_page = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);
			manage_AdHoc_courses_membership_window =PageFactory.initElements(driver, ManageAdHocCoursesMembershipWindow.class);
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
			edit_recording_properties_window = PageFactory.initElements(driver, EditRecordinPropertiesWindow.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);

		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC17019ValidateOwnerAndTypeCorrectFlow at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC17019ValidateOwnerAndTypeCorrectFlow at " + DateToStr, "Starting the test: TC17019ValidateOwnerAndTypeCorrectFlow at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC17019 Validate Owner and type correct flow")
	public void test17019() throws InterruptedException, ParseException {
		
		
		//1.log in courses page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		tegrity.loginCourses("User1");
		initializeCourseObject();
		
		//2.open some course whom listed you as instructor
		String CourseName = course.selectCourseThatStartingWith("Ab");
		
	
		//3.Check some recording respective checkbox 
		int recordNumber = record.checkExistenceOfNonEditRecordingsStatusInRecordings();
		record.selectIndexCheckBox(recordNumber);

		//4.click on the recording tasks->edit recording properties option
		record.toEditRecordingPropertiesMenu();
			
		//5.wait for edit recording properties window to open
		edit_recording_properties_window.waitForPageToLoad();
			
		//6.Click on the "Owner" drop down button
		//7.The drop down edit box contains only INSTRUCTORS users.	
		edit_recording_properties_window.addOwnersToList("Instractor");
		edit_recording_properties_window.verifyThatAllTheTypeInTheDropDownList();
		
		//get the Instructor User after changing the owner for verify later
		String InstructorUser =  edit_recording_properties_window.getOwnerName("SuperUser");
		
		//8.Choose the "Student Recording" option from the Type drop down list
		edit_recording_properties_window.ChooseDiffrenetType("Student recording");
		edit_recording_properties_window.verifyThatTheTypeWasChoosen("Student recording");
		
		//9.The Owner drop down list consists only from STUDENTS users.
		edit_recording_properties_window.addOwnersToList("Student");
		edit_recording_properties_window.verifyThatAllTheTypeInTheDropDownList();
		
		//get the record by after changing the owner for verify later
		String StudentUser =edit_recording_properties_window.getOwnerName("User4");
							
		//10.Choose the "Proctoring Recordings" option from the Type drop down list
		edit_recording_properties_window.ChooseDiffrenetType("Proctoring recording");
		edit_recording_properties_window.verifyThatTheTypeWasChoosen("Proctoring recording");
		
		//11.The Owner drop down list is consist from all INSTRUCTORS and STUDENTS users.
		edit_recording_properties_window.addOwnersToList("Proctoring");
		edit_recording_properties_window.verifyThatAllTheTypeInTheDropDownList();
		
		//12.Choose the "Regular Recordings" Option from the type drop down list
		edit_recording_properties_window.ChooseDiffrenetType("Regular recording");
		edit_recording_properties_window.verifyThatTheTypeWasChoosen("Regular recording");
		
		//13.The Owner drop down list consists only from INSTRUCTORS users.
		edit_recording_properties_window.addOwnersToList("Instractor");
		edit_recording_properties_window.verifyThatAllTheTypeInTheDropDownList();
			
		//click on the cancel button for the next test 
		edit_recording_properties_window.clickElement(edit_recording_properties_window.cancel_button);
				
		//14.Sign out
		record.signOut();
		
		//15.login as Admin
		tegrity.loginAdmin("Admin");
		
		//16.Click on the 'Ad Hock courses and enrollments' link
		admin_dash_board_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
		
		//17.Unenroll both users from the course
		//search the right course that start with Ab
		manage_adhoc_courses_enrollments_page.waitForThePageToLoad();
		manage_adhoc_courses_enrollments_page.searchAndFilterCourses(CourseName);
		
		//press on membership of the first course and remove the student and the instructor
		manage_adhoc_courses_enrollments_page.clickOnFirstCourseMembershipButton();
		manage_AdHoc_courses_membership_window.selectIrUserFromUserList(manage_AdHoc_courses_membership_window.instructor_elements_list, InstructorUser);
		manage_AdHoc_courses_membership_window.clickOnRemoveSelectedUserToInstructorList();
		
		manage_AdHoc_courses_membership_window.selectIrUserFromUserList(manage_AdHoc_courses_membership_window.student_elements_list, StudentUser);
		manage_AdHoc_courses_membership_window.clickOnRemoveSelectedUserToStudentsList();	
		manage_AdHoc_courses_membership_window.clickOnOkButton();
		
		//14.Sign out
		manage_adhoc_courses_enrollments_page.exitInnerFrame();
		record.signOut();
		
		//15.Login as the instructor you've just unenrolled
		tegrity.loginCoursesByParameter(InstructorUser);
		
		//16.Sign out
		record.signOut();
				
		//17.Login as the Student you've just unenrolled
		tegrity.loginCoursesByParameter(StudentUser);
				
		//18.Sign out
		record.signOut();
		
		//19.Login as the initial Instructor 
		tegrity.loginCourses("User1");
		
		//20.Open the same course
		course.selectCourseThatStartingWith(CourseName);
		
		//21.Click on the same recording respective check box and than on 'Recording Tasks - Edit Recording Properties'
		record.selectIndexCheckBox(recordNumber);
		record.toEditRecordingPropertiesMenu();
					
		//22.wait for edit recording properties window to open
		edit_recording_properties_window.waitForPageToLoad();
		
		//23.Choose the regular recording type and make sure the unenrolled Instructor isn't on the list of owenrs
		edit_recording_properties_window.verifyUserIsNotOnTheOwnerList(InstructorUser);
				
		//24.Choose Student recording
		edit_recording_properties_window.ChooseDiffrenetType("Student recording");
		edit_recording_properties_window.verifyThatTheTypeWasChoosen("Student recording");
		
		//25.make sure the unenrolled Instructor isn't on the list of owenrs
		edit_recording_properties_window.verifyUserIsNotOnTheOwnerList(StudentUser);
		
		//26.Choose the proctoring recording type and make sure the both of the unenrolled student and instructor aren't on the list
		edit_recording_properties_window.ChooseDiffrenetType("Proctoring recording");
		edit_recording_properties_window.verifyThatTheTypeWasChoosen("Proctoring recording");
				
		//27.both of the unenrolled student and instructor aren't on the list
		edit_recording_properties_window.verifyUserIsNotOnTheOwnerList(InstructorUser);
		edit_recording_properties_window.verifyUserIsNotOnTheOwnerList(StudentUser);
				
		//click on the cancel button for the next test 
		edit_recording_properties_window.clickElement(edit_recording_properties_window.cancel_button);
						
		//28.Sign out
		record.signOut();
		
		//29.After test , enrolled user again
		tegrity.loginAdmin("Admin");
				
		//30.Click on the 'Ad Hock courses and enrollments' link
		admin_dash_board_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
			
		//31.enroll both users from the course
		//search the right course that start with Ab
		manage_adhoc_courses_enrollments_page.waitForThePageToLoad();
		manage_adhoc_courses_enrollments_page.searchAndFilterCourses(CourseName);
		
		//press on membership of the first course and remove the student and the instructor
		manage_adhoc_courses_enrollments_page.clickOnFirstCourseMembershipButton();
		
		manage_AdHoc_courses_membership_window.searchForUser(InstructorUser);
		manage_AdHoc_courses_membership_window.selectFirstUserFromUserList();
		manage_AdHoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
		
		manage_AdHoc_courses_membership_window.searchForUser(StudentUser);
		manage_AdHoc_courses_membership_window.selectFirstUserFromUserList();
		manage_AdHoc_courses_membership_window.clickOnAddSelectedUserToStudentList();
		
		manage_AdHoc_courses_membership_window.clickOnOkButton();
		
		//31.Sign out
		manage_adhoc_courses_enrollments_page.exitInnerFrame();
		record.signOut();
				
		//32.Login as the instructor you've just enrolled
		tegrity.loginCoursesByParameter(InstructorUser);
				
		//33.Sign out
		record.signOut();
						
		//34.Login as the Student you've just enrolled
		tegrity.loginCoursesByParameter(StudentUser);
						
		//35.Sign out
		record.signOut();
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}

	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}
}
