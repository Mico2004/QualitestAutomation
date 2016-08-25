package com.automation.main;

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

	@FindBy (xpath="//*[@id='CoursesBox']/ul/li/a[text() = 'View Archive']") 
	WebElement viewArchive;

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
	public void clickOnTargetSubmenuCourses(String target) {
//		wait.until(ExpectedConditions.visibilityOf(courses_submenu.get(0)));






		try {
		System.out.println("clickOnTargetSubmenuCourses1");
		waitForVisibility(CoursesBox);
		System.out.println("clickOnTargetSubmenuCourses2");
	//	wait.until(ExpectedConditions.visibilityOfAllElements(dashboardSections));
		System.out.println("clickOnTargetSubmenuCourses3");
		Thread.sleep(4000);
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
					ATUReports.add("Click on target submenu of Courses:"+linkText, "Clicked on target submenu.", "Not click on target submenu.", LogAs.FAILED, null);
					Assert.assertTrue(false);
					return;

				


			
		}
		



		
	}

	
	// This function get String with the name of target submenu of Users and clicks on it
	public void clickOnTargetSubmenuUsers(String target) throws InterruptedException {
		
//		wait.until(ExpectedConditions.visibilityOfAllElements(users_submenu));
//		wait.until(ExpectedConditions.visibilityOf(users_submenu.get(0)));
//		wait.until(ExpectedConditions.visibilityOf(users_submenu.get(1)));
		
		for(int i=0; i<10; i++) {
			try {
				if(users_submenu.size()>0) {
					break;
				}
				Thread.sleep(1000);
			} catch (Exception msg) {
				Thread.sleep(1000);
			}
		}

		for (int i = 0; i < users_submenu.size(); i++) {
			
			if (users_submenu.get(i).getText().equals(target)) {
				try {
					users_submenu.get(i).click();
					System.out.println("Click on target submenu of Users: " + target);
					ATUReports.add("Click on target submenu of Users.", "Clicked on target submenu.", "Clicked on target submenu.", LogAs.PASSED, null);
					return;
				} catch (Exception msg) {
					System.out.println("Not click on target submenu of Users menu: " + target);
					System.out.println("ERROR msg: " + msg);
					ATUReports.add("Click on target submenu of Users.", "Clicked on target submenu.", "Not clicked on target submenu.", LogAs.FAILED, null);
					return;
				}

			}
		}
		
		
		
		System.out.println("Not found the target submenu to click on: " + target);
		ATUReports.add("Click on target submenu of Users.", "Clicked on target submenu.", "Target submenu not found.", LogAs.FAILED, null);
		Assert.assertTrue(false);
	}

	// This function go to admin dashboard by using its url
	public void goToAdminDashboard() {
		String login_url = driver.getCurrentUrl();
		String university_name = login_url.split("/")[2].substring(0, login_url.split("/")[2].length() - 12);
		String course_page_url = "https://" + university_name + ".tegrity.com/#/dashboard";
		driver.navigate().to(course_page_url);
	}

	
	// This function get String with the name of target submenu of Service Settings and Maintenance and clicks on it
	public void clickOnTargetSubmenuAdvancedServices(String target) {

		for (int i = 0; i < services_setting_submenu.size(); i++) {
			if (services_setting_submenu.get(i).getText().equals(target)) {
				try {
					services_setting_submenu.get(i).click();
					System.out.println("Click on target submenu of Service Settings and Maintenance: " + target);
					ATUReports.add("Click on target submenu of Service Settings and Maintenance.", "Clicked on target submenu.", "Clicked on target submenu.", LogAs.PASSED, null);
					return;
				} catch (Exception msg) {
					System.out.println("Not click on target submenu of Serivce Settings and Maintenance: " + target);
					System.out.println("ERROR msg: " + msg);
					ATUReports.add("Click on target submenu of Service Settings and Maintenance.", "Clicked on target submenu.", "Not clicked on target submenu.", LogAs.FAILED, null);
					return;
				}

			}
		}
		
		System.out.println("Not found the target submenu to click on: " + target);
		ATUReports.add("Click on target submenu of Service Settings and Maintenance.", "Clicked on target submenu.", "Target submenu not found.", LogAs.FAILED, null);
		Assert.assertTrue(false);
	}


	
	
	
}
