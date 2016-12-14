package com.automation.main.tags;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
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
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.EditRecordingPropertiesWindow;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.ManageAdHocCoursesMembershipWindow;
import com.automation.main.page_helpers.ManageAdhocCoursesEnrollmentsPage;
import com.automation.main.page_helpers.PublishWindow;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.page_helpers.TagMenu;
import com.automation.main.utilities.DriverSelector;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import junitx.util.PropertyManager;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC8089ValidatePublicTagsChangesToPrivateAfterInstrutorIsEnrolledToStudent {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
	public PublishWindow publish_window;
	public AdminDashboardViewCourseList admin_dashboard_view_course_list;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public CalendarPage calendarPage;
	public EditRecordingPropertiesWindow edit_recording_properties_window;
	public AdminDashboardPage admin_dash_board_page;
	public DeleteMenu delete_menu;
	WebDriver driver;
	WebDriverWait wait;
	public TagMenu tag_window;
	public static WebDriver thread_driver;
	public ConfirmationMenu confirm_menu;
	public CopyMenu copy;
	public ManageAdhocCoursesEnrollmentsPage manage_adhoc_courses_enrollement_page;
	public ManageAdHocCoursesMembershipWindow manage_AdHoc_courses_membership_window;
	DesiredCapabilities capability;
	String firstValidName,SecondVaildName;
	String validNewName;
	List<String> namesOfTags = new ArrayList<String>();
	@BeforeClass
	public void setup() {

			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			calendarPage = PageFactory.initElements(driver, CalendarPage.class);
			course = PageFactory.initElements(driver, CoursesHelperPage.class);
			admin_dash_board_page = PageFactory.initElements(driver, AdminDashboardPage.class);
			admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
			confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			tag_window= PageFactory.initElements(driver, TagMenu.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
			delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
			manage_adhoc_courses_enrollement_page = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);
			manage_AdHoc_courses_membership_window =PageFactory.initElements(driver, ManageAdHocCoursesMembershipWindow.class);
			
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC8089ValidatePublicTagsChangesToPrivateAfterInstrutorIsEnrolledToStudent at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC8089ValidatePublicTagsChangesToPrivateAfterInstrutorIsEnrolledToStudent at " + DateToStr, "Starting the test: TC8089ValidatePublicTagsChangesToPrivateAfterInstrutorIsEnrolledToStudent at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC8089 Validate public tags changes to private after INSTRUTOR is enrolled to STUDENT")
	public void test8089() throws InterruptedException {
		
		//1.Enter the university
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
				
		//2.pre test - Login as SuperUser
		tegrity.loginCourses("SuperUser");
			
		//copy one recording to the course Ba
		course.copyOneRecordingFromCourseStartWithToCourseStartWithOfType("BankValidRecording", "Ba", 0, record,copy, confirm_menu);
		course.verifyRecordingsStatusIsClear("BankValidRecording", 0,record);
		
		//3. sign out
		record.signOut();
		
		//4.pre test - Login as SuperUser
		tegrity.loginCourses("User1");
				
		//3.Click on certain course from the courses list.
		String current_course =course.selectCourseThatStartingWith("Ba");
		String url =  course.getCurrentUrlCoursePage(); 
								
		//4.Check one available recording checkbox. 
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
						
		//5.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
		record.clickOnRecordingTaskThenTag();
			
		//6.The "Tag" Dialog window is appeared.
		tag_window.waitForPageToLoad();
				
		//7.create tag for the test
		validNewName = "Tirosh";
		tag_window.createNewTag(validNewName);		
		
		//8.Click on the "Apply" button
		tag_window.saveAllTheInstractors();
		record.clickElementJS(tag_window.apply_button);
				
		//9. sign out
		record.signOut();
	
		//10.Login as STUDENT 
		tegrity.loginCourses("User4");
			
		//11.Click on certain course from the courses list.
		course.selectCourseThatStartingWith(current_course);
		
		//12.Validate that the INSTRUCTOR's public recording tags are displayed.
		record.verifyTagApperedUderTheSelectRecording(validNewName);
		
		//13. sign out
		record.signOut();
		
		//14.Login as ADMIN
		tegrity.loginAdmin("Admin");
			
		//15.Click on "view course list" under "courses" section.
		admin_dash_board_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");	
		
		//16."Courses Builder" page is displayed.
		manage_adhoc_courses_enrollement_page.waitForThePageToLoad();
		
		//17.Enroll the preconditional INSTRUCTOR as STUDENT to the same course. 
		//Search the preconditional course.
		manage_adhoc_courses_enrollement_page.searchAndFilterCourses(current_course);
		String user1 = PropertyManager.getProperty("User1");
							
		//18.Unroll the INSTRUCTOR to the same course. 
		manage_adhoc_courses_enrollement_page.clickOnFirstCourseMembershipButton();
		manage_AdHoc_courses_membership_window.selectIrUserFromUserList(manage_AdHoc_courses_membership_window.instructor_elements_list, user1);
		manage_AdHoc_courses_membership_window.clickOnRemoveSelectedUserToInstructorList();
		
		//19.Enroll the INSTRUCTOR as STUDENT to the same course.			
		manage_AdHoc_courses_membership_window.searchForUser(user1);
		manage_AdHoc_courses_membership_window.selectFirstUserFromUserList();
		manage_AdHoc_courses_membership_window.clickOnAddSelectedUserToStudentList();	
		manage_AdHoc_courses_membership_window.clickOnOkButton();
		
		//20.Sign out
		manage_adhoc_courses_enrollement_page.exitInnerFrame();
		record.signOut();
		
		//21.Login as enroled STUDENT(not the previous INSTRUCTOR) to the preconditional course. 
		tegrity.loginCourses("User4");
								
		//22.Click on certain course from the courses list.
		course.selectCourseThatStartingWith(current_course);
						
		//23.Validate that the INSTRUCTOR's public recording tags are not displayed.
		record.verifyTagNotApperedUderTheSelectRecordings();
		
		//24.sign out
		record.signOut();
		
		//post test , return the user to the original Enrollment
		//25.Login as ADMIN
		tegrity.loginAdmin("Admin");
					
		//26.Click on "view course list" under "courses" section.
		admin_dash_board_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");	
				
		//27."Courses Builder" page is displayed.
		manage_adhoc_courses_enrollement_page.waitForThePageToLoad();
				
		//28.Enroll the preconditional INSTRUCTOR as STUDENT to the same course. 
		//Search the preconditional course.
		manage_adhoc_courses_enrollement_page.searchAndFilterCourses(current_course);
										
		//29.Unroll the STUDENT to the same course. 
		manage_adhoc_courses_enrollement_page.clickOnFirstCourseMembershipButton();
		manage_AdHoc_courses_membership_window.selectIrUserFromUserList(manage_AdHoc_courses_membership_window.student_elements_list, user1);
		manage_AdHoc_courses_membership_window.clickOnRemoveSelectedUserToStudentsList();
		
		//30.Enroll the INSTRUCTOR as INSTRUCTOR to the same course.
		manage_AdHoc_courses_membership_window.searchForUser(user1);
		manage_AdHoc_courses_membership_window.selectFirstUserFromUserList();
		manage_AdHoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
		manage_AdHoc_courses_membership_window.clickOnOkButton();
		
		//31.Sign out
		manage_adhoc_courses_enrollement_page.exitInnerFrame();
		record.signOut();
		
		//32.Post test - Login as INSTRUCTOR 
		tegrity.loginCourses("User1");
		
		//33.Click on certain course from the courses list.
		course.selectCourseThatStartingWith("Ba"); 
										
		//34.Check one available recording checkbox. 
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
								
		//35.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
		record.clickOnRecordingTaskThenTag();
		
		//36.The "Tag" Dialog window is appeared.
		tag_window.waitForPageToLoad();
		tag_window.verifyTagWindowOpen();
					
		//37.delete all the tag for the next test
		tag_window.deleteAllExistingTags();
							
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
			
		}

}