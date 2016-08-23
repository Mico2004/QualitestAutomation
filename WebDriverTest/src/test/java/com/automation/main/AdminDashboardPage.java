package com.automation.main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
	@FindBy (xpath="//*[@id='CoursesBox']/ul/li[4]/a") WebElement manageAdHockCourses;

	
	// This function get String with the name of target submenu of Courses and clicks on it
	public void clickOnTargetSubmenuCourses(String target) {
//		wait.until(ExpectedConditions.visibilityOf(courses_submenu.get(0)));
		for (int i = 0; i < courses_submenu.size(); i++) {
			if (courses_submenu.get(i).getText().equals(target)) {
				try {
					Thread.sleep(1500);
					waitForVisibility(courses_submenu.get(i));
					courses_submenu.get(i).click();					
					System.out.println("Click on target submenu of Courses: " + target);
					ATUReports.add("Click on target submenu of Courses.", "Clicked on target submenu.", "Clicked on target submenu.", LogAs.PASSED, null);
					return;
				} catch (Exception msg) {
					System.out.println("Not click on target submenu of Courses: " + target);
					System.out.println("ERROR msg: " + msg.getMessage());
					ATUReports.add("Click on target submenu of Courses.", "Clicked on target submenu.", "Not click on target submenu.", LogAs.FAILED, null);
					return;
				}

			}
		}
		
		System.out.println("Not found the target submenu to click on: " + target);
		ATUReports.add("Click on target submenu of Courses.", "Clicked on target submenu.", "Target submenu not found.", LogAs.FAILED, null);
		Assert.assertTrue(false);
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
