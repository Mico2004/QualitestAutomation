package com.automation.main.page_helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
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
public class AdminDashboardPage extends Page {
	
	WebElement targetLink;
	
	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public AdminDashboardPage(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
		
		
		
	}

	@FindBy(css = "#CoursesBox>ul>li>a")
	public
	List<WebElement> courses_submenu;
	@FindBy(css = "#UsersBox>ul>li>a")
	public
	List<WebElement> users_submenu;
	@FindBy(css="#ServiceSettingsBox>ul>li>a") List<WebElement> services_setting_submenu;
	@FindBy (xpath="//*[@id='CoursesBox']/ul/li/a[text() = 'Manage Ad-hoc Courses / Enrollments (Course Builder)']") 
	WebElement manageAdHockCourses;
	@FindBy (xpath="//*[@id='CoursesBox']/ul/li/a[text() = 'View Course List']") 
	WebElement viewCourseList;
	@FindBy (xpath="//*[@id='CoursesBox']/ul/li/a[text() = 'Manage Course Settings']") 
	WebElement manageCourseSettings;
	@FindBy (xpath="//*[@id='CoursesBox']/ul/li/a[text() = 'Manage Excel Courses / Enrollments']") 
	WebElement ManageExcelCoursesEnrollments;
	@FindBy (xpath="//*[@id='CoursesBox']/ul/li/a[text() = 'View Archive']") 
	WebElement viewArchive;
	@FindBy (xpath="//*[@id='UsersBox']/ul/li/a[text() = 'Manage Admin Roles']") 
	WebElement manageAdminRoles;
	@FindBy (xpath="//*[@id='UsersBox']/ul/li/a[text() = 'Manage Admin Users']") 
	WebElement manageAdminUsers;
	@FindBy (xpath="//*[@id='UsersBox']/ul/li/a[text() = 'Manage Excel Users']") 
	WebElement manageExcelUsers;
	@FindBy (xpath="//*[@id='UsersBox']/ul/li/a[text() = 'Manage Self Registration Users']") 
	WebElement manageSelfRegistration;
	@FindBy (xpath="//*[@id='UsersBox']/ul/li/a[text() = 'Manage Ad-hoc Users (User Builder)']") 
	WebElement manageAdHocUsers;
	@FindBy (xpath="//*[@id='UsersBox']/ul/li/a[text() = 'Impersonate User']") 
	WebElement ImpersonateUser;
	
	@FindBy (xpath="//*[@id='ServiceSettingsBox']/ul/li/a[text() = 'Customize User Interface']") 
	WebElement customizeUserInterface;
	@FindBy (xpath="//*[@id='ServiceSettingsBox']/ul/li/a[text() = 'Set Recorder Enforced Login']") 
	WebElement setRecorderEnforcedLogin;
	@FindBy (xpath="//*[@id='ServiceSettingsBox']/ul/li/a[text() = 'Set Email and Connection Settings']") 
	WebElement setEmailandConnectionSettings;
	@FindBy (xpath="//*[@id='ServiceSettingsBox']/ul/li/a[text() = 'Advanced Service Settings']") 
	WebElement advancedServiceSettings;
	@FindBy (xpath="//*[@id='ServiceSettingsBox']/ul/li/a[text() = 'Reprocess Tegrity Recordings']") 
	WebElement reprocessTegrityRecordings;
	@FindBy (xpath=".//*[@id='IntegrationBox']/ul/li/a[text() = 'Manage AAIRS']")
	WebElement ManageAAIRS;
	@FindBy (id="StatusBox")
	WebElement StatusBox;
	@FindBy (id="RecordersRecordingsBox")
	WebElement RecordersRecordingsBox;
	@FindBy (id="UsersBox")
	WebElement UsersBox;
	@FindBy (id="ServiceSettingsBox")
	WebElement ServiceSettingsBox;
	@FindBy (id="EventsAlertsBox")
	WebElement EventsAlertsBox;
	@FindBy (id="CoursesBox")
	WebElement CoursesBox;
	@FindBy (id="IntegrationBox")
	WebElement IntegrationBox;
	List <WebElement> dashboardSections;
	String linkText="";
	
	// This function get String with the name of target submenu of Courses and clicks on it
	public void clickOnTargetSubmenuCourses(String target) 
	{
//		wait.until(ExpectedConditions.visibilityOf(courses_submenu.get(0)));

		try {
		System.out.println("clickOnTargetSubmenuCourses1");
		waitForVisibility(CoursesBox);
		System.out.println("clickOnTargetSubmenuCourses2");
	//	wait.until(ExpectedConditions.visibilityOfAllElements(dashboardSections));
		System.out.println("clickOnTargetSubmenuCourses3");
		Thread.sleep(2000);
		System.out.println("clickOnTargetSubmenuCourses4");
		
		switch(target){
		case "View Course List": 
			targetLink=viewCourseList;
			break;
		case "View Archive": 
			targetLink=viewArchive;
			break;
		case "Manage Course Settings": 
			targetLink=manageCourseSettings;
			break;
		case "Manage Ad-hoc Courses / Enrollments (Course Builder)": 
			targetLink=manageAdHockCourses;
			break;
		case "Manage Excel Courses / Enrollments":
			targetLink=ManageExcelCoursesEnrollments;
			break;
			
			default: targetLink=manageAdHockCourses;
		}
		linkText=targetLink.getText();
		System.out.println("clickOnTargetSubmenuCourses5");
				
					waitForVisibility(targetLink);
					System.out.println("clickOnTargetSubmenuCourses6");
					targetLink.click();					
					System.out.println("clickOnTargetSubmenuCourses7");
					System.out.println("Click on target submenu of Courses: "+linkText  );
					ATUReports.add("Click on target submenu of Courses:"+linkText, "Clicked on target submenu.", "Clicked on target submenu.", LogAs.PASSED, null);
					Assert.assertTrue(true);
					return;
				} catch (Exception msg) {
					System.out.println("Not click on target submenu of Courses: " + targetLink.getText().toString());
					System.out.println("ERROR msg: " + msg.getMessage());
					ATUReports.add("Click on target submenu of Courses:"+linkText, "Clicked on target submenu.", "Not click on target submenu.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
					return;
	
		}
		
	}


	// This function get String with the name of target submenu of Users and clicks on it
	public void clickOnTargetSubmenuUsers(String target) throws InterruptedException 
		
	{
//		wait.until(ExpectedConditions.visibilityOf(courses_submenu.get(0)));
		try {
		System.out.println("clickOnTargetSubmenuUsers1");
		waitForVisibility(CoursesBox);
		System.out.println("clickOnTargetSubmenuUsers2");
	//	wait.until(ExpectedConditions.visibilityOfAllElements(dashboardSections));
		System.out.println("clickOnTargetSubmenuUsers3");
		Thread.sleep(2000);
		System.out.println("clickOnTargetSubmenuUsers4");
		
		switch(target){
		case "Impersonate User": 
			targetLink=ImpersonateUser;
			break;
		case "Manage Ad-hoc Users (User Builder)": 
			targetLink=manageAdHocUsers;
			break;
		case "Manage Self Registration Users": 
			targetLink=manageSelfRegistration;
			break;
		case "Manage Excel Users": 
			targetLink=manageExcelUsers;
			break;
		case "Manage Admin Users": 
			targetLink=manageAdminUsers;
			break;		
		case "Manage Admin Roles": 
			targetLink=manageAdminRoles;
			break;
			default: targetLink=manageAdHocUsers;
		}
		linkText=targetLink.getText();
		System.out.println("clickOnTargetSubmenuUsers5");
				
					waitForVisibility(targetLink);
					System.out.println("clickOnTargetSubmenuUsers6");
					targetLink.click();					
					System.out.println("clickOnTargetSubmenuUsers7");
					System.out.println("clickOnTargetSubmenuUsers: "+linkText  );
					ATUReports.add("Click on target submenu of Users:"+linkText, "Clicked on target submenu.", "Clicked on target submenu.", LogAs.PASSED, null);
					Assert.assertTrue(true);
					return;
				} catch (Exception msg) {
					System.out.println("Not click on target submenu of Users: " + targetLink.getText().toString());
					System.out.println("ERROR msg: " + msg.getMessage());
					ATUReports.add("Click on target submenu of Users:"+linkText, "Clicked on target submenu.", "Not click on target submenu.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
					return;
		
		}
		
	}

	// This function get String with the name of target submenu of Service Settings and Maintenance and clicks on it
	public void clickOnTargetSubmenuAdvancedServices(String target) {

		try {
		System.out.println("clickOnTargetSubmenuAdvanceServiceSet1");
		waitForVisibility(ServiceSettingsBox);
		System.out.println("clickOnTargetSubmenuAdvanceServiceSet2");

		Thread.sleep(2000);
		System.out.println("clickOnTargetSubmenuAdvanceServiceSet4");
		
		switch(target){
		case "Customize User Interface": 
			targetLink=customizeUserInterface;
			break;
		case "Set Recorder Enforced Login": 
			targetLink=setRecorderEnforcedLogin;
			break;
		case "Set Email and Connection Settings": 
			targetLink=setEmailandConnectionSettings;
			break;
		case "Advanced Service Settings": 
			targetLink=advancedServiceSettings;
			break;
		case "Reprocess Tegrity Recordings": 
			targetLink=reprocessTegrityRecordings;
			break;		
			default: targetLink=advancedServiceSettings;
		}
		linkText=targetLink.getText();
		System.out.println("clickOnTargetSubmenuAdvanceServiceSet5");
				
					waitForVisibility(targetLink);
					System.out.println("clickOnTargetSubmenuAdvanceServiceSet6");
					targetLink.click();					
					System.out.println("clickOnTargetSubmenuAdvanceServiceSet7");
					System.out.println("clickOnTargetSubmenuAdvanceServiceSet: "+linkText  );
					ATUReports.add("Click on target submenu of AdvanceServiceSet:"+linkText, "Clicked on target submenu.", "Clicked on target submenu.", LogAs.PASSED, null);
					Assert.assertTrue(true);
					return;
				} catch (Exception msg) {
					System.out.println("Not click on target submenu of AdvanceServiceSet: " + targetLink.getText().toString());
					System.out.println("ERROR msg: " + msg.getMessage());
					ATUReports.add("Click on target submenu of AdvanceServiceSet:"+linkText, "Clicked on target submenu.", "Not click on target submenu.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
					return;			
		}
		
		
	}
	public void clickOnTargetSubmenuIntegration(String target) {
		
		try {
			waitForVisibility(IntegrationBox);
		switch(target){
			case "Manage AAIRS": 
				targetLink=ManageAAIRS;
				break;
			}	
		linkText=targetLink.getText();
		waitForVisibility(targetLink);
		System.out.println("clickOnTargetSubmenuIntegration6");
		targetLink.click();					
		System.out.println("clickOnTargetSubmenuIntegration7");
		System.out.println("Click on target submenu of Courses: "+linkText  );
		ATUReports.add("Click on target submenu of Courses:"+linkText, "Clicked on target submenu.", "Clicked on target submenu.", LogAs.PASSED, null);
		Assert.assertTrue(true);
		return;
		} catch (Exception msg) {
			System.out.println("Not click on target submenu of Courses: " + targetLink.getText().toString());
			System.out.println("ERROR msg: " + msg.getMessage());
			ATUReports.add("Click on target submenu of Courses:"+linkText, "Clicked on target submenu.", "Not click on target submenu.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
			return;
		}
}
	
	
	
}
