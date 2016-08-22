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

	public void clickOnTargetSubmenuCourses(String target) {
//		wait.until(ExpectedConditions.visibilityOf(courses_submenu.get(0)));
		for (int i = 0; i < courses_submenu.size(); i++) {
			if (courses_submenu.get(i).getText().equals(target)) {
				try {
					courses_submenu.get(i).click();
					System.out.println("Click on target: " + target);
					break;
				} catch (Exception msg) {
					System.out.println("Not click on target: " + target);
					System.err.println("ERROR msg: " + msg);
				}

			}
		}
	}

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
					System.out.println("Click on target: " + target);
					break;
				} catch (Exception msg) {
					System.out.println("Not click on target: " + target);
					System.err.println("ERROR msg: " + msg);
				}

			}
		}
	}

	// This function go to admin dashboard by using its url
	public void goToAdminDashboard() {
		String login_url = driver.getCurrentUrl();
		String university_name = login_url.split("/")[2].substring(0, login_url.split("/")[2].length() - 12);
		String course_page_url = "https://" + university_name + ".tegrity.com/#/dashboard";
		driver.navigate().to(course_page_url);
	}

	public void clickOnTargetSubmenuAdvancedServices(String target) {

		for (int i = 0; i < services_setting_submenu.size(); i++) {
			if (services_setting_submenu.get(i).getText().equals(target)) {
				try {
					services_setting_submenu.get(i).click();
					System.out.println("Click on target: " + target);
					break;
				} catch (Exception msg) {
					System.out.println("Not click on target: " + target);
					System.err.println("ERROR msg: " + msg);
				}

			}
		}
	}
}
