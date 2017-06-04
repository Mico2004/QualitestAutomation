package com.automation.main.report.content;

import com.automation.main.report.page.DefaultInstructorContentSection;
import com.automation.main.validator.ui.CustomAnalysisInstructorUiValidator;
import com.automation.main.validator.ui.UiValidatorParent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.ATUManager;
import utils.WaitDriverUtility;

import java.util.Arrays;
import java.util.List;


public class InstructorContentValidator extends UiValidatorParent {

    InstructorDropBoxBuilder instructorDropBoxBuilder;

    public InstructorContentValidator(String coursesTypes) {
        this.instructorDropBoxBuilder = new InstructorDropBoxBuilder(coursesTypes);
    }

    String expectedResultsAfterChooseInsAndCourseRecordings = "none,regularrecordings_a_1,regularrecordings_a_1_deleted";
    String InstructorArecordingsFromAllCourses = "none,RegularRecordings_a_1,RegularRecordings_a_1_deleted,RegularRecordings_b_1,RegularRecording_D_1";
    String listOfExpectedCourseARecordings = "none,RegularRecordings_a_1,RegularRecordings_a_1_deleted,RegularRecordings_a_upb,StudentRecording_a_1,Test1Recording";
    String listOfViewerCourseD = "ReportInsA,ReportInsB";


    @Override
    public void validateDropBox(CustomAnalysisInstructorUiValidator.DropDownType downType, WebElement element ){
        element.click();
        WaitDriverUtility.sleepInSeconds(3);
        WaitDriverUtility.waitForChildElementBeDisplayed(element, By.tagName("option"));
        List<WebElement> options = element.findElements(By.tagName("option"));

        List<String> strings = convertListElementToListOfElementText(options);
        DropBox popualtedDropBox = instructorDropBoxBuilder.getPopualtedDropBox(downType);
        areListsContainsSomeData(popualtedDropBox.getListOfContent(), strings, "Drop down of courses");
    }
    public void validateDropBox(CustomAnalysisInstructorUiValidator.DropDownType downType, WebElement element,String param ){
        element.click();
        WaitDriverUtility.sleepInSeconds(3);
        WaitDriverUtility.waitForChildElementBeDisplayed(element, By.tagName("option"));
        List<WebElement> options = element.findElements(By.tagName("option"));

        List<String> strings = convertListElementToListOfElementText(options);
        List<String> expectedContent = instructorDropBoxBuilder.getAllCoursesOfSpecificIns(param);
        areListsContainsSomeData(expectedContent, strings, "Drop down of courses");
    }
    private void areListsContainsSomeData(List<String> listOfContent, List<String> actualContent, String message) {
        setAllListLowerCase(listOfContent);
        setAllListLowerCase(actualContent);
        int matchCounter = 0;
            for (String content : listOfContent) {
            for (String actual : actualContent) {
                if (actual.startsWith(content)) {
                    matchCounter++;
                }
            }

        }
        ATUManager.asserIsTrueAndReport(matchCounter >= listOfContent.size(), message);
    }

    private void areListsEquals(List<String> listOfContent, List<String> actualContent, String message) {
        setAllListLowerCase(listOfContent);
        setAllListLowerCase(actualContent);
        for (String expected : listOfContent) {
            boolean contains = actualContent.contains(expected);
            if (!contains) {
                ATUManager.asserIsTrueAndReport(contains, message);
            }
        }
        ATUManager.asserIsTrueAndReport(true, message);
    }

    private void setAllListLowerCase(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i).toLowerCase());
        }
    }

    @Override
    public void validateDropDownUi(WebDriver webDriver) {

    }

    @Override
    public void validateDropDownResultsSortedInAlphabeticalOrder(WebDriver driver, WebElement element, String expectedText, String textToSearch) {

    }

    @Override
    public List<String> convertListElementToListOfElementText(List<WebElement> elements) {
        return super.convertListElementToListOfElementText(elements);
    }

    @Override
    public void verifyingReportTypeFunctionality(List<WebElement> actualDropDown) {
        super.verifyingReportTypeFunctionality(actualDropDown);
    }

    @Override
    public void verifyTheSelectedOptionByDefault(WebElement element) {
        super.verifyTheSelectedOptionByDefault(element);
    }

    @Override
    public void validateIncludedUsageCheckBox(List<WebElement> checkBox) {
        super.validateIncludedUsageCheckBox(checkBox);
    }

    @Override
    public void validateTimeAggregation(WebDriver driver) {
        super.validateTimeAggregation(driver);
    }

    @Override
    public void validateGroupsDropDowns(List<WebElement> option) {
        super.validateGroupsDropDowns(option);
    }

    @Override
    public void validateCalendar(WebElement element, WebDriver driver, String ngModel) {
        super.validateCalendar(element, driver, ngModel);
    }

    @Override
    public void setExpectedReportTypeDropDownOption(String expectedReportTypeDropDownOption) {
        super.setExpectedReportTypeDropDownOption(expectedReportTypeDropDownOption);
    }

    public void validateUserWhichDontHaveRecoAsOwner(WebElement dropBox, WebElement expectedDropBoxTochanged) {
        //click at student b
        clickAtDropBoxAndThenClickOnOption(dropBox, "ReportStudB");
        WaitDriverUtility.sleepInSeconds(1);
        //validate
        String color = expectedDropBoxTochanged.getCssValue("color");
        ATUManager.asserIsTrueAndReport(color.contains("rgba(128, 128, 128, 1)") && !expectedDropBoxTochanged.isEnabled(), "the recording box should be greyed and disabled when filtering with user does not owned ");
    }


    public void validateUserOwnedRecordings(WebElement searchInstructor, WebElement searchRecordingTextBox, WebElement courseDropBox) {
        clickAtDropBoxAndThenClickOnOption(courseDropBox, "CouresA");
        WaitDriverUtility.sleepInSeconds(1);
        clickAtDropBoxAndThenClickOnOption(searchInstructor, "ReportInsA");
        List<WebElement> webElements = clickAtDropBoxAndGetOptions(searchRecordingTextBox);
        List<String> strings = convertListElementToListOfElementText(webElements);
        String[] split = expectedResultsAfterChooseInsAndCourseRecordings.split(",");
        areListsContainsSomeData(Arrays.asList(split), strings, "Only the instructor recordings owned by the user AND the selected course recordings are displayed in the \"Recording\" drop down list");
    }

    private List<WebElement> clickAtDropBoxAndGetOptions(WebElement dropBox) {
        dropBox.click();
        WaitDriverUtility.waitForChildElementBeDisplayed(dropBox, By.tagName("option"));
        return dropBox.findElements(By.tagName("option"));
    }

    private void clickAtDropBoxAndThenClickOnOption(WebElement dropBox, String optionValue) {
        dropBox.click();
        WaitDriverUtility.waitForChildElementBeDisplayed(dropBox, By.tagName("option"));
        List<WebElement> options = dropBox.findElements(By.tagName("option"));
        clickAtOptionByValue(optionValue, options);
    }


    public void validateInstructorRecordingsDDFromAllCourses(WebElement searchInstructor, WebElement searchRecordingTextBox, WebElement searchCourseTextBox) {
        clickAtDropBoxAndThenClickOnOption(searchCourseTextBox, "none");
        WaitDriverUtility.sleepInSeconds(1);
        clickAtDropBoxAndThenClickOnOption(searchInstructor, "ReportInsA");

        List<WebElement> webElements = clickAtDropBoxAndGetOptions(searchRecordingTextBox);
        List<String> strings = convertListElementToListOfElementText(webElements);
        String[] splited = InstructorArecordingsFromAllCourses.split(",");
        areListsEquals(Arrays.asList(splited), strings, "Only the instructor recordings owned by the user AND the selected course recordings are displayed in the \"Recording\" drop down list");
    }

    public void validaterRecordingsDDFromCourseA(WebElement searchInstructor, WebElement searchRecordingTextBox, WebElement searchCourseTextBox) {
        clickAtDropBoxAndThenClickOnOption(searchCourseTextBox, "CouresA");
        List<WebElement> webElements = clickAtDropBoxAndGetOptions(searchRecordingTextBox);
        List<String> strings = convertListElementToListOfElementText(webElements);
        String[] split = listOfExpectedCourseARecordings.split(",");
        areListsEquals(Arrays.asList(split), strings, "The drop down list include all recordings from CourseA");

    }
    public   void resetAllFiltesr(DefaultInstructorContentSection defaultInstructorContentSection){
        clickAtDropBoxAndThenClickOnOption(defaultInstructorContentSection.searchCourseTextBox, "none");
        WaitDriverUtility.sleepInSeconds(1);
        clickAtDropBoxAndThenClickOnOption(defaultInstructorContentSection.searchInstructor, "none");
        WaitDriverUtility.sleepInSeconds(1);
        clickAtDropBoxAndThenClickOnOption(defaultInstructorContentSection.searchRecordingTextBox, "none");
        WaitDriverUtility.sleepInSeconds(1);
        clickAtDropBoxAndThenClickOnOption(defaultInstructorContentSection.searchViewer, "none");
        WaitDriverUtility.sleepInSeconds(1);
    }

    public void validateSelectedRecordingChangeTheCourseFilter(WebElement searchInstructor, WebElement searchRecordingTextBox, WebElement searchCourseTextBox) {
        clickAtDropBoxAndThenClickOnOption(searchRecordingTextBox, "RegularRecordings_a_1");
        WaitDriverUtility.sleepInSeconds(1);
        Select searchResult = new Select(searchCourseTextBox);
        WebElement firstSelectedOption = searchResult.getFirstSelectedOption();
        String selectedOption = firstSelectedOption.getText();
        ATUManager.asserIsTrueAndReport(selectedOption.startsWith("CouresA"),"The course filter selection changes to the  selected recording's course");
    }

    public void validateEnrollmentUsersToCourseD(WebElement viewerDroBox,WebElement searchCourseTextBox) {
        List<WebElement> webElements = null;
        clickAtDropBoxAndThenClickOnOption(searchCourseTextBox, "CouresD");
        WaitDriverUtility.sleepInSeconds(1);
        webElements = clickAtDropBoxAndGetOptions(viewerDroBox);
        List<String> strings = convertListElementToListOfElementText(webElements);
        String[] split = listOfViewerCourseD.split(",");
        areListsContainsSomeData(Arrays.asList(split), strings, "enrollments are displayed in the drop down list, for both past and active enrollments");
    }
}
