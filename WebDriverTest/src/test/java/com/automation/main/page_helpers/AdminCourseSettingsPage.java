package com.automation.main.page_helpers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.io.filefilter.TrueFileFilter;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
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
public class AdminCourseSettingsPage extends Page {
	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public AdminCourseSettingsPage(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "PubliclyVisibleAvaliable") WebElement checkbox_lock_of_make_this_course_publicly_visible;
	@FindBy(id = "PubliclyVisible") WebElement checkbox_on_off_of_make_this_course_publicly_visible;
	@FindBy(id = "SaveButton") WebElement save_button;

	
	
	// This function insure that lock of make this course public selected
	public void makeSureThatLockMakeThisCoursePublicSelected() {
		forceWebElementToBeSelected(checkbox_lock_of_make_this_course_publicly_visible, "Lock of make this course public");
	}
	
	// This function insure that lock of make this course public unselected
	public void makeSureThatLockMakeThisCoursePublicUnSelected() {
		forceWebElementToBeUnselected(checkbox_lock_of_make_this_course_publicly_visible, "Lock of make this course public");
	}
	
	// This function insure that make this course public is on
	public void makeSureThatOnOffMakeThisCoursePublicSelected() {
		forceWebElementToBeUnselected(checkbox_on_off_of_make_this_course_publicly_visible, "Make this course public is on");
	}
	
	// This function insure that make this course public is off
	public void makeSureThatOnOffMakeThisCoursePublicUnSelected() {
		forceWebElementToBeUnselected(checkbox_on_off_of_make_this_course_publicly_visible, "Make this course public is off");
	}
	
	// This function clicks on save button
	public void clickOnSaveButton() {
		try {
			save_button.click();
			System.out.println("Clicked on save button.");
			ATUReports.add("Clicked on save button.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch(Exception msg) {
			System.out.println("Fail to click on save button.");
			ATUReports.add("Clicked on save button.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	
	
}
