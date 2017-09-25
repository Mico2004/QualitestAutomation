package com.automation.main.page_helpers;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import utils.WaitDriverUtility;

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
	public	WebElement new_course_button;
	@FindBy(id = "ctl00_ContentPlaceHolder1_txtSearch")
	WebElement filter_search_input;
	@FindBy(id = "ctl00_ContentPlaceHolder1_btnSearch")
	WebElement filter_search_button;
	@FindBy(id = "ctl00_ContentPlaceHolder1_TegrityCourseRepeater_ctl00_LinkButton3")
	public WebElement first_course_membership_button;
	@FindBy(partialLinkText = "Admin Dashboard")
	public WebElement admin_dashboard_link;
	@FindBy(id = "ctl00_ContentPlaceHolder1_TegrityCourseRepeater_ctl00_LinkButton2")
	WebElement first_course_delete_button;
	public @FindBy(css = ".clsGridListEntryInfo1") WebElement first_course_name;
	@FindBy(xpath="//div[@id='contentDIV']/table/tbody") WebElement contentTable;

	public void clickOnNewCourse() {
		try {
			new_course_button.click();
			System.out.println("Clicked on new course.");
		} catch (Exception msg) {
			System.out.println("Not clicked on new course.");
		}
	}

	
	public void waitForThePageToLoad(){
		
		try{
		wait.until(ExpectedConditions.visibilityOf(admin_dashboard_link));
		driver.switchTo().frame(0);
		wait.until(ExpectedConditions.visibilityOf(new_course_button));
		wait.until(ExpectedConditions.visibilityOf(filter_search_button));
		wait.until(ExpectedConditions.visibilityOf(first_course_membership_button));
		
		} catch(org.openqa.selenium.TimeoutException e){
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			e.printStackTrace();
		}
	
	}
	
	public void clickOnFilterButton() {
		try {
			wait.until(ExpectedConditions.visibilityOf(filter_search_button));

			String text = contentTable.getText();

			filter_search_button.click();

			System.out.println("Clicked on filter button.");

			wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.xpath("//div[@id='contentDIV']/table/tbody"),text)));

		} catch (Exception msg) {
			System.out.println("Not clicked on filter button.");
		}
	}

	public void setFilterSearchBox(String set_to) throws InterruptedException {
		try{
				wait.until(ExpectedConditions.elementToBeClickable(filter_search_input));
				filter_search_input.clear();				
				filter_search_input.sendKeys(set_to);
				wait.until(ExpectedConditions.textToBePresentInElementValue(filter_search_input,set_to));
				Thread.sleep(1000);
				System.out.println("Filter search box setted to: " + set_to);
			} catch (Exception msg) {
				Thread.sleep(1000);
				msg.getMessage();
				System.out.println("Filter search box fail set to: " + set_to);
			}

	}

	public boolean searchAndFilterCourses(String course_name) throws InterruptedException {
		setFilterSearchBox(course_name);
		for(int i=0; i<5; i++) {
			clickOnFilterButton();
			try {
				wait.until(ExpectedConditions.textToBePresentInElement(first_course_name, course_name));
				ATUReports.add(time +" Search and filter for a course: "+course_name,LogAs.PASSED,null);
				return true;
			} catch(Exception msg) {
				continue;
			}
		}
		
		ATUReports.add(time +" Search and filter for a course failed: "+course_name,LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		return false;
	}
	
	public boolean firstCourseIsDisplayed(){
		return first_course_name.isDisplayed();

	}

	// course displayed by index
	public boolean CourseIsDisplayedByIndex(int i){
		try{
		return driver.findElement(By.id("ctl00_ContentPlaceHolder1_TegrityCourseRepeater_ctl0"+i+"_LinkButton2")).isDisplayed();
	}catch(NoSuchElementException e){
		return false;
	}

	}

	public boolean clickOnFirstCourseMembershipButton() throws InterruptedException {
		for(int i=0; i<30; i++) {
			try {
				wait.until(ExpectedConditions.visibilityOf(first_course_membership_button));
				clickElementJS(first_course_membership_button);
				ATUReports.add(time +" Clicked on first course membership button",LogAs.PASSED,null);
				System.out.println("Clicked on first course membership button");
				return true;
			} catch (Exception msg) {
				ATUReports.add(time +" Clicked on first course membership button failed",LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
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
			waitForAlert(60);
			clickOkInAlertIfPresent();
			Thread.sleep(3000);
		} catch (Exception msg) {
			System.out.println("Fail to click on first course delete button. ");
			Assert.assertTrue(false);
			return false;
		}
		return true;
	}


	/// click on course delete according to the course index (parameter) - mickaele
	public boolean clickOnCourseDeleteButtonByIndex(int i) {
		try {
			waitForVisibility(first_course_delete_button);

			String text=getCourseNameByIndex(i).getText();

			WebElement courseDeleteButton=getDeleteButtonByIndex(i);

			wait.until(ExpectedConditions.elementToBeClickable(courseDeleteButton));

			courseDeleteButton.click();

			System.out.println("Clicked on course delete button according to index");

			waitForAlert(60);

			clickOkInAlertIfPresent();

			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(120, TimeUnit.SECONDS)
					.pollingEvery(5, TimeUnit.MILLISECONDS);

			wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(contentTable,text)));

		//	wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(getCourseNameByIndex(i),text)));

			Thread.sleep(3000);

		} catch (Exception msg) {
			System.out.println("Fail to click on index course delete button. "+msg.getMessage());
			Assert.assertTrue(false);
			return false;
		}
		return true;
	}

	/// enrolls instructor or instructor list to course
	public void enrollInstructorToCourse(String course,List<String>  users,
			ManageAdHocCoursesMembershipWindow mangage_adhoc_courses_membership_window) throws InterruptedException {
		waitForFrameAndGoIn();
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
			waitForAlert(10);
			clickOkInAlertIfPresent();
			Thread.sleep(5000);
	    }
		mangage_adhoc_courses_membership_window.ok_button.click();
		Thread.sleep(2000);
		clickOkInAlertIfPresent();
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
			waitForAlert(10);
			clickOkInAlertIfPresent();
			ATUReports.add(time +" Enrolled "+u+"to course "+course+" as student ","user enrolled to course","user enrolled to course", LogAs.PASSED, null);
			}catch(Exception e){
				ATUReports.add(time +" Enrolled "+u+"to course "+course+" as student "+e,"user enrolled to course","user not enrolled to course", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				
				
			}
		}
	/// un-enrolls instructor to course
	public void unEnrollInstructorToCourse(String course, String user,ManageAdHocCoursesMembershipWindow mangage_adhoc_courses_membership_window) throws InterruptedException {
		// Click on create course href link
	try{
        Thread.sleep(2000);
        // Search target course name
		searchAndFilterCourses(course);
        Thread.sleep(2000);
		/// click on membership button
		clickOnFirstCourseMembershipButton();
		Thread.sleep(1000);
		/// 5.unroll instructor
		System.out.println("d1");
		boolean onList=mangage_adhoc_courses_membership_window.selectIrUserFromUserList(mangage_adhoc_courses_membership_window.instructor_elements_list, user);
		Thread.sleep(3000);
		System.out.println("d2");
		if(onList) {
			mangage_adhoc_courses_membership_window.clickOnRemoveSelectedUserToInstructorList();
			Thread.sleep(2000);
			System.out.println("d3");
			waitForVisibility(mangage_adhoc_courses_membership_window.ok_button);
			mangage_adhoc_courses_membership_window.ok_button.click();
			Thread.sleep(2000);
			System.out.println("d4");
			System.out.println("clicked on ok");
			System.out.println("d5");
		}else{
			waitForVisibility(mangage_adhoc_courses_membership_window.ok_button);
			mangage_adhoc_courses_membership_window.ok_button.click();
			waitForAlert(10);
			clickOkInAlertIfPresent();

		}
		waitForAlert(10);
		clickOkInAlertIfPresent();
		ATUReports.add(time +" unEnrolled "+user+" from course "+course,"user unenrolled from course","user unenrolled from course", LogAs.PASSED, null);
	}catch(Exception e){
		ATUReports.add(time +" unEnrolled "+user+" from course "+course,"user unenrolled from course","failed", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		
		
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
			waitForAlert(60);
			clickOkInAlertIfPresent();
			Thread.sleep(3000);
			System.out.println("clicked on ok");
		}
        //this function searches for course name and than deletes it
		public void deleteCourseByNameSearch(String past_course_student2) throws InterruptedException {
			try{
			driver.switchTo().frame(0);			
			Thread.sleep(4000);			
			searchAndFilterCourses(past_course_student2);
			Thread.sleep(3000);
			clickOnFirstCourseDeleteButton();
			waitForAlert(60);
			clickOkInAlertIfPresent();
			Thread.sleep(3000);
			ATUReports.add(time +" Deleted course '"+past_course_student2+"' successfully",LogAs.PASSED,null);
			}catch(Exception e){
			ATUReports.add(time +" Course deletion '"+past_course_student2+"' failed",LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));	
			Assert.assertTrue(false);
			}
		}
		public void waitForPageToLoad(){
			try{
				getIntoFrame(0);
				waitForVisibility(new_course_button, 40);
				waitForVisibility(contentTable, 40);
				
			}catch(Exception e){
				ATUReports.add(time +" Loading 'Manage Ad-Hock Courses' page failed",  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		}

    // [M.E] Check if a specific course equels to one of courses in the list
	// courseList - the course list
	// index - the specific course according to it's location on the list (manage ad hock course page)
		public boolean CourseByIndexNameEqualsToOneOfaList(List<String> courseList,int index){

			try{

				String text=getCourseNameByIndex(index).getText();

				boolean isOnTheList=false;

				for(String str: courseList) {
					if(str.trim().contains(text) || text.toLowerCase().contains("bank") )
						return true;
				}
				return false;


			}catch(Exception e){

				System.out.println(e.getLocalizedMessage());

			}

			return false;

		}

		// [M.E] gets the delete button of a specific course according to index
		public WebElement getDeleteButtonByIndex(int i){
			return driver.findElement(By.id("ctl00_ContentPlaceHolder1_TegrityCourseRepeater_ctl0"+i+"_LinkButton2"));

		}

		// [M.E] gets the webelement of a course according to it's position in the list
		public WebElement getCourseNameByIndex(int index){
			try{

				WebElement delete=getDeleteButtonByIndex(index);

				WebElement parent1=delete.findElement(By.xpath("./.."));

				WebElement parent2=parent1.findElement(By.xpath("./.."));

				WebElement courseName=parent2.findElement(By.xpath("./td[2]"));


				return courseName;

			}catch(Exception e){

				System.out.println(e.getMessage());

				return null;
			}


		}



		
}
