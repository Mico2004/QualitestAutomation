package com.automation.main.page_helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

public class CalendarPage extends Page {

	public CalendarPage(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}


	public @FindBy(id="dateField")
	WebElement date_Field;
	public @FindBy(xpath =".//*[@id='editRecordingWindow']/form/div[1]/div[2]/div/div[2]/div/table")
	WebElement calenderTable;
	public @FindBy(xpath ="/*[@id='editRecordingWindow']/form/div[1]/div[2]/div/div[2]/div/table/thead/tr[1]/th[3]")
	WebElement monthAndYear;
	

	public void verifyTest(){
		 
		String correctDate = date_Field.getText();
		clickElement(date_Field);
		List<WebElement> tr_collection=calenderTable.findElements(By.xpath("tbody/tr"));
   
	        int row_num,col_num;
	        row_num=1;
	        for(WebElement trElement : tr_collection)
	        {
	            List<WebElement> td_collection=trElement.findElements(By.xpath("td"));
	            col_num=1;
	            for(WebElement tdElement : td_collection)
	            {
	                System.out.println("row # "+row_num+", col # "+col_num+ "text="+tdElement.getText());
	                col_num++;
	            }
	            row_num++;
	        } 
	    }
	

	public void verifyTheCurrentCreationDateIsDisplayedWithinTheEditBox() throws ParseException{
	 
	String correctDate = date_Field.getText();
	String monthAndYearString = monthAndYear.getText();
	String[] splited_structure_displayed = correctDate.split("/");	
	String[] splited_structure_displayed_yearAndMonth = monthAndYearString.split("-");	
	clickElement(date_Field);
	
	String day = splited_structure_displayed[0];
	String month = splited_structure_displayed[1];
	String year = splited_structure_displayed[2];
	
	if(year.equals(splited_structure_displayed_yearAndMonth[0])){
		ATUReports.add("Verify the year from the calendar.", LogAs.PASSED, null);
		System.out.println("Verify the year from the calendar.");
	} else {
		ATUReports.add("Not Verify the year from the calendar.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		System.out.println("Not Verify the year from the calendar.");
	}
	
	Date date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(splited_structure_displayed_yearAndMonth[1]);
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int monthNumer = cal.get(Calendar.MONTH);
	
    if(month.equals(Integer.toString(monthNumer))){
		ATUReports.add("Verify the month from the calendar.", LogAs.PASSED, null);
		System.out.println("Verify the month from the calendar.");
	} else {
		ATUReports.add("Not Verify the month from the calendar.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		System.out.println("Not Verify the month from the calendar.");
	}
	
    
    String id = "day ng-scope ng-binding active";
    List<WebElement> dayPicker = (List<WebElement>) ((JavascriptExecutor) driver).executeScript("document.getElementsByClassName(\""+id+"\");");
    String dayP = dayPicker.get(0).getText();
	
    if(day.equals(dayP)){
		ATUReports.add("Verify the day from the calendar.", LogAs.PASSED, null);
		System.out.println("Verify the year from the calendar.");
	} else {
		ATUReports.add("Not Verify the day from the calendar.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		System.out.println("Not Verify the year from the calendar.");
	}
	
	}		
}
