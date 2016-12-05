package com.automation.main.page_helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.testng.Assert;

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
	public @FindBy(css = ".switch.ng-binding")
	WebElement monthAndYear;
	public @FindBy(css = ".glyphicon.glyphicon-arrow-left")
	WebElement arrowLeft;
	public @FindBy(css = ".glyphicon.glyphicon-arrow-right")
	WebElement arrowRight;
	public @FindBy(css =".week.ng-binding.firstDisplayWeek")
	WebElement firstWeek;
	public @FindBy(xpath= ".//*[@id='editRecordingWindow']/form/div[1]/div[2]/div/div[2]/div/table/tbody")
	WebElement calenderTable;
	public @FindBy(xpath = ".//*[@id='publishRecordingWindow']/form/div[1]/div[1]/div[2]/div/div[2]/div[2]/div/table/thead/tr[1]/th[2]/i")
	WebElement arrowLeftPublishRight;
	public @FindBy(xpath = ".//*[@id='publishRecordingWindow']/form/div[1]/div[1]/div[2]/div/div[2]/div[2]/div/table/thead/tr[1]/th[4]/i")
	WebElement arrowRightPublishRight;	
	String day,month,year;
	
	public void getMonthAndYearFromCalendar() throws ParseException {
		
		String monthAndYearString = monthAndYear.getText();
		String[] splited_structure_displayed_yearAndMonth = monthAndYearString.split("-");
		year = splited_structure_displayed_yearAndMonth[0];
		
		Date date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(splited_structure_displayed_yearAndMonth[1]);
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    int monthNumer = cal.get(Calendar.MONTH) + 1 ;
	    month = Integer.toString(monthNumer);
		
	}
	
	public void getMonthAndYearFromCalendarWithElement(WebElement ie) throws ParseException {
		
		String monthAndYearString = ie.getText();
		String[] splited_structure_displayed_yearAndMonth = monthAndYearString.split("-");
		year = splited_structure_displayed_yearAndMonth[0];
		
		Date date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(splited_structure_displayed_yearAndMonth[1]);
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    int monthNumer = cal.get(Calendar.MONTH) + 1 ;
	    month = Integer.toString(monthNumer);
		
	}
	
	
	//The current month is presented - year-month in the format of (xxxx)-(yyy)
    public void verifyThatFormatOfTheMonthAndYear(WebElement ie) throws ParseException {
    	String monthAndYearString = null;
		try {
			monthAndYearString = ie.getText();													
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM",Locale.ENGLISH);
			sdf.setLenient(false);
			//if not valid, it will throw ParseException
			Date date = sdf.parse(monthAndYearString);
			System.out.println(date);
			System.out.println("The date is in the following format: 'yyyy-MMM'");
			ATUReports.add("The date is in the following format: 'yyyy-MMM'" + "and the date is: " + monthAndYearString , "Success.", "Success.", LogAs.PASSED, null);
			Assert.assertTrue(true);
			
		} catch (ParseException e) {
			System.out.println("The date is not in the following format: 'yyyy-MMM'" );				
			ATUReports.add("The date is in the following format: 'yyyy-MMM' and the date is: " + monthAndYearString + e.getMessage(), "Success.", "Fail", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		} catch (Exception e) {
		System.out.println("No such Element exists" );				
		ATUReports.add("No such Element exists " + e.getMessage(), "Success.", "Fail", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		Assert.assertTrue(false);
	}
		
	}
	
	public void getDayFromCalender() {
		String id = "day ng-scope ng-binding active";
	    List<WebElement> dayPicker = (List<WebElement>) ((JavascriptExecutor) driver).executeScript("return document.getElementsByClassName(\""+id+"\");");
	    day = dayPicker.get(0).getText();	
	}


	public void verifyTheCurrentCreationDateIsDisplayedWithinTheEditBox() throws ParseException, InterruptedException{
	 
	String id = date_Field.getAttribute("id");
	String correctDate = (String)((JavascriptExecutor) driver).executeScript("return document.getElementById(\""+id+"\").value;");	
	clickElementJS(date_Field);
	Thread.sleep(500);
	
	String[] splited_structure_displayed = correctDate.split("/");	
	
	String dayToCompare = splited_structure_displayed[1];
	String monthToCompare = splited_structure_displayed[0];
	String yearToCompare = splited_structure_displayed[2];
	
	getMonthAndYearFromCalendar();
	getDayFromCalender();
	
	if(year.equals(yearToCompare)){
		ATUReports.add("Verify the year from the calendar.", LogAs.PASSED, null);
		System.out.println("Verify the year from the calendar.");
	} else {
		ATUReports.add("Not Verify the year from the calendar.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		System.out.println("Not Verify the year from the calendar.");
	}
	
    if(month.equals(monthToCompare)){
		ATUReports.add("Verify the month from the calendar.", LogAs.PASSED, null);
		System.out.println("Verify the month from the calendar.");
	} else {
		ATUReports.add("Not Verify the month from the calendar.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		System.out.println("Not Verify the month from the calendar.");
	}

    if(day.equals(dayToCompare)){
		ATUReports.add("Verify the day from the calendar.", LogAs.PASSED, null);
		System.out.println("Verify the day from the calendar.");
	} else {
		ATUReports.add("Not Verify the day from the calendar.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		System.out.println("Not Verify the day from the calendar.");
	}
	
	}
	
	public String changeCreateDayWithoutDayPickerActive(int days,WebElement element,By by ,WebElement rightArrow ,WebElement leftArrow) throws ParseException, InterruptedException {
		
		String returnDate = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd");
			Date date = new Date();
			Thread.sleep(500);
			day = dateFormat.format(date);
			getMonthAndYearFromCalendarWithElement(element);
			int dayPicker =getTheDayOnMonth(days,rightArrow,leftArrow);
			String dayPick = Integer.toString(dayPicker);
			changeDayOnCalender(dayPick,by);	
			returnDate = month + "/" + dayPick + "/" + year;
			
		} catch (Exception e) {
			e.getMessage();			
			ATUReports.add("The error message is: " + e.getMessage(), "Success.", "Fail", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		return returnDate;
	}
	/**
	 * Enter on the calendar label and get the month year and day after subtract days
	 * @param days days the amount of days that we should subtract or author
	 * @throws ParseException
	 * @throws InterruptedException
	 */

	public void changeCreateDay(int days) throws ParseException, InterruptedException {
		
		try {
			String id = date_Field.getAttribute("id");
			String correctDate = (String)((JavascriptExecutor) driver).executeScript("return document.getElementById(\""+id+"\").value;");	
			clickElementJS(date_Field);	
			Thread.sleep(500);
			getMonthAndYearFromCalendar();
			getDayFromCalender();	
			int pickTwoDayBefore = getTheDayOnMonth(days,arrowLeft,arrowRight);
			String dayNewNumber = Integer.toString(pickTwoDayBefore);
			By by = By.className("table-condensed");
			changeDayOnCalender(dayNewNumber,by);
		} catch (Exception e) {
			e.getMessage();;			
			ATUReports.add(e.getMessage(), "Success.", "Fail", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	
	/**
	 * This function is returning the current day after subtract days
	 *  ( days can be negative and we can author instead subtract)
	 * @param days the amount of days that we should subtract or author
	 * @return The current day - days
	 */
	public int getTheDayOnMonth(int days ,WebElement rightArrow ,WebElement leftArrow ){
		
			int dayInt = Integer.parseInt(day);
		    int monthInt = Integer.parseInt(month);
		    int pickTwoDayBefore = 0;
		    DateFormat dateFormat = new SimpleDateFormat("MM");
    		Date date = new Date();
    		String monthToCheck = dateFormat.format(date);
		    pickTwoDayBefore= dayInt -days;
		    
		    if(!monthToCheck.equals(month)){
			   int digitToMove = Integer.parseInt(monthToCheck) - Integer.parseInt(month);
			   if(digitToMove < 0 ){
				   for(int i = 0 ; i < digitToMove * (-1) ;i++){
					   clickElement(leftArrow);
					   monthInt -=1; 
			    	   month = Integer.toString(monthInt);
			   		}
			   } else {
				   for(int i = 0 ; i < digitToMove ;i++){
					   clickElement(rightArrow);
					   monthInt +=1; 
			    	   month = Integer.toString(monthInt);
			   		}
			   }  
		    }       
		    if(dayInt == 1 || dayInt == 2 || dayInt ==3){
		    	if(!monthToCheck.equals(month)){
		    		monthInt++;
		    	}
		    	if(monthInt == 3 ) {	
		    		if(pickTwoDayBefore <=0)
		    			pickTwoDayBefore +=28;    		
		    	} else if(monthInt == 1 || monthInt == 11 || monthInt == 9 || monthInt == 8  || monthInt == 6 || monthInt == 4 ||  monthInt == 2) {
		    		if(pickTwoDayBefore <=0)
		    			pickTwoDayBefore +=31;   			
		    	} else if(monthInt == 12 || monthInt == 10 || monthInt == 7 || monthInt == 5) {	
		    		if(pickTwoDayBefore <=0)
		    			pickTwoDayBefore +=30;
		    	}
		    	if(pickTwoDayBefore != 1)	{
		    		if(monthToCheck.equals(month)){
		    			clickElement(leftArrow);
		    		
		    		}
		    		monthInt -=1; 
		    	    month = Integer.toString(monthInt);
		    	}
		    } else if ( days < 0  && dayInt>= 28){
		    	if(!monthToCheck.equals(month)){
		    		monthInt--;
		    	}
		    	if(monthInt == 2 ) {	
		    		if(pickTwoDayBefore >28)
		    			pickTwoDayBefore -=28;    		
		    	} else if(monthInt == 1 || monthInt == 3 || monthInt == 5 || monthInt == 7  || monthInt == 8 || monthInt == 10 ||  monthInt == 12) {
		    		if(pickTwoDayBefore >31)
		    			pickTwoDayBefore -=31;   			
		    	} else if(monthInt == 4 || monthInt == 6 || monthInt == 9 || monthInt == 11) {	
		    		if(pickTwoDayBefore >30)
		    			pickTwoDayBefore -=30;
		    	}
		    	if(pickTwoDayBefore == 1 || pickTwoDayBefore == 2 )	{
		    		if(monthToCheck.equals(month)){
		    			clickElement(rightArrow);   			
		    		}
		    		monthInt +=1; 
		    	    month = Integer.toString(monthInt);
		    	}
		    }
		    return pickTwoDayBefore;
	}

	/**
	 * 
	 * @param dayNewNumber
	 * @param by
	 */
	public void changeDayOnCalender(String dayNewNumber ,By by){
		
		WebElement table = driver.findElement(by);
	    List<WebElement> rows = table.findElements(By.tagName("tr"));
	    List<WebElement> Numbers = new ArrayList<WebElement>() ;
	    int rowNumber = rows.size();
	    for(int i = 0 ; i< rowNumber ; i++) {
	    	List<WebElement> cols = table.findElements(By.tagName("td"));
	    	int colsNum = cols.size();
	    	for(int j = 0; j< colsNum ; j++) {
	    		String currentNumber = cols.get(j).getText();
	    		if(currentNumber.equals(dayNewNumber)){
	    			Numbers.add(cols.get(j));    			
	    		}
	    	}
	    }
	    
    	for(WebElement e :Numbers ){
    		String color = e.getCssValue("color").toString();
    		System.out.println(e.getText());
    		System.out.println(color);
    		String grey = "rgba(102, 102, 102, 1)";
    		if(color.equals(grey)){
    			//clickElement(e);
    			((JavascriptExecutor) driver).executeScript("arguments[0].click();",e);
    			ATUReports.add("Verify the day from the calendar.", LogAs.PASSED, null);
    			System.out.println("Verify the day from the calendar.");
    			Assert.assertTrue(true);	
    			return;
    		}
    	}
    	ATUReports.add("Not Verify the day from the calendar.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		System.out.println("Not Verify the day from the calendar.");
	    Assert.assertTrue(false);
	}
}