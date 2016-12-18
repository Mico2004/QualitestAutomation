package com.automation.main.page_helpers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections.set.SynchronizedSortedSet;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.UnhandledAlertException;
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
public class ManageAdHocCoursesMembershipWindow extends Page {
	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public ManageAdHocCoursesMembershipWindow(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(id="ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_txtSerach") WebElement search_user_input;
	@FindBy(id="ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_btnSearch") WebElement search_user_button;
	@FindBy(css="#ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ListBoxAllUsers>option") WebElement first_user_of_user_list;
	@FindBy(css="#ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ListBoxInstructors>option") WebElement	first_user_of_instructors_list;
	@FindBy(id="ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ButtonAddInstructor")
	public WebElement add_selected_as_instructor_button;
	@FindBy(id="ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ButtonDeleteInstructor") WebElement remove_selected_from_instructor_list_button;
	@FindBy(id="ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ButtonAddStudent") WebElement add_selected_as_student_button;
	@FindBy(id="ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ButtonDeleteStudent") WebElement remove_selected_from_student_list_button;
	@FindBy(id="ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_btnOK")
	public WebElement ok_button;
	@FindBy(id="ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_btnCancel") WebElement cancel_button;
	public @FindBy(xpath="//*[@id=\"ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ListBoxInstructors\"]/option") List<WebElement> instructor_elements_list;
	public @FindBy(xpath="//*[@id=\"ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ListBoxStudents\"]/option") List<WebElement> student_elements_list;

	
	public boolean clickOnSearchUserButton() {
		try {
			wait.until(ExpectedConditions.visibilityOf(search_user_button));
			search_user_button.click();
			System.out.println("Clicked on search user button.");
			return true;
		} catch (Exception msg) {
			System.out.println("Fail to click on search user button.");
			return false;
		}
	}
	
	public boolean setSearchUserInput(String set_to) {
		try {
			wait.until(ExpectedConditions.visibilityOf(search_user_input));
			Thread.sleep(1000);
			search_user_input.clear();
			search_user_input.sendKeys(set_to);
			System.out.println("Setting user input search to: " + set_to);
			return true;
		} catch (Exception msg) {
			System.out.println("Fail to set user input search to: " + set_to);
			return false;
		}
	}
	
	public void searchForUser(String user_name) throws InterruptedException {
		setSearchUserInput(user_name);
		clickOnSearchUserButton();
		wait.until(ExpectedConditions.textToBePresentInElement(first_user_of_user_list, user_name));		
	}
	
	public boolean selectFirstUserFromUserList() {
		try {
			wait.until(ExpectedConditions.visibilityOf(first_user_of_user_list));
			first_user_of_user_list.click();
			System.out.println("First user selected from user list.");
			return true;
		} catch (Exception msg) {
			System.out.println("Fail to select first user from user list.");
			return false;
		}
	}
	
	
	
	public boolean clickOnOkButton() throws InterruptedException {
		
		Thread.sleep(2000);	
		try {
			waitForVisibility(ok_button);
			ok_button.click();		
			waitForAlert(60);
			clickOkInAlertIfPresent();			
			System.out.println("Accecpt alert message.");
			ATUReports.add("Click enrollment diaglog OK button", "OK button was clicked","OK button was clicked",LogAs.PASSED,null);
			return true;
		}catch (Exception msg) {
			System.out.println("Didn't Accecpt alert message."+msg.getMessage());
			ATUReports.add("Click enrollment diaglog OK button", "OK button was clicked","Clicked Failed:"+msg.getLocalizedMessage(),LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			return false;
			}
		}
		// Elvira api tryout
	/*	for (int i=0; i<10; i++) {
			
			try {
				driver.switchTo().alert().accept();				
				System.out.println("Accecpt alert message.");
				return true;
			} catch (Exception msg) {
				try {
					ok_button.click();
					System.out.println("Clicked on ok button.");
					
				} catch (Exception ms1g) {
					//System.out.println("Fail to click on ok button.");
					
				}
			}
			wait.until(ExpectedConditions.alertIsPresent());
		}
		
		return false;*/
	
	
	public boolean clickOnCancelButton() {
		try {
			cancel_button.click();
			System.out.println("Clicked on cancel button.");
			return true;
		} catch (Exception msg) {
			System.out.println("Fail to cancel on ok button.");
			return false;
		}
	}
	
	public boolean clickOnAddSelectedUserToInstructorList() {
		try {
			wait.until(ExpectedConditions.visibilityOf(add_selected_as_instructor_button));
			clickElementJS(add_selected_as_instructor_button);
			System.out.println("Added selected to instructor list.");
			return true;
		} catch (Exception msg) {
			System.out.println("Fail to add selected to instructor list.");
			return false;
		}
	}
	
	public boolean clickOnRemoveSelectedUserToInstructorList() {
		try {
			wait.until(ExpectedConditions.visibilityOf(remove_selected_from_instructor_list_button));
			clickElementJS(remove_selected_from_instructor_list_button);
			System.out.println("Removed selected to instructor list.");
			ATUReports.add("Removed selected to instructor list.", LogAs.PASSED, null);
			return true;
		} catch (Exception msg) {
			System.out.println("Fail to remove selected to instructor list.");
			ATUReports.add("Fail to remove selected to instructor list.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			return false;
		}
	}
	
	public boolean clickOnAddSelectedUserToStudentList() {
		try {
			wait.until(ExpectedConditions.visibilityOf(add_selected_as_student_button));
			clickElementJS(add_selected_as_student_button);
			System.out.println("Added selected to student list.");
			return true;
		} catch (Exception msg) {
			System.out.println("Fail to add selected to student list.");
			return false;
		}
	}
	
	///select user by name using its name from eleemnt list
	public boolean selectIrUserFromUserList(List<WebElement> list,String name) {
		try {
			waitForVisibility(first_user_of_user_list);
			for(WebElement el:list) { 
				String val=el.getText();			
				if(val.contains(name)) {
					Thread.sleep(2000);
					clickElementJS(el);
					System.out.println("User selected from user list.");
					return true;
				}
			}
			
			System.out.println("The user isn't on the list.");
			return false;
		}catch(UnhandledAlertException ex){
			System.out.println("Alert pop up");
			clickOkInAlertIfPresent();
			return false;
		}
			
		catch (Exception msg) {
			System.out.println("Fail to select  user from user list.");
			return false;
		}
	}
	
	
	
	//////this function verifies if instructor is enrolled to course or not expected variable give us the condition it should be(enrolled or un enrolled)
	public void isInstructorEnrolledToCourse(boolean expected,String instructor_name)
	{
		boolean enrolled_instructor=selectIrUserFromUserList(instructor_elements_list, instructor_name);
		///instructor should have been enrolled to course
		if(expected==true)
		{
		    if(enrolled_instructor==true)
	    	{
	    		System.out.println("Verified that  selected user is  displaied in instructor list.");
	    		ATUReports.add("Verified that  selected user is  displaied in instructor list.", LogAs.PASSED, null);
	    		Assert.assertTrue(true);
	    	} else {
	    		System.out.println(" selected user is not displaied in instructor list.");
	    		ATUReports.add(" selected user is not displaied in instructor list.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	    		Assert.assertTrue(false);
	    	}
		}
		else
		if(expected==false)	///instructor should have not been enrolled to course
		{
			  if(enrolled_instructor==false)
				{
					System.out.println("Verified that  selected user is not displaied in instructor list.");
					ATUReports.add("Verified that  selected user is not displaied in instructor list.", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println(" selected user is  displaied in instructor list.");
					ATUReports.add(" selected user is  displaied in instructor list.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				}
		}
		
	}
	//////this function verifies if instructor is enrolled to course or not expected variable give us the condition it should be(enrolled or un enrolled)
	public void isStudentEnrolledToCourse(boolean expected,String instructor_name)
	{
		boolean enrolled_student=selectIrUserFromUserList(student_elements_list, instructor_name);
		///instructor should have been enrolled to course
		if(expected==true)
		{
		    if(enrolled_student==true)
	    	{
	    		System.out.println("Verified that  selected user is  displaied in student list.");
	    		ATUReports.add("Verified that  selected user is  displaied in student list.", LogAs.PASSED, null);
	    		Assert.assertTrue(true);
	    	} else {
	    		System.out.println(" selected user is not displaied in student list.");
	    		ATUReports.add(" selected user is not displaied in student list.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	    		Assert.assertTrue(false);
	    	}
		}
		else
		if(expected==false)	///instructor should have not been enrolled to course
		{
			  if(enrolled_student==false)
				{
					System.out.println("Verified that  selected user is not displaied in student list.");
					ATUReports.add("Verified that  selected user is not displaied in student list.", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println(" selected user is  displaied in student list");
					ATUReports.add(" selected user is  displaied in student list", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				}
		}
		
	}
	///"Removed selected to students list."
	public boolean clickOnRemoveSelectedUserToStudentsList() {
		try {                                              
			wait.until(ExpectedConditions.visibilityOf(remove_selected_from_student_list_button));
			clickElementJS(remove_selected_from_student_list_button);
			System.out.println("Removed selected to students list.");
			ATUReports.add("Removed selected to students list.", LogAs.PASSED, null);
        	Assert.assertTrue(true);
			return true;
		} catch (Exception msg) {
			System.out.println("Fail to remove selected to students list.");
			Assert.assertTrue(false);
			ATUReports.add("Fail to remove selected to students list.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			return false;
		}
	}
	
	// This function wait x second until instructor enroll to a course
	public void waitMaxTimeUntillInstructorEnrollToCourse(String instructor_name) throws InterruptedException {
		try{
		for (int i=0; i<60;i++) {
			if(selectIrUserFromUserList(instructor_elements_list, instructor_name)) {
				break;
			} else {
				Thread.sleep(1000);
			}
		}	
		}catch(UnhandledAlertException ex){
			System.out.println("Alert pop up");
			clickOkInAlertIfPresent();
			
		}			
			
			
			
			}
		
	
	
	// This function wait x second until student enroll to a course
	public void waitMaxTimeUntillStudentEnrollToCourse(String student_name) throws InterruptedException {
		try{
		for (int i=0; i<60;i++) {
			if(selectIrUserFromUserList(student_elements_list, student_name)) {
				ATUReports.add("User was added to course users list",student_name,LogAs.PASSED,null);
				return;
			} else {
				Thread.sleep(1000);
			}
		}
		ATUReports.add("User wasn't added to course users list",student_name,LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}catch(Exception e){
			ATUReports.add("User wasn't added to course users list",student_name,"Error:"+e.getMessage(),LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			
			
		}
	}
}
