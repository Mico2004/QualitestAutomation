package com.automation.main.validator.ui;

import com.automation.main.impersonate.helper.ElementCoordinates;
import com.automation.main.impersonate.helper.LocationCalculator;
import com.automation.main.page_helpers.RecordingHelperPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.ATUManager;
import utils.WaitDriverUtility;

import java.util.ArrayList;
import java.util.List;


public class CourseUiValidator{

    RecordingHelperPage record;
    LocationCalculator locationCalculator;
    String expectedViewDropDownText = "Sort By,View,Title,Date,Duration,Tags,(Show all recordings)";

    String expectedRecordingsTask = "To make changes to recording(s) please select one or more recordings from the list below using the checkboxes on the right.,Move,Copy,Delete,Publish,Tag,Upload to YouTube,Request Captions,Download,Edit Recording,Edit Recording Properties,Share Recording";
    String expectedCourseTaskTexts = "Course Settings,Upload a Recording,Upload Video File,Upload Audio File,Add Additional Content File,Add Additional Content Link,Get Live Webcast,Move to Past Courses,Subscribe to Your Course's...,RSS Feed,Podcast,Video Podcast";

    public CourseUiValidator(RecordingHelperPage recordingHelperPage) {
        this.record = recordingHelperPage;
        this.locationCalculator = new LocationCalculator();
    }

    public void validateStartRecordingsAtRightTop(){
        ElementCoordinates startNewRecordings = new ElementCoordinates(record.start_recording_button, 1330, 1660, 130, 200);
        locationCalculator = new LocationCalculator();
        locationCalculator.isElementLocatedAtExpectedLocation(startNewRecordings);
    }
    public void validateTabsAreDisplayedAtCorrectPosition() {
        ElementCoordinates startNewRecordings = new ElementCoordinates(record.start_recording_button, 1330, 1660, 130, 200);
        locationCalculator = new LocationCalculator();
        locationCalculator.isElementLocatedAtExpectedLocation(startNewRecordings);
        //verofy "Start test " is located at expected
        ElementCoordinates startTestButton = new ElementCoordinates(record.start_test_button, 1300, 1570, 120, 200);
        locationCalculator.isElementLocatedAtExpectedLocation(startTestButton);

        //verify start test button is on the left of the "Start a record" button
        boolean isStartTestIsOnleft = locationCalculator.isElementDisplayedToLefElement(record.start_recording_button, record.start_test_button);
        ATUManager.asserIsTrueAndReport(isStartTestIsOnleft, "verify start test button is on the left of the Start a record button", "", "");

        //The "Recordings" tab is displayed under the course name
        boolean recordingsTabUnderCourseName = locationCalculator.isElementUnderOnAnotherElement(record.recordings_tab, record.course_title);
        ATUManager.asserIsTrueAndReport(recordingsTabUnderCourseName, "The Recordings tab is displayed under the course name", "", "");

        //The "Bookmark" tab is displayed to the right of "Recordings" tab
        boolean BookmarkTabIsDisplayedToTheRightofRecordingsTab = locationCalculator.isElementOnRightToAnotherElement(record.recordings_tab, record.bookmarks_tab);
        ATUManager.asserIsTrueAndReport(BookmarkTabIsDisplayedToTheRightofRecordingsTab, "The Bookmark tab is displayed to the right of Recordings tab", "", "");

        //The "Additional Content" tab is displayed next to the ‘Bookmarks’ tab
        boolean AdditionalContentTabOnRightToBookmarks = locationCalculator.isElementOnRightToAnotherElement(record.bookmarks_tab, record.additional_content_tab);
        ATUManager.asserIsTrueAndReport(AdditionalContentTabOnRightToBookmarks, "The additional content tab is displayed to the right of bookmarks tab", "", "");

        //The "Student Recordings" tab is displayed next to the ‘Additional Content’ tab
        boolean StudentRecordingsOnRightToAdditionalContent = locationCalculator.isElementOnRightToAnotherElement(record.additional_content_tab, record.student_recordings_tab);
        ATUManager.asserIsTrueAndReport(StudentRecordingsOnRightToAdditionalContent, "The student recordings tab is displayed to the right of additional content tab", "", "");

        //The "Tests" tab is displayed next to the ‘Student Recordings’ tab
        boolean testIsOneRightToStudentRecordings = locationCalculator.isElementOnRightToAnotherElement(record.student_recordings_tab, record.test_tab);
        ATUManager.asserIsTrueAndReport(testIsOneRightToStudentRecordings, "The test tab is displayed to the right of student recordings tab", "", "");

        boolean isCourseTaskOnRightToViewButton = locationCalculator.isElementOnRightToAnotherElement(record.view_button, record.course_task_button);
        ATUManager.asserIsTrueAndReport(isCourseTaskOnRightToViewButton, "The Course Tasks drop-down is displayed next to the View element", "", "");

        boolean isAllCheckBoxOnrightToRecordingTasjButton = locationCalculator.isElementOnRightToAnotherElement(record.recording_tasks_button, record.check_all_checkbox);
        ATUManager.asserIsTrueAndReport(isCourseTaskOnRightToViewButton, "The check box to select/deselect all the records in the course is displayed on the right of the Recording Task.", "", "");


    }

    public void validateAllDropDownOnPage() {
        List<WebElement> theDropDownElements = null;
        validateViewDropDown();

        theDropDownElements = record.getRecordingsAsElements();
        validateDropDown(theDropDownElements, "Recording Tasks", expectedRecordingsTask);

        validateCourseTaskDropDown();
    }

    public void validateViewDropDown() {
        List<WebElement> theDropDownElements = null;
        theDropDownElements = record.getTheViewDropDownElements();
        validateDropDown(theDropDownElements, "View", expectedViewDropDownText);
    }

    public void validateCourseTaskDropDown() {
        List<WebElement> theDropDownElements = null;
        theDropDownElements = record.getCourseTaskDropDownElements();
        validateDropDown(theDropDownElements, "Course Tasks", expectedCourseTaskTexts);
    }

    private void validateDropDown(List<WebElement> dropDownElements, String dropDownName, String expectedText) {
        List<String> textElements = convertListElementToListOfElementText(dropDownElements);
        boolean areTextElementDisplayed = verifyTwoListsAreEqule(expectedText.split(","), textElements);
        ATUManager.asserIsTrueAndReport(areTextElementDisplayed, "Verify " + dropDownName + " drop down texts are displayed", "", "");
        boolean isAlign = isDropDownAligne(dropDownElements, dropDownName);
        ATUManager.asserIsTrueAndReport(isAlign, "Verify " + dropDownName + "down texts are alignToLeft", "", "");

    }

    public List<String> convertListElementToListOfElementText(List<WebElement> elements) {
        List<String> texts = new ArrayList<>();
        for (WebElement webElement : elements) {
            texts.add(webElement.getText().toLowerCase());
        }
        return texts;
    }

    private boolean isDropDownAligne(List<WebElement> theViewDropDownElements, String ignorElementByText) {
        boolean isAlign = true;
        for (WebElement option : theViewDropDownElements) {
            String textAlign = option.getCssValue("text-align");
            if (!textAlign.equals("left") && !option.getText().equals(ignorElementByText)) {
                isAlign = false;
            }
        }
        return isAlign;
    }



    private boolean verifyTwoListsAreEqule(String[] listA, List<String> listB) {
        int expectedMatches = 0;
        for (String objectA : listA) {
            for (String objecB : listB) {
                if (objectA.equals(objecB)) {
                    expectedMatches++;
                }
            }
        }
        if (expectedMatches == listA.length) {
            return true;
        }
        return false;
    }

    public void validateRecordingInformation() {
        record.waitForVisibility(record.first_recording);
        record.first_recording.click();
        boolean timeFormatCorrect = isTimeFormatCorrect();
        ATUManager.asserIsTrueAndReport(timeFormatCorrect,"The record should be in the following Length: X:XX:XX","","");
        boolean isrecordedByDisplayed = isRecordedByDisplayed();
        ATUManager.asserIsTrueAndReport(isrecordedByDisplayed,"Verify the format : 'Recorded by: Username.'","","");
        boolean isdateFormatCorrrect = isDateFormatCorrrect();
        ATUManager.asserIsTrueAndReport(isdateFormatCorrrect,"expecting the following date format: dd/mm/yyyy","","");
    }

    private boolean isTimeFormatCorrect() {
        record.waitForVisibility(record.first_recording);
        WebElement a = WaitDriverUtility.getElementParent(record.first_recording);
        WebElement div = WaitDriverUtility.getElementParent(a);
        String recordingsLenght = div.findElement(By.cssSelector("div>span>#RecordingLength1")).getText();
        return recordingsLenght.matches("length: ([0-9]:[0-9][0-9]:[0-9][0-9])");
    }

    private boolean isRecordedByDisplayed() {
        record.waitForVisibility(record.first_recording);
        WebElement a = WaitDriverUtility.getElementParent(record.first_recording);
        WebElement div = WaitDriverUtility.getElementParent(a);
        String recordingsLenght = div.findElement(By.cssSelector("div>#RecordedBy1")).getText();
        return recordingsLenght.matches("recorded by: (.*)");
    }

    private boolean isDateFormatCorrrect() {
        record.waitForVisibility(record.first_recording);
        String recordingsLenght = record.driver.findElement(By.id("RecordingDate1")).getText();
        return recordingsLenght.matches("[1-9]{1,2}(/|-)[0-9]{2}(/|-)[0-9]{4}");
    }

    private boolean isBookmark() {
        record.waitForVisibility(record.first_recording);
        String recordingsLenght = record.driver.findElement(By.id("RecordingDate1")).getText();
        return recordingsLenght.matches("[1-9]{1,2}(/|-)[0-9]{2}(/|-)[0-9]{4}");
    }

    public void checkIfChaptersAreDisplayed(){
        boolean displayed = record.driver.findElements(By.className("panel-body")).get(0).isDisplayed();
        ATUManager.asserIsTrueAndReport(displayed,"The recording chapters are displayed to the USER","","");
    }
}
