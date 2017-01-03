package com.automation.main.page_helpers;

import java.awt.Frame;
import java.awt.Robot;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import org.apache.commons.collections.FastArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.annotations.Listeners;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class CustomAnalysis extends Page {
	public CustomAnalysis(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(id = "typeReport") WebElement report_type_select;
	@FindBy(css = "#typeReport>option") List<WebElement> report_type_options;
	@FindBy(css = ".btn.btn-primary:nth-of-type(1)") WebElement run_report_button;
	@FindBy(css = ".btn.btn-primary:nth-of-type(2)") WebElement download_report_button;
	@FindBy(css = "#dropdown1>.form-control.ng-pristine.ng-valid.ng-valid-valid-date") WebElement from_date;
	@FindBy(css = "#dropdown2>.form-control.ng-pristine.ng-valid.ng-valid-valid-date") WebElement to_date;
	List<WebElement> col_subtitles;
	List<WebElement> row_subtitles;
	List<WebElement> header_titles;
	int number_of_header_elements;
	int number_of_cols;
	int number_of_rows;
	int number_of_subtitles_per_title;
	String[][] values_matrix;
	
	@FindBy(css = ".alt") List<WebElement> grouping_textbox_labels;
	@FindBy(css = ".inputWrap.ng-scope>.ng-pristine.ng-valid") List<WebElement> grouping_textbox_inputs;
	@FindBy(css = ".ng-pristine.ng-valid>option") List<WebElement> grouping_dropbox_options;
	WebElement[] grouping_1_dropbox_options;
	WebElement[] grouping_2_dropbox_options;
	WebElement[] grouping_3_dropbox_options;
	WebElement[] grouping_4_dropbox_options;
	WebElement[] time_aggregation_options;
	
	@FindBy(css = ".large.ng-scope.ng-pristine.ng-valid>option") List<WebElement> additional_instructor_grouping_dropbox_options;
	List<WebElement> instructor_additional_course_dropbox_options;
	List<WebElement> instructor_additional_instructor_dropbox_options;
	List<WebElement> instructor_additional_recording_dropbox_options;
	List<WebElement> instructor_additional_downloader_dropbox_options;
	
	// Temp
	List<WebElement> temp_webElements_for_first_column_select;
	

	// This function get integer 0-4 and choose the report type according it
	// 1 - Viewing report by course, recording, instructor, viewer
	// 2 - View report by chapters
	// 3 - Recording report
	// 4 - Downloading report
	// 5 - Quota usage report
	public void chooseIndexReportType (int target_type) {
		waitForVisibility(report_type_select);
		if((target_type>4) || (target_type<1)) {
			System.out.println("Error: Invalid input for choosing report type!.");
		} else {
			try {
				report_type_select.click();
				
				for(int i=1; i<target_type; i++) {
					report_type_select.sendKeys(Keys.DOWN);
				}
				Thread.sleep(100);
				report_type_select.sendKeys(Keys.ENTER);
				
				if(report_type_options.get(target_type-1).getAttribute("selected").equals("true")) {
					System.out.println("Report type selected successfuly.");
					ATUReports.add(time +" Report type selected successfuly.", "True.", "True.", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("Report type not selected.");
					ATUReports.add(time +" Report type selected successfuly.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				}
			} catch(Exception msg) {
				System.out.println("Report type not selected.");
				ATUReports.add(time +" Report type selected successfuly.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		}
	}
	
	// This function click on run report
	public void clickOnRunReportButton() throws InterruptedException {
		clickElementWithDescription(run_report_button, "run report button");
		
			Thread.sleep(1000);
			try {
			getIntoFrame(0);
			waitForVisibility(driver.findElement(By.id("reportForm")));
			
			exitInnerFrame();
			System.out.println("Report form appear.");
			ATUReports.add(time +" Report form appear.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception msg) {
			exitInnerFrame();
			System.out.println("Report form not appearing.");
			ATUReports.add(time +" Report form appear.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}

	}
	
	// This function verify that start date of data selection in format "M/D/YYYY" (for example: "12/15/2013").
	public void verifyStartDateOfDataSelectionInCorrectFormat() {
		System.out.println("Checking that start date of data selection in format mm/dd/yyyy.");
		ATUReports.add(time +" Checking that start date of data selection in format mm/dd/yyyy.", LogAs.PASSED, null);
		verifyThatTargetStringIsValidDate(from_date.getAttribute("value"));
	}
	
	// This function verify that end date of data selection in format "M/D/YYYY" (for example: "1/7/2014").
	public void verifyEndDateOfDataSelectionInCorrectFormat() {
		System.out.println("Checking that end date of data selection in format mm/dd/yyyy.");
		ATUReports.add(time +" Checking that end date of data selection in format mm/dd/yyyy.", LogAs.PASSED, null);
		verifyThatTargetStringIsValidDate(to_date.getAttribute("value"));
	}
	
	// This function init number of cols and the list of row titles sub-menu
	public void initRowSubMenuTitlesAndNumberOfCols() {
		int loop_id = 2;
		while(true) {
			try {
				col_subtitles.add(driver.findElement(By.xpath(".//table/tbody/tr/td/table/tbody/tr[3]/td[" + Integer.toString(loop_id) + "]")));
				loop_id++;
			} catch(Exception msg) {
				break;
			}
		}
		
		number_of_cols = col_subtitles.size();
	}
	
	// This function init number of row and the list of col titles sub-menu (and probably the matrix)
	public void initColSubMenuTitlesAndNumberOfRows() {
		int loop_id = 4;
		while(true) {
			try {
				row_subtitles.add(driver.findElement(By.xpath(".//table/tbody/tr/td/table/tbody/tr[" + Integer.toString(loop_id) + "]/td[2]")));
				loop_id++;
			} catch(Exception msg) {
				loop_id--;
				break;
			}
		}
		
		number_of_rows = row_subtitles.size();
	}
	
	// This function init stat information into matrix
	public void initStatInformationIntoMatrix() {
		values_matrix = new String[number_of_rows][number_of_cols];
		
		int row = 4;
		for(int i=0; i<number_of_rows; i++) {
			int col = 3;
			
			for(int j=0; j<number_of_cols; j++) {
//				System.out.println("row:" + row);
//				System.out.println("col:" + col);
//				System.out.println(driver.findElement(By.xpath(".//table/tbody/tr/td/table/tbody/tr[" + Integer.toString(row) + "]/td[" + Integer.toString(col) +"]/div")).getText());
				values_matrix[i][j] = driver.findElement(By.xpath(".//table/tbody/tr/td/table/tbody/tr[" + Integer.toString(row) + "]/td[" + Integer.toString(col) +"]/div")).getText();
				
				col++;
			}
			row++;
		}
	}
	
	// This function return the value of values of matrix in i,j
	public String getValueIJOfValuesMatrix(int i, int j) {
		return values_matrix[i][j];
	}
	
	// This function return the values matrix
	public String[][] getValuesMatrix() {
		return values_matrix;
	}
	
	// This function init everything
	public void initCustomAnalysis() {
		getIntoFrame(0);
		col_subtitles = new ArrayList<WebElement>();
		row_subtitles = new ArrayList<WebElement>();
		header_titles = new ArrayList<WebElement>();
		initRowSubMenuTitlesAndNumberOfCols();
		initColSubMenuTitlesAndNumberOfRows();
		initHeaderMenuTitlesAndNumberOfElements();
		initStatInformationIntoMatrix();
		exitInnerFrame();
	}
	
	// This function init number of cols and the list of row titles sub-menu
	public void initHeaderMenuTitlesAndNumberOfElements() {
		int loop_id = 3;
		while(true) {
			try {
				header_titles.add(driver.findElement(By.xpath(".//table/tbody/tr/td/table/tbody/tr[2]/td[" + Integer.toString(loop_id) + "]")));
				loop_id++;
			} catch(Exception msg) {
				break;
			}
		}
		
		number_of_header_elements = header_titles.size();
		
		number_of_subtitles_per_title = number_of_cols/number_of_header_elements;
	}
	
	// This function verify that the second column from the left contains the "Total" aggregation.
	public void verifyThatTheSecondColumnFromTheLeftContainsTheTotalAggregation() {
		getIntoFrame(0);
		verifyWebElementTargetText(header_titles.get(0), "Total");
		exitInnerFrame();
	}
	
	// This function verify that the 3rd columns etc contain data according to time aggregation - for Year format
	public void verifyThatThirdColumnsEtcContainDataAccordingToTimeAggregationInYears() {
		int from_date_year = Integer.parseInt(from_date.getAttribute("value").split("/")[2]);
		int to_date_year = Integer.parseInt(to_date.getAttribute("value").split("/")[2]);
		getIntoFrame(0);
		
		int j=1;
		for(int i=to_date_year; i>=from_date_year; i--) {
//			System.out.println("Year " + i);
			verifyWebElementTargetText(header_titles.get(j), "Year " + i);
			j++;
		}
		exitInnerFrame();
	}
	
	// This function verify that for report type report downloading each Report "Time Period" for  column contains next subcolumns:
	// "Podcast Downloads".
	// "Vodcast Downloads".
	// "Recording Downloads".
	public void verifyForReportDownloadingReportTypeEachTimePerfiodContainsSubtitles() {
		getIntoFrame(0);
		for(int i=0; i<number_of_cols; i++) {
			if(i%3==0) {
				verifyWebElementTargetText(col_subtitles.get(i), "Podcast Downloads");
			} else if (i%3==1) {
				verifyWebElementTargetText(col_subtitles.get(i), "Vodcast Download");
			} else if (i%3==2) {
				verifyWebElementTargetText(col_subtitles.get(i), "Recording Downloads");
			}
		}
		exitInnerFrame();
	}
	
	// This function verify that the last row contains the "Grand Total" data.
	public void verifyThatTheLastRowContainsTheGrandTotalData() {
		getIntoFrame(0);
		verifyWebElementTargetText(row_subtitles.get(number_of_rows-1), "Grand Total");
		
		if(row_subtitles.size()==1) {
			exitInnerFrame();
			return;
		}
	
		boolean second_padding = false;
		boolean third_padding = false;
		int[] value_array_for_courses = new int[number_of_cols];
		int[] value_array_for_users = new int[number_of_cols];
		int[] value_array_for_recordings = new int[number_of_cols];
		for(int i=0; i<number_of_cols; i++) {
			value_array_for_courses[i] = 0;
			value_array_for_recordings[i] = 0;
			value_array_for_users[i] = 0;
		}
		
		for(int i=0; i<number_of_rows-1; i++) {
			String padding_left = row_subtitles.get(i).getCssValue("padding-left");
//			System.out.println(padding_left);
			
			if(padding_left.equals("2.66667px")) {
				for(int j=0; j<number_of_cols; j++) {
					value_array_for_courses[j] += Integer.parseInt(values_matrix[i][j]);
				}
			} else if (padding_left.equals("13.3333px")) {
				for(int j=0; j<number_of_cols; j++) {
					value_array_for_users[j] += Integer.parseInt(values_matrix[i][j]);
				}
				second_padding = true;
			} else if (padding_left.equals("26.6667px")) {
				for(int j=0; j<number_of_cols; j++) {
					value_array_for_recordings[j] += Integer.parseInt(values_matrix[i][j]);
				}
				third_padding = true;
			}
		}
		
		for(int i=0; i<number_of_cols; i++) {
			if(second_padding && third_padding) {
				if((value_array_for_courses[i] == value_array_for_users[i]) && 
						(value_array_for_users[i] == value_array_for_recordings[i]) &&
						(value_array_for_recordings[i] == Integer.parseInt(values_matrix[number_of_rows-1][i]))) {
					continue;
				}
			} else if(second_padding) {
				if((value_array_for_courses[i] == value_array_for_users[i]) && 
						(value_array_for_users[i] == Integer.parseInt(values_matrix[number_of_rows-1][i]))) {
					continue;
				}
			} else {
				if((value_array_for_courses[i] == Integer.parseInt(values_matrix[number_of_rows-1][i]))) {
					continue;
				} else {
					System.out.println("Not verfied last row contains the Grand Total data which is correct.");
					ATUReports.add(time +" Verfied last row contains the Grand Total data which is correct.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				}
			}
			
		}
		
		exitInnerFrame();
		System.out.println("Verfied last row contains the Grand Total data which is correct.");
		ATUReports.add(time +" Verfied last row contains the Grand Total data which is correct.", "True.", "True.", LogAs.PASSED, null);
		Assert.assertTrue(true);
		
		
	}
	
	// This function init grouping 1-4 dropbox options array
	public void initGroupingOneToFourOptionsArrayAndTimeAggregationOptions() {
		List<WebElement>grouping_dropbox_options = driver.findElements(By.cssSelector(".ng-pristine.ng-valid>option"));
		
		grouping_1_dropbox_options = new WebElement[5];
		grouping_2_dropbox_options = new WebElement[5];
		grouping_3_dropbox_options = new WebElement[5];
		grouping_4_dropbox_options = new WebElement[5];
		time_aggregation_options = new WebElement[3];
		
		int k = 0;
		for(int i=0; i<4; i++) {
			for(int j=0; j<5; j++) {
				switch (i) {
				case 0:
					grouping_1_dropbox_options[j] = grouping_dropbox_options.get(k);
					break;
				case 1:
					grouping_2_dropbox_options[j] = grouping_dropbox_options.get(k);
					break;
				case 2:
					grouping_3_dropbox_options[j] = grouping_dropbox_options.get(k);
					break;
				case 3:
					grouping_4_dropbox_options[j] = grouping_dropbox_options.get(k);
					break;
				}
				k++;
			}
		}
		
		k=0;
		for(int i=grouping_dropbox_options.size()-1; i>grouping_dropbox_options.size()-4; i--){
			time_aggregation_options[k] = grouping_dropbox_options.get(i);
			k++;
		}
		
	}
	
	// This function get grouping number, and String. It will verify that this string is the selected option of this grouping dropbox.
	public void verifyChosenGroupingDropboxSelectedTargetOption(int grouping, String target_option) {
		WebElement[] grouping_dropbox_to_check;
		switch (grouping) {
		case 1:
			grouping_dropbox_to_check = grouping_1_dropbox_options;
			break;
		case 2:
			grouping_dropbox_to_check = grouping_2_dropbox_options;
			break;
		case 3:
			grouping_dropbox_to_check = grouping_3_dropbox_options;
			break;
		case 4:
		default:
			grouping_dropbox_to_check = grouping_4_dropbox_options;
			break;
		}
		
		for(WebElement webElement: grouping_dropbox_to_check) {
			if((webElement.getAttribute("selected") != null) && (webElement.getAttribute("selected").equals("true"))) {
				if(webElement.getText().equals(target_option)) {
					System.out.println("Verfied that for grouping " + grouping + " the option: " + target_option + " selected.");
					ATUReports.add(time +" Verfied that for grouping " + grouping, target_option, target_option, LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("Not verfied that for grouping " + grouping + " the option: " + target_option + " selected.");
					ATUReports.add(time +" Verfied that for grouping " + grouping, target_option, webElement.getText(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				}
				break;
			}
			
		}
	}
	
	// This function get grouping textbox label, and verify that it input have target value
	public void verifyThatForTargetTextboxLabelTheInputHaveTargetValue(String target_label, String target_value_of_input) {
	
		for(int i=0; i<grouping_textbox_labels.size(); i++) {
			if(grouping_textbox_labels.get(i).getText().equals(target_label)) {
				verifyWebElementHaveTargetAttributeWithTargetValue(grouping_textbox_inputs.get(i), "value", target_value_of_input);
				return;
			}
		}
		
		System.out.println("Not find target grouping textbox label.");
		ATUReports.add(time +" Found target grouping textbox label." , "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		Assert.assertTrue(true);
	}
	
	// This function get string ("Year", "Month", "Week") and check if it selected in time aggregation
	public void verifyThatTargetStringSelectedAsTimeAggregation(String target_time_aggregation) {
		for(WebElement webElement: time_aggregation_options) {
			if((webElement.getAttribute("selected") != null) && (webElement.getAttribute("selected").equals("true"))) {
				if(webElement.getText().equals(target_time_aggregation)) {
					System.out.println("Verfied that time aggregation option: " + target_time_aggregation + " is selected.");
					ATUReports.add(time +" Verfiy time aggregation option.", target_time_aggregation, target_time_aggregation, LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("No verfied that time aggregation option: " + target_time_aggregation + " is selected.");
					ATUReports.add(time +" Verfiy time aggregation option.", target_time_aggregation, webElement.getText(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				}
				break;
			}
			
		}
	}
	
	// This function verify that "From" calendar contains the yesterday date.
	public void verifyFromCalendarContainsTheYesterdayDate() {
		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("M/dd/yyyy");

		cal.add(Calendar.DATE, -1);
		
		if(from_date.getAttribute("value").equals(dateFormat.format(cal.getTime()))) {
			System.out.println("Verfied that From calendar contains the yesterday date.");
			ATUReports.add(time +" Verfied that From calendar contains the yesterday date.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verfied that From calendar contains the yesterday date.");
			ATUReports.add(time +" Verfied that From calendar contains the yesterday date.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	// This function verify that "To" calendar contains the today date.
	public void verifyToCalendarContainsTheTodayDate() {
		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("M/dd/yyyy");
		
		if(to_date.getAttribute("value").equals(dateFormat.format(cal.getTime()))) {
			System.out.println("Verfied that To calendar contains the today date.");
			ATUReports.add(time +" Verfied that To calendar contains the today date.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verfied that To calendar contains the today date.");
			ATUReports.add(time +" Verfied that To calendar contains the today date.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	// This function init additional dropbox options (the second column of 4 dropbox options) which represent for instructor report
	public void initFourAdditionalDropboxOptionWhichRepresentInSecondColumnForInstructor() {
		instructor_additional_course_dropbox_options = new ArrayList<WebElement>();
		instructor_additional_instructor_dropbox_options = new ArrayList<WebElement>();
		instructor_additional_recording_dropbox_options = new ArrayList<WebElement>();
		instructor_additional_downloader_dropbox_options = new ArrayList<WebElement>();
		
		int flag = 0;
		for(WebElement webElement: additional_instructor_grouping_dropbox_options) {
			if(webElement.getText().equals("")) {
				continue;
			} else if(webElement.getText().equals("none")) {
				flag++;	
			} 
			
			switch (flag) {
			case 1:
				instructor_additional_course_dropbox_options.add(webElement);
				break;
			case 2:
				instructor_additional_instructor_dropbox_options.add(webElement);
				break;
			case 3:
				instructor_additional_recording_dropbox_options.add(webElement);
				break;
			case 4:
				instructor_additional_downloader_dropbox_options.add(webElement);
			}
		}
	}
	
	// This function get grouping number, and String. It will verify that this string is the selected option of this grouping dropbox.
	public void verifyForInstructorChosenSecondColumnGroupingDropboxSelectedTargetOption(int grouping, String target_option) {
		List<WebElement> grouping_dropbox_to_check;
		switch (grouping) {
		case 1:
			grouping_dropbox_to_check = instructor_additional_course_dropbox_options;
			break;
		case 2:
			grouping_dropbox_to_check = instructor_additional_instructor_dropbox_options;
			break;
		case 3:
			grouping_dropbox_to_check = instructor_additional_recording_dropbox_options;
			break;
		case 4:
		default:
			grouping_dropbox_to_check = instructor_additional_downloader_dropbox_options;
			break;
		}
		
		for(WebElement webElement: grouping_dropbox_to_check) {
			if((webElement.getAttribute("selected") != null) && (webElement.getAttribute("selected").equals("true"))) {
				if(webElement.getText().equals(target_option)) {
					System.out.println("Verfied that for grouping " + grouping + " the option: " + target_option + " selected.");
					ATUReports.add(time +" Verfied that for grouping " + grouping, target_option, target_option, LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("Not verfied that for grouping " + grouping + " the option: " + target_option + " selected.");
					ATUReports.add(time +" Verfied that for grouping " + grouping, target_option, webElement.getText(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				}
				break;
			}	
		}
	}
	
	// This function return selected option of target grouping 1-4 (of first column)
	public String getSelectedOptionOfTargetGroupingOptionOfFirstColumn(int target_grouping) {
		WebElement[] grouping_dropbox_to_check;
		switch (target_grouping) {
		case 1:
			grouping_dropbox_to_check = grouping_1_dropbox_options;
			break;
		case 2:
			grouping_dropbox_to_check = grouping_2_dropbox_options;
			break;
		case 3:
			grouping_dropbox_to_check = grouping_3_dropbox_options;
			break;
		case 4:
		default:
			grouping_dropbox_to_check = grouping_4_dropbox_options;
			break;
		}
		
		for(WebElement webElement: grouping_dropbox_to_check) {
			if((webElement.getAttribute("selected") != null) && (webElement.getAttribute("selected").equals("true"))) {
				System.out.println(webElement.getText());
				return webElement.getText();
			}
		}
		
		
		return null;
	}
	
	// This function get option from grouping 1-4 options and return it integer (of it order)
	public int getOrderValueOfTargetOptionForOptionsInFirstColumn(String target_option) {
		switch (target_option) {
		case "None":
			return 0;
		case "Course":
			return 1;
		case "Instructor":
			return 2;
		case "Recording":
			return 3;
		default:
			return 4;
		}
	}
	
	// This function get grouping 1-4 and target option, and selected that option in grouping 1-4 options list
	public void selectedTargetOptionInFirstColumnTargetGrouping(String target_option, int target_grouping) {
		String selected_option;
		if (temp_webElements_for_first_column_select == null) {
			temp_webElements_for_first_column_select = driver.findElements(By.cssSelector(".ng-pristine.ng-valid"));
		}
		WebElement webElement;
		
		switch (target_grouping) {
		case 1:
			selected_option = getSelectedOptionOfTargetGroupingOptionOfFirstColumn(1);
			webElement = temp_webElements_for_first_column_select.get(3);
			break;
		case 2:
			selected_option = getSelectedOptionOfTargetGroupingOptionOfFirstColumn(2);
			webElement = temp_webElements_for_first_column_select.get(4);
			break;
		case 3:
			selected_option = getSelectedOptionOfTargetGroupingOptionOfFirstColumn(3);
			webElement = temp_webElements_for_first_column_select.get(5);
			break;
		case 4:
		default:
			selected_option = getSelectedOptionOfTargetGroupingOptionOfFirstColumn(4);
			webElement = temp_webElements_for_first_column_select.get(6);
			break;
		}
		
		if(selected_option.equals(target_option)) {
			return;
		}
		
		int selected_option_value = getOrderValueOfTargetOptionForOptionsInFirstColumn(selected_option);
		int target_option_value = getOrderValueOfTargetOptionForOptionsInFirstColumn(target_option);
		
		boolean up;
		int moves;
	
		if(selected_option_value > target_option_value) {
			up = true;
			moves = selected_option_value - target_option_value;
		} else {
			moves = target_option_value - selected_option_value;
			up = false;
		}
		
		webElement.click();
		for(int i=0; i<moves; i++) {
			if(up) {
				webElement.sendKeys(Keys.UP);
			} else {
				webElement.sendKeys(Keys.DOWN);
			}
		}
		webElement.sendKeys(Keys.ENTER);
		
		verifyChosenGroupingDropboxSelectedTargetOption(target_grouping, target_option);
	}
	
	// This function get integer as target grouping, and string to input to second column
	public void sendKeysToTargetGroupingSecondColumnInput(int target_grouping, String target_input) throws InterruptedException {
		int index;
		switch (target_grouping) {
		case 1:
			index = 0;
			break;
		case 2:
			index = 1;
			break;
		case 3:
			index = 2;
			break;
		default:
		case 4:
			index = 4;
			break;
		}
		
		try {
			grouping_textbox_inputs.get(index).clear();
			grouping_textbox_inputs.get(index).sendKeys(target_input);
			Thread.sleep(3000);
			grouping_textbox_inputs.get(index).sendKeys(Keys.ENTER);
			
			System.out.println("Sent keys: " + target_input + ", to target grouping: " + target_grouping + " input.");
			ATUReports.add(time +" Sent keys: " + target_input + ", to target grouping: " + target_grouping + " input.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch(Exception msg) {
			System.out.println("Fail to send keys: " + target_input + ", to target grouping: " + target_grouping + " input.");
			ATUReports.add(time +" Sent keys: " + target_input + ", to target grouping: " + target_grouping + " input.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	// This function verify that only data from selected course is displayed
	public void verifyThatOnlyDataFromTargetSelectedCourseIsDisplayed(String target_selected_course) {
		getIntoFrame(0);
		
		if(row_subtitles.size()==1) {
			exitInnerFrame();
			return;
		}
		
		for(int i=0; i<number_of_rows-1; i++) {
			String padding_left = row_subtitles.get(i).getCssValue("padding-left");
			
			if(padding_left.equals("2.66667px")) {
				if(!row_subtitles.get(i).getText().equals(target_selected_course)) {
					System.out.println("Not verified that only data from selected course is displayed.");
					ATUReports.add(time +" Verify that only data from selected course is displayed.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				}
			}
		}
		
		exitInnerFrame();
		System.out.println("Verified that only data from selected course is displayed.");
		ATUReports.add(time +" Verify that only data from selected course is displayed.", "True.", "True.", LogAs.PASSED, null);
		Assert.assertTrue(true);
		
	}
	
	// This function get integer as grouping, and target option to select, and selects it from second column selection options list
	public void selectTargetOptionFromSecondColumnSelectOptionsForTargetGrouping(String target_option_to_select, int target_group) {
		if(instructor_additional_course_dropbox_options.size() == 0) {
			initFourAdditionalDropboxOptionWhichRepresentInSecondColumnForInstructor();
		}
		
		int index;
		String selected_option;
		switch (target_group) {
		case 1:
			index = 0;
			selected_option = getSelectedOptionOfTargetGroupingOptionOfSecondColumn(1);
			break;
		case 2:
			index = 1;
			selected_option = getSelectedOptionOfTargetGroupingOptionOfSecondColumn(2);
			break;
		case 3:
			index = 2;
			selected_option = getSelectedOptionOfTargetGroupingOptionOfSecondColumn(3);
			break;
		case 4:
		default:
			index = 4;
			selected_option = getSelectedOptionOfTargetGroupingOptionOfSecondColumn(4);
			break;
		}
		
		int selected_option_value = getOrderValueOfTargetOptionForTargetGroupingInSecondColumn(target_group, selected_option);
		int target_option_value = getOrderValueOfTargetOptionForTargetGroupingInSecondColumn(target_group, target_option_to_select);
		
		boolean up;
		int moves;
		
		if(selected_option_value > target_option_value) {
			up = true;
			moves = selected_option_value - target_option_value;
		} else {
			moves = target_option_value - selected_option_value;
			up = false;
		}
		
		driver.findElements(By.cssSelector(".large.ng-scope.ng-pristine.ng-valid")).get(index).click();
		for(int i=0; i<moves; i++) {
			if(up) {
				driver.findElements(By.cssSelector(".large.ng-scope.ng-pristine.ng-valid")).get(index).sendKeys(Keys.UP);
			} else {
				driver.findElements(By.cssSelector(".large.ng-scope.ng-pristine.ng-valid")).get(index).sendKeys(Keys.DOWN);
			}
		}
		driver.findElements(By.cssSelector(".large.ng-scope.ng-pristine.ng-valid")).get(index).sendKeys(Keys.ENTER);
		
	}
	
	
	// This function get integer as grouping number, and String of target option, and return it location as value
	public int getOrderValueOfTargetOptionForTargetGroupingInSecondColumn(int target_grouping, String target_option) {
		List<WebElement> options_list;
		switch (target_grouping) {
		case 1:
			options_list = instructor_additional_course_dropbox_options;
			break;
		case 2:
			options_list = instructor_additional_instructor_dropbox_options;
			break;
		case 3:
			options_list = instructor_additional_recording_dropbox_options;
			break;
		case 4:
		default:
			options_list = instructor_additional_downloader_dropbox_options;
			break;
		}
		
		for(int i=0; i<options_list.size(); i++) {
			if(options_list.get(i).getText().contains(target_option)) {
				return i;
			}
		}
		
		return -1;
	}
	
	
	// This function return selected option of target grouping 1-4 (of second column)
	public String getSelectedOptionOfTargetGroupingOptionOfSecondColumn(int target_grouping) {
		List<WebElement> grouping_dropbox_to_check;
		switch (target_grouping) {
		case 1:
			grouping_dropbox_to_check = instructor_additional_course_dropbox_options;
			break;
		case 2:
			grouping_dropbox_to_check = instructor_additional_instructor_dropbox_options;
			break;
		case 3:
			grouping_dropbox_to_check = instructor_additional_recording_dropbox_options;
			break;
		case 4:
		default:
			grouping_dropbox_to_check = instructor_additional_downloader_dropbox_options;
			break;
		}
		
		for(WebElement webElement: grouping_dropbox_to_check) {
			if((webElement.getAttribute("selected") != null) && (webElement.getAttribute("selected").equals("true"))) {
				System.out.println(webElement.getText());
				return webElement.getText();
			}
		}
		
		
		return null;
	}
	
	
	// This function verify that report result contains X levels of grouping
	public void verifyThatReportResultContainsXLevelsOfGrouping(int target_number_of_levels) {
		getIntoFrame(0);
		HashSet<String> levels = new HashSet<String>();
		for(int i=0; i<number_of_rows-1; i++) {
			String padding_left = row_subtitles.get(i).getCssValue("padding-left");
			levels.add(padding_left);
		}
		
		if(levels.size() == target_number_of_levels) {
			System.out.println("Verified that report results contains: " + target_number_of_levels + " level of grouping.");
			ATUReports.add(time +" Verified that report results contains # levels of grouping.", Integer.toString(target_number_of_levels), Integer.toString(target_number_of_levels), LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verified that report results contains: " + target_number_of_levels + " level of grouping, instead: " + levels.size() + ".");
			ATUReports.add(time +" Verified that report results contains # levels of grouping.", Integer.toString(target_number_of_levels), Integer.toString(levels.size()), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		exitInnerFrame();
	}
	
	// This function verify that each level contains the right content to the downloads in precondition (precondition - foreach level there is same elements, except 1st & 3rd level which is uniq)
	// Input: 	Level 1 - list of elements
	// 			Level 2 - list of elements
	//			Level 3 - list of elements
	//			Level 4 - list of elements
	public void verifyEachLevelContainsTheRightContentToDownloads(List<String> level_1, List<String> level_2, List<String> level_3, List<String> level_4) {
		getIntoFrame(0);
		List<String> temp_level_2 = null;
		List<String> temp_level_4 = null;
		for(int i=0; i<number_of_rows-1; i++) {
			String padding_left = row_subtitles.get(i).getCssValue("padding-left");
			String value = row_subtitles.get(i).getText();
			
			if(padding_left.equals("2.66667px")) {
				
				String element = verifyTargetStringContainOneOfElementOfList(value, level_1);
				level_1.remove(element);
				
				if(level_2 != null) {
					if(((temp_level_2 == null) || (temp_level_2.size() == 0)) && ((temp_level_4 == null) || (temp_level_4.size() == 0))) {
						temp_level_2 = new ArrayList<String>();
						temp_level_2.addAll(level_2);
					} else {	
						System.out.println("Not verfied that each level contains the right content to the downloads in precondition. (Problem with second level)");
						ATUReports.add(time +" Verfied that each level contains the right content to the downloads in precondition.", "True.", "False (problem with second level).", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
						Assert.assertTrue(false);
					}
				}
			} else if ((padding_left.equals("13.3333px")) && (level_2 != null)){
				
				String element = verifyTargetStringContainOneOfElementOfList(value, level_2);
				temp_level_2.remove(element);
				
				
			} else if ((padding_left.equals("26.6667px")) && (level_3 != null) && (level_2 != null)){
				
				String element = verifyTargetStringContainOneOfElementOfList(value, level_3);
				level_3.remove(element);
				
				if(level_3 != null) {
					if((temp_level_4 == null) || (temp_level_4.size() == 0)) {
						temp_level_4 = new ArrayList<String>();
						temp_level_4.addAll(level_4);
					} else {
						System.out.println("Not verfied that each level contains the right content to the downloads in precondition. (Problem with fourth level)");
						ATUReports.add(time +" Verfied that each level contains the right content to the downloads in precondition.", "True.", "False (problem with fourth level).", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
						Assert.assertTrue(false);
					}
				}
				
			} else if ((padding_left.equals("40px")) && (level_4 != null) && (level_3 != null) && (level_2 != null)) {
				
				String element = verifyTargetStringContainOneOfElementOfList(value, level_4);
				temp_level_4.remove(element);
				
			}
		}
		exitInnerFrame();
		
		if(level_1.size() != 0) {
			System.out.println("Not verfied that each level contains the right content to the downloads in precondition. (Problem with first level)");
			ATUReports.add(time +" Verfied that each level contains the right content to the downloads in precondition.", "True.", "False (problem with first level).", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		} else if (level_3 != null) {
			if(level_3.size() != 0) {
				System.out.println("Not verfied that each level contains the right content to the downloads in precondition. (Problem with second level)");
				ATUReports.add(time +" Verfied that each level contains the right content to the downloads in precondition.", "True.", "False (problem with second level).", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		} else {
			System.out.println("Verfied that each level contains the right content to the downloads in precondition.");
			ATUReports.add(time +" Verfied that each level contains the right content to the downloads in precondition.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
	}
	
	
	// This function verify that each level contain the correct number in matrix 
	// Such: Verify each course's 'Recording Downloads' number is correct (According to precondition - 4).
	public void veriyfEachLevelContainTheCorrectNumberInMatrix(int value_level_1, int value_level_2, int value_level_3, int value_level_4) {
		getIntoFrame(0);
		for(int row=0; row<number_of_rows-1; row++) {
			String padding_left = row_subtitles.get(row).getCssValue("padding-left");
			
			if(padding_left.equals("2.66667px")) {
				for(int col=0; col<number_of_cols; col++) {
					if((col+1)%3 == 2) {
						continue;
					}
					if(Integer.parseInt(values_matrix[row][col]) != value_level_1) {
						System.out.println("Not verfied that each level contain correct number in the table.");
						ATUReports.add(time +" Verfied that each level contain the correct number in the table.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
						Assert.assertTrue(false);
					}
				}
			} else if (padding_left.equals("13.3333px")) {
				for(int col=0; col<number_of_cols; col++) {
					if((col+1)%3 == 2) {
						continue;
					}
					if(Integer.parseInt(values_matrix[row][col]) != value_level_2) {
						System.out.println("Not verfied that each level contain correct number in the table.");
						ATUReports.add(time +" Verfied that each level contain the correct number in the table.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
						Assert.assertTrue(false);
					}
				}
			} else if (padding_left.equals("26.6667px")) {
				for(int col=0; col<number_of_cols; col++) {
					if((col+1)%3 == 2) {
						continue;
					}
					if(Integer.parseInt(values_matrix[row][col]) != value_level_3) {
						System.out.println("Not verfied that each level contain correct number in the table.");
						ATUReports.add(time +" Verfied that each level contain the correct number in the table.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
						Assert.assertTrue(false);
					}
				}
			} else if (padding_left.equals("40px")) {
				for(int col=0; col<number_of_cols; col++) {
					if((col+1)%3 == 2) {
						continue;
					}
					if(Integer.parseInt(values_matrix[row][col]) != value_level_4) {
						System.out.println("Not verfied that each level contain correct number in the table.");
						ATUReports.add(time +" Verfied that each level contain the correct number in the table.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
						Assert.assertTrue(false);
					}
				}
			}
		}
		exitInnerFrame();
		
		System.out.println("Verified that each level contain the correct number in the table.");
		ATUReports.add(time +" Verfied that each level contain the correct number in the table.", "True.", "True.", LogAs.PASSED, null);
		Assert.assertTrue(true);
	}
	
	// This function get integer as input. It verify that grand total is correct according to precodition and the input value.
	public void verifyGrandTotalIsCorrectAccordingThePreconditionAndInput(int input_value) {
		for(int col=0; col<number_of_cols; col++) {
			if((col+1) % 3 == 2) {
				continue;
			}
			if(Integer.parseInt(values_matrix[number_of_rows-1][col]) != input_value) {
				System.out.println("Not verfied that grand total values is correct according to precondition.");
				ATUReports.add(time +" Verfied that grand total values is correct according to precondition.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		}
		
		System.out.println("Verfied that grand total values is correct according to precondition.");
		ATUReports.add(time +" Verfied that grand total values is correct according to precondition.", "True.", "True.", LogAs.PASSED, null);
		Assert.assertTrue(true);
	}
	
	// This function get string as username, and verify that this username exist in instructor_additional_instructor_dropbox_options
	public void verifyUsernameExistInInstructorDropbox(String target_username) {
		for(WebElement webElement: additional_instructor_grouping_dropbox_options) {
			if(webElement.getText().contains(target_username)) {
				System.out.println("Verfied that username: " + target_username + " exist in instructor dropbox.");
				ATUReports.add(time +" Verfied that username: " + target_username + " exist in instructor dropbox.", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
				return;
			}
		}
		
		System.out.println("Not verfied that username: " + target_username + " exist in instructor dropbox.");
		ATUReports.add(time +" Verfied that username: " + target_username + " exist in instructor dropbox.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		Assert.assertTrue(false);

	}
	
	// This function verify that only data from the recording which the chosen instructor's recordings is displayed in the report.
	// It gets valid recording list, and check if all that recording appear
	public void verifyThatOnlyDataFromTheRecordingFromTargetListDisplayed(List<String> valid_recording) {
		for(int i=0; i<number_of_rows-1; i++) {
			String padding_left = row_subtitles.get(i).getCssValue("padding-left");

			if (padding_left.equals("26.6667px")) {
				if(!valid_recording.contains(row_subtitles.get(i).getText())) {
					System.out.println("Not verfied that only data from the recording is displayed.");
					ATUReports.add(time +" Verfied that only data from the recording is displayed.", "True.", "False.", LogAs.PASSED, null);
					Assert.assertTrue(false);
				}
			}
			System.out.println("Verfied that only data from the recording is displayed.");
			ATUReports.add(time +" Verfird that only data from the recording is displaued.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(true);
		}
	}

	// This function get list of courses and verify that all these courses appears in course dropbox
	public void verifyThatTargetListOfCoursesAppearsInCourseDropboxOptions(List<String> target_course_list) {
		List<String> course_options_list = getTitleAttributeListOfWebElementList(instructor_additional_course_dropbox_options); 

		for(String course_name: course_options_list) {
			
			Pattern course_name_pattern = Pattern.compile("[\\w\\d\\s]*");
			Matcher course_name_matcher = course_name_pattern.matcher(course_name);
			course_name_matcher.find();
			String extracted_course_name = course_name_matcher.group(0).trim();
			
			if(target_course_list.contains(extracted_course_name)) {
				target_course_list.remove(extracted_course_name);
			}
		}
		
		if(target_course_list.size() == 0) {
			System.out.println("Verfied that target list of courses exist in course dropbox options.");
			ATUReports.add(time +" Verfied that target list of courses exist in course dropbox options.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verfied that target list of courses exist in course dropbox options.");
			ATUReports.add(time +" Verfied that target list of courses exist in course dropbox options.",  "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	// This function get list of string, and verify that each element in following format: <Course name> (<Course id>).
	public void verifyThatEachCourseInCourseListInTargetFormat(List<String> course_list) {
		for(String course_name: course_list) {
			
			
			Pattern course_name_pattern = Pattern.compile("[\\w\\d\\s]*");
			Matcher course_name_matcher = course_name_pattern.matcher(course_name);
			course_name_matcher.find();
			String extracted_course_name = course_name_matcher.group(0);
			
			Pattern course_id_pattern = Pattern.compile("\\((.*)\\)");
			Matcher course_id_matcher = course_id_pattern.matcher(course_name);
			course_id_matcher.find();
			String extracted_course_id = course_id_matcher.group(0);
			
			if((extracted_course_name.length() > 0) && (extracted_course_id.startsWith("(")) && (extracted_course_id.endsWith(")")) && (extracted_course_id.substring(1, extracted_course_id.length()-2).length()>0)) {
//				System.out.println("Pass: " + course_name);
				continue;
			} else {
//				System.out.println("Not pass: " + course_name);
//				System.out.println("Not verfied that every course in course dropbox option list in correct format.");
				ATUReports.add(time +" Verfied that every course in course dropbox option list in correct format.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		}
		
		System.out.println("Verfied that every course in course dropbox option list in correct format.");
		ATUReports.add(time +" Verfied that every course in course dropbox option list in correct format.", "True.", "True.", LogAs.PASSED, null);
		Assert.assertTrue(true);
	}
	
	// This function verify that each course name in course dropbox options in following format: <Course name> (<Course id>)
	public void verifyFormatOfOptionsInSecondColumnCourseDropbox() {
		List<String> course_options_list = getTitleAttributeListOfWebElementList(instructor_additional_course_dropbox_options);
		verifyThatEachCourseInCourseListInTargetFormat(course_options_list);
	}
	
	// This function get all option from drop down box which is opened.
	// TEMP: 19.1. A drop down box is opened with all courses that fit the course name description.
	// TEMP: 19.2. The active course is in the list.
	// TEMP 19.3. Format is: <Course name> (<Course id>).
	public void getAllOptionFromDropDownBoxWhichIsOpenedInSecondColumn() {
		List<WebElement> list_drop_down_box = driver.findElements(By.cssSelector(".dropdown-menu.ng-isolate-scope>li"));
		
		System.out.println("Size:");
		System.out.println(list_drop_down_box.size());
		
		for(WebElement webElement: list_drop_down_box) {
			System.out.println(webElement.getText());
		}
		
		moveToElement(grouping_textbox_inputs.get(1), driver).perform();
		try {
			Thread.sleep(1000);
		} catch (Exception msg) {
			
		}

//		grouping_textbox_inputs.get(1).sendKeys(Keys.DOWN);
//		grouping_textbox_inputs.get(1).sendKeys(Keys.DOWN);
//		list_drop_down_box.get(2).sendKeys(Keys.ENTER);
//		grouping_textbox_inputs.get(1).sendKeys(Keys.ENTER);
//		grouping_textbox_inputs.get(1).sendKeys(Keys.ENTER);
//		grouping_textbox_inputs.get(1).sendKeys(Keys.ENTER);
	}
			
	
}
