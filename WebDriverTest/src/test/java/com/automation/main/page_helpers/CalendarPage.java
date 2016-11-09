package com.automation.main.page_helpers;

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
	public @FindBy(css =".week.ng-binding.firstDisplayWeek")
	WebElement firstWeek;
	public @FindBy(xpath= ".//*[@id='editRecordingWindow']/form/div[1]/div[2]/div/div[2]/div/table/tbody")
	WebElement calenderTable;
	String day,month,year;
	

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
	
	
	public void getDayFromCalender() {
		String id = "day ng-scope ng-binding active";
	    List<WebElement> dayPicker = (List<WebElement>) ((JavascriptExecutor) driver).executeScript("return document.getElementsByClassName(\""+id+"\");");
	    day = dayPicker.get(0).getText();	
	}


	public void verifyTheCurrentCreationDateIsDisplayedWithinTheEditBox() throws ParseException, InterruptedException{
	 
	String id = date_Field.getAttribute("id");
	String correctDate = (String)((JavascriptExecutor) driver).executeScript("return document.getElementById(\""+id+"\").value;");	
	clickElement(date_Field);
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
	
	public void changeCreateDay(int days) throws ParseException, InterruptedException {
		
		String id = date_Field.getAttribute("id");
		String correctDate = (String)((JavascriptExecutor) driver).executeScript("return document.getElementById(\""+id+"\").value;");	
		clickElementJS(date_Field);	
		Thread.sleep(500);
		getMonthAndYearFromCalendar();
		getDayFromCalender();
			
	    int dayInt = Integer.parseInt(day);
	    int monthInt = Integer.parseInt(month);
	    int pickTwoDayBefore = 0;
	    pickTwoDayBefore= dayInt -days;
	    if(dayInt == 1 || dayInt == 2 || dayInt ==3){
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
	    		clickElement(arrowLeft);
	    	    monthInt -=1; 
	    	}
	    }
	    String dayNewNumber = Integer.toString(pickTwoDayBefore);
	    WebElement table = driver.findElement(By.className("table-condensed"));
	    List<WebElement> rows = table.findElements(By.tagName("tr"));
	    List<WebElement> Numbers = new ArrayList<WebElement>() ;
	    int rowNumber = rows.size();
	    for(int i = 0 ; i< rowNumber ; i++) {
	    	List<WebElement> cols = table.findElements(By.tagName("td"));
	    	int colsNum = cols.size();
	    	for(int j = 0; j< colsNum ; j++) {
	    		if(cols.get(j).getText().equals(dayNewNumber)){
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