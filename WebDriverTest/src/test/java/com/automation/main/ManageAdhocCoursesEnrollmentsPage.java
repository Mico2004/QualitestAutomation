package com.automation.main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.tools.ant.taskdefs.Javadoc;
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

import com.gargoylesoftware.htmlunit.javascript.host.event.RTCDataChannelEvent;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class ManageAdhocCoursesEnrollmentsPage extends Page {
	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public ManageAdhocCoursesEnrollmentsPage(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "ctl00_ContentPlaceHolder1_NewCourseButton")
	public
	WebElement new_course_button;
	@FindBy(id = "ctl00_ContentPlaceHolder1_txtSearch")
	WebElement filter_search_input;
	@FindBy(id = "ctl00_ContentPlaceHolder1_btnSearch")
	WebElement filter_search_button;
	@FindBy(id = "ctl00_ContentPlaceHolder1_TegrityCourseRepeater_ctl00_LinkButton3")
	WebElement first_course_membership_button;
	@FindBy(partialLinkText = "Admin Dashboard")
	WebElement admin_dashboard_link;
	@FindBy(id = "ctl00_ContentPlaceHolder1_TegrityCourseRepeater_ctl00_LinkButton2")
	WebElement first_course_delete_button;
	@FindBy(css = ".clsGridListEntryInfo1") WebElement first_course_name;

	public void clickOnNewCourse() {
		try {
			new_course_button.click();
			System.out.println("Clicked on new course.");
		} catch (Exception msg) {
			System.out.println("Not clicked on new course.");
		}
	}

	public void clickOnFilterButton() {
		try {
			wait.until(ExpectedConditions.visibilityOf(filter_search_button));
			filter_search_button.click();
			System.out.println("Clicked on filter button.");
			
		} catch (Exception msg) {
			System.out.println("Not clicked on filter button.");
		}
	}

	public void setFilterSearchBox(String set_to) throws InterruptedException {
		for(int i=0; i<30; i++) {
			try {
				wait.until(ExpectedConditions.visibilityOf(filter_search_input));
				filter_search_input.sendKeys(Keys.CONTROL + Keys.chord("a"));
				filter_search_input.sendKeys(Keys.DELETE);
				filter_search_input.sendKeys(set_to);
				if (filter_search_input.getAttribute("value").equals(set_to)) {
					break;
				}
				Thread.sleep(1000);
				System.out.println("Filter search box setted to: " + set_to);
			} catch (Exception msg) {
				Thread.sleep(1000);
				System.out.println("Filter search box fail set to: " + set_to);
			}
		}
	}

	public boolean searchAndFilterCourses(String course_name) throws InterruptedException {
		setFilterSearchBox(course_name);
		for(int i=0; i<5; i++) {
			clickOnFilterButton();
			try {
				wait.until(ExpectedConditions.textToBePresentInElement(first_course_name, course_name));
				return true;
			} catch(Exception msg) {
				continue;
			}
		}
		
		
		return false;
	}
	
	public boolean firstCourseIsDisplayed(){
		return first_course_name.isDisplayed();
		
	}

	public boolean clickOnFirstCourseMembershipButton() throws InterruptedException {
		for(int i=0; i<30; i++) {
			try {
				wait.until(ExpectedConditions.visibilityOf(first_course_membership_button));
				first_course_membership_button.click();
				System.out.println("Clicked on first course membership button");
				return true;
			} catch (Exception msg) {
				System.out.println("Fail to click on first course membership button. ");
				Thread.sleep(1000);
			}
		}
		return false;
	}

	public boolean clickOnAdminDashboard() {
		try {
			admin_dashboard_link.click();
			System.out.println("Clicked on admin dashboard link.");
			return true;
		} catch (Exception msg) {
			System.out.println("Fail to click on admin dashboard link.");
			return false;
		}
	}

	/// click on delete button and confirm alarm: "are you sure you want to
	/// delete"
	public boolean clickOnFirstCourseDeleteButton() {
		try {
			waitForVisibility(first_course_delete_button);
			first_course_delete_button.click();
			System.out.println("Clicked on first course delete button");
			Thread.sleep(3000);
		} catch (Exception msg) {
			System.out.println("Fail to click on first course delete button. ");
			Assert.assertTrue(false);
			return false;
		}
		return true;
	}

	/// enrolls instructor or instructor list to course
	public void enrollInstructorToCourse(String course,List<String>  users,
			ManageAdHocCoursesMembershipWindow mangage_adhoc_courses_membership_window) throws InterruptedException {
		driver.switchTo().frame(0);
		Thread.sleep(2000);
		// Search target course name
		searchAndFilterCourses(course);
		Thread.sleep(7000);
		// Click on result first course (the only one) membership button
		clickOnFirstCourseMembershipButton();
		Thread.sleep(2000);
	    for(String user:users)
	    {
	    mangage_adhoc_courses_membership_window.searchForUser(user);
		Thread.sleep(5000);
		// Select first user from user list (the only user it found because of
		// the uniq of the search)
		mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();
		Thread.sleep(5000);
		// Add selected user to instructor list
		mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
		Thread.sleep(5000);
	    }
		mangage_adhoc_courses_membership_window.ok_button.click();
		Thread.sleep(1000);
		driver.switchTo().alert().accept();
	}
	/// enrolls student or students list to course
		public void enrollStudentsToCourse(String course,List<String>  users,ManageAdHocCoursesMembershipWindow mangage_adhoc_courses_membership_window) throws InterruptedException {
			String u="";
			try{
			driver.switchTo().frame(0);
			Thread.sleep(2000);
			// Search target course name
			searchAndFilterCourses(course);
			Thread.sleep(7000);
			// Click on result first course (the only one) membership button
			clickOnFirstCourseMembershipButton();
			Thread.sleep(2000);
			
		    for(String user:users)
		    {
		    mangage_adhoc_courses_membership_window.searchForUser(user);
		    u=user;
			Thread.sleep(5000);
			// Select first user from user list (the only user it found because of
			// the uniq of the search)
			mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();
			Thread.sleep(5000);
			// Add selected user to instructor list
			mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToStudentList();
			Thread.sleep(5000);
		    }
			mangage_adhoc_courses_membership_window.ok_button.click();
			Thread.sleep(1000);
			driver.switchTo().alert().accept();
			ATUReports.add("Enrolled "+u+"to course "+course+" as student ","user enrolled to course","user enrolled to course", LogAs.PASSED, null);
			}catch(Exception e){
				ATUReports.add("Enrolled "+u+"to course "+course+" as student ","user enrolled to course","user not enrolled to course", LogAs.FAILED, null);
				
				
			}
		}
	/// un-enrolls instructor to course
	public void unEnrollInstructorToCourse(String course, String user,ManageAdHocCoursesMembershipWindow mangage_adhoc_courses_membership_window) throws InterruptedException {
		// Click on create course href link
	try{driver.switchTo().frame(0);
        Thread.sleep(2000);
        // Search target course name
		searchAndFilterCourses(course);
        Thread.sleep(7000);
		/// click on membership button
		clickOnFirstCourseMembershipButton();
		Thread.sleep(3000);
		/// 5.unroll instructor
		System.out.println("d1");
		mangage_adhoc_courses_membership_window.selectIrUserFromUserList(mangage_adhoc_courses_membership_window.instructor_elements_list, user);
		Thread.sleep(3000);
		System.out.println("d2");
		mangage_adhoc_courses_membership_window.clickOnRemoveSelectedUserToInstructorList();
		Thread.sleep(2000);
		System.out.println("d3");
		waitForVisibility(mangage_adhoc_courses_membership_window.ok_button);
		mangage_adhoc_courses_membership_window.ok_button.click();
		Thread.sleep(2000);
		System.out.println("d4");
		driver.switchTo().alert().accept();
		System.out.println("clicked on ok");
		System.out.println("d5");
		ATUReports.add("Enrolled "+user+"to course "+course+" as student ","user enrolled to course","user enrolled to course", LogAs.PASSED, null);
	}catch(Exception e){
		ATUReports.add("Enrolled "+user+"to course "+course+" as student ","user enrolled to course","user not enrolled to course", LogAs.FAILED, null);
		
		
	}
	}
	/// un-enrolls instructor to course
		public void unEnrollStusentsFromCourse(String course, String user,ManageAdHocCoursesMembershipWindow mangage_adhoc_courses_membership_window) throws InterruptedException {
			// Click on create course href link
			driver.switchTo().frame(0);
	        Thread.sleep(2000);
	        // Search target course name
			searchAndFilterCourses(course);
	        Thread.sleep(7000);
			/// click on membership button
			clickOnFirstCourseMembershipButton();
			Thread.sleep(3000);
			/// 5.unroll instructor
			mangage_adhoc_courses_membership_window.selectIrUserFromUserList(mangage_adhoc_courses_membership_window.student_elements_list, user);
			Thread.sleep(3000);
			mangage_adhoc_courses_membership_window.clickOnRemoveSelectedUserToStudentsList();
			Thread.sleep(2000);
			mangage_adhoc_courses_membership_window.ok_button.click();
			Thread.sleep(2000);
			driver.switchTo().alert().accept();
			System.out.println("clicked on ok");
		}
        //this function searches for course name and than deletes it
		public void deleteCourseByNameSearch(String past_course_student2) throws InterruptedException {
			driver.switchTo().frame(0);
			Thread.sleep(2000);
			Thread.sleep(2000);
			searchAndFilterCourses(past_course_student2);
			Thread.sleep(3000);
			clickOnFirstCourseDeleteButton();
		    Thread.sleep(3000);	
		    driver.switchTo().alert().accept();
		}
}
