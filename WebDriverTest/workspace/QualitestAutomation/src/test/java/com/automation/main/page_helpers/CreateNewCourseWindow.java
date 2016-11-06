package com.automation.main.page_helpers;

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
public class CreateNewCourseWindow extends Page {
	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public CreateNewCourseWindow(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id="ctl00_ContentPlaceHolder1_ucNewCourseControl_ucDialog_CourseIDTextBox")
	public WebElement course_id_input;
	@FindBy(id="ctl00_ContentPlaceHolder1_ucNewCourseControl_ucDialog_FolderNameTextBox") WebElement course_name_input;
	@FindBy(id="ctl00_ContentPlaceHolder1_ucNewCourseControl_ucDialog_btnOK") WebElement ok_button;
	@FindBy(id="ctl00_ContentPlaceHolder1_ucNewCourseControl_ucDialog_btnCancel") WebElement cancel_button;
	@FindBy(id="#ctl00_ContentPlaceHolder1_ucNewCourseControl_ucDialog_lblTitle") WebElement title_modal_window;
	
	public boolean setCourseId(String course_id) {
		try {
			course_id_input.sendKeys(course_id);
			return true;
		} catch (Exception msg) {
			return false;
		}
	}
	
	
	public boolean setCourseName(String course_name) {
		try {
			course_name_input.sendKeys(course_name);
			return true;
		} catch (Exception msg) {
			return false;
		}
		
	}
	
	public boolean clickOnOkButton() {
		try {
			ok_button.click();
			return true;
		} catch (Exception msg) {
			return false;
		}
	}
	
	public void createNewCourse(String course_name, String course_id) throws InterruptedException  {
//		wait.until(ExpectedConditions.visibilityOf(title_modal_window));
		setCourseName(course_name);
		wait.until(ExpectedConditions.textToBePresentInElementValue(course_name_input, course_name));
		setCourseId(course_id);
		wait.until(ExpectedConditions.textToBePresentInElementValue(course_id_input, course_id));
		clickOnOkButton();		
		System.out.println("New course created. Course name: " + course_name + ". Course id: " + course_id);
		waitForAlert(60);
	
	}
	
	

}
