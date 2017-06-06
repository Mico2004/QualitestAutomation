package com.automation.main.report;

import com.automation.main.report.content.DropBox;
import com.automation.main.report.page.DefaultAdminAutocompleteContentSection;
import com.automation.main.validator.ui.CustomAnalysisInstructorUiValidator;
import com.automation.main.validator.ui.UiValidatorParent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.ATUManager;
import utils.WaitDriverUtility;

import java.util.List;


public class AdminAutocompleteValidator extends UiValidatorParent {

    private AdminDropBoxBuilder adminDropBoxBuilder;
    private WebDriver driver;

    public AdminAutocompleteValidator(String coursesTypes) {
        this.adminDropBoxBuilder = new AdminDropBoxBuilder(coursesTypes);
    }




    public enum TypeOfActions {
        Course(""), SingelCourse("ExactCourseId"), Instructors("instructors"), SingleIns("single ins"),students("students"), studentRecording("studentRecording"), AllUsers("all users");
        private String val;

        TypeOfActions(String value) {
            this.val = value;
        }
    }


    @Override
    public void validateDropBox(CustomAnalysisInstructorUiValidator.DropDownType downType, WebElement element ){

    }

    public void validateDropBoxSingleResult(TypeOfActions  typeOfActions, WebElement element ){
        DropBox popualtedDropBox = adminDropBoxBuilder.getPopualtedDropBox(typeOfActions);
        String expectedResult = popualtedDropBox.getListOfContent().get(0);
        element.sendKeys(expectedResult);
        WaitDriverUtility.sleepInSeconds(3);
        String searchResult = WaitDriverUtility.getElementParent(element).getText();
        ATUManager.asserIsTrueAndReport(searchResult.contains(expectedResult),"Autocomplete list contains the value you specified");
        element.clear();
    }

    public void validateDropBox(AdminAutocompleteValidator.TypeOfActions typeOfActions, WebElement element,String param ){
        WaitDriverUtility.waitToElementVisibility(element, driver);
        element.sendKeys(param);
        WaitDriverUtility.sleepInSeconds(3);
        WebElement elementParent = WaitDriverUtility.getElementParent(element);
        WaitDriverUtility.waitForChildElementBeDisplayed(elementParent, By.cssSelector("ul>li>a"));
        List<WebElement> searchResults = elementParent.findElements(By.cssSelector("ul>li>a"));
        List<String> resultsInText = convertListElementToListOfElementText(searchResults);
        DropBox popualtedDropBox = adminDropBoxBuilder.getPopualtedDropBox(typeOfActions);
        areListsContainsSomeData(popualtedDropBox.getListOfContent(), resultsInText, "Drop down of courses");
        element.clear();
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

    private void clearFilter(WebElement dropBox) {
       dropBox.clear();
    }

    public   void resetAllFiltesr(DefaultAdminAutocompleteContentSection defaultContentSection){
        clearFilter(defaultContentSection.searchCourseTextBox);
        clearFilter(defaultContentSection.searchInstructor);
        clearFilter(defaultContentSection.searchRecordingTextBox);
        clearFilter(defaultContentSection.searchViewer);
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public void validateOptionIsDisplayedAfterClicked(TypeOfActions typeOfActions, WebElement element) {
        String expectedResult = adminDropBoxBuilder.getCourseNameByType("C");
        element.sendKeys(expectedResult);
        WaitDriverUtility.sleepInSeconds(3);
        String searchResult = WaitDriverUtility.getElementParent(element).getText();
        WaitDriverUtility.getElementParent(element).findElement(By.cssSelector("ul>li")).click();
        String displayedValue = element.getAttribute("value");
        ATUManager.asserIsTrueAndReport(displayedValue.equals(searchResult),"The course name and id is displayed in the filter");
    }

    public void verifyInstructorAreFilttirngByCourse(WebElement element) {
        List<String> users = adminDropBoxBuilder.getInstructorsOfCurse("C");
        element.sendKeys("ReportIns");
        WebElement elementParent = WaitDriverUtility.getElementParent(element);
        WaitDriverUtility.waitForChildElementBeDisplayed(elementParent, By.cssSelector("ul>li>a"));
        List<WebElement> searchResults = elementParent.findElements(By.cssSelector("ul>li>a"));
        List<String> resultsInText = convertListElementToListOfElementText(searchResults);
        searchResults.get(0).click();
        areListsContainsSomeData(users, resultsInText, "Type a common 3 letters sub string of two instructors, one which is enrolled to the course and one without enrollment or past enrollments to the course");
    }
    public void verifyViewerAreFilttirngByCourse(WebElement element) {
        WaitDriverUtility.waitToElementVisibility(element, driver);
        element.sendKeys("Report");
        WaitDriverUtility.sleepInSeconds(3);
        WebElement elementParent = WaitDriverUtility.getElementParent(element);
        WaitDriverUtility.waitForChildElementBeDisplayed(elementParent, By.cssSelector("ul>li>a"));
        List<WebElement> searchResults = elementParent.findElements(By.cssSelector("ul>li>a"));
        List<String> resultsInText = convertListElementToListOfElementText(searchResults);
        List<String> users = adminDropBoxBuilder.getUesrsOfCourse("C");
        areListsContainsSomeData(users, resultsInText, "Verify that \"Viewer\" dropbox contains  contains all of the USERS enrolled and unerolled to the instructor's courses including past enrollments");
        element.clear();
    }

    public void validatUserThatDoesnotHaveAnyRecordingsAsAnOwner(DefaultAdminAutocompleteContentSection defaultAdminAutocompleteContentSection) {
        String courseNameByType = adminDropBoxBuilder.getCourseNameByType("C");
        defaultAdminAutocompleteContentSection.searchCourseTextBox.sendKeys(courseNameByType);
        WebElement elementParent = WaitDriverUtility.getElementParent(defaultAdminAutocompleteContentSection.searchCourseTextBox);
        WaitDriverUtility.sleepInSeconds(3);

        WaitDriverUtility.waitForChildElementBeDisplayed(elementParent,By.cssSelector("ul>li"));
        elementParent.findElement(By.cssSelector("ul>li")).click();

        List<String> a = adminDropBoxBuilder.getInstructorsOfCurse("A");
        String instructorName = a.get(0);
        defaultAdminAutocompleteContentSection.searchInstructor.sendKeys(instructorName);
        WebElement searchIns = WaitDriverUtility.getElementParent(defaultAdminAutocompleteContentSection.searchInstructor);
        WaitDriverUtility.waitForChildElementBeDisplayed(searchIns,By.cssSelector("ul>li"));
        String noData = searchIns.findElement(By.cssSelector("ul>li")).getText();
        ATUManager.asserIsTrueAndReport(noData.equals("No data"),"");


    }
}
