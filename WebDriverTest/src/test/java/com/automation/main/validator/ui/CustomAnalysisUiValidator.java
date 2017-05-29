package com.automation.main.validator.ui;

import com.automation.main.page_helpers.*;
import com.google.common.collect.Ordering;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.ATUManager;
import utils.WaitDriverUtility;
import java.util.ArrayList;
import java.util.List;


public class CustomAnalysisUiValidator extends UiValidatorParent {

    private String expectedReportTypeDropDownOption = "Viewing report by chapter,Recording Report,Downloading Report,Quota Usage Report";

    List<CustomAnalysisDropDown> listOfDropDownObjects = new ArrayList<>();

    private String expectedIncludedUsageCheckBoxTexts = "Desktop/laptop viewing,Desktop/laptop webcast viewing,Mobile viewing,Mobile webcast viewing";

    private String timeAggregationDD = "Week,Month,Year";

    String groupDropDown = "none,course,instructor,recording";



    public void verifyingReportTypeFunctionality(List<WebElement> actualDropDown) {
        List<String> options = convertListElementToListOfElementText(actualDropDown);
        String[] expectedList = expectedReportTypeDropDownOption.split(",");
        verifyTheSelectedOptionByDefault(actualDropDown.get(0));

        for (String expected : expectedList) {
            boolean contains = options.contains(expected.toLowerCase());
            if (!contains) {
                ATUManager.asserIsTrueAndReport(false, "Report Type DropDown does not contain '" + expected + "'", "", "");
            }
        }
        ATUManager.asserIsTrueAndReport(true, "Report Type DropDown contain the expected dropDown  ", "", "");

    }

    private void verifyTheSelectedOptionByDefault(WebElement element) {
        String expectedValue = "true";
        String actualValue = element.getAttribute("selected");
        String text = element.getText();
        boolean result = expectedValue.equals(actualValue) && text.equals("Viewing report by course, recording, instructor, viewer");
        ATUManager.asserIsTrueAndReport(result, "the selected option by default should be 'Viewing report by course, recording, instructor, viewer' ", "", "");
    }

    public void validateDropDownUi(WebDriver webDriver) {

        listOfDropDownObjects.add(new ViewingReportByChapterDiv(webDriver));
        listOfDropDownObjects.add(new RecordingReportSection(webDriver));
        listOfDropDownObjects.add(new DownloadReportSection(webDriver));
        listOfDropDownObjects.add(new QuotaReportSection(webDriver));
        listOfDropDownObjects.add(new DefaultReportSection(webDriver));

        for (CustomAnalysisDropDown dropDown : listOfDropDownObjects) {
            CustomAnalysisDropDown dd = PageFactory.initElements(webDriver, dropDown.getClass());
            System.out.println("Strating to validate "+dd.getDropDownType());
            dd.navigateToPage();
            dd.verifyUi();
        }
        System.out.println("dd");
    }

    public void validateDropDownResultsSortedInAlphabeticalOrder(WebDriver driver, WebElement element, String expectedText, String textToSearch) {
        WaitDriverUtility.waitToElementVisibility(element, driver);
        element.sendKeys(textToSearch);
        WaitDriverUtility.sleepInSeconds(3);
        WebElement elementParent = WaitDriverUtility.getElementParent(element);

        WaitDriverUtility.waitForChildElementBeDisplayed(elementParent, By.cssSelector("ul>li>a"));

        List<WebElement> searchResults = elementParent.findElements(By.cssSelector("ul>li>a"));
        List<String> resultsInText = convertListElementToListOfElementText(searchResults);
        boolean ordered = Ordering.natural().isOrdered(resultsInText);
        ATUManager.asserIsTrueAndReport(ordered, "The drop down result should be sorted in alphabetical order");
        element.clear();

    }

    public void validateCalendar(WebElement element, WebDriver driver,String ngModel) {
        element.click();
        WebElement dataPicker = WaitDriverUtility.waitForElementBeDisplayed(driver, By.cssSelector("div[data-ng-model=\""+ngModel+"\"]"), 10);
        ATUManager.asserIsTrueAndReport(dataPicker.isDisplayed(), "The calendar should be displayed ");
    }

    public void validateIncludedUsageCheckBox(List<WebElement> checkBox) {
        List<String> labels = convertListElementToListOfElementText(checkBox);
        String[] split = expectedIncludedUsageCheckBoxTexts.split(",");
        for (String singleText:split) {
            boolean contains = labels.contains(singleText.toLowerCase());
            if (!contains){
                ATUManager.asserIsTrueAndReport(false,singleText+" does not contain");
            }
        }
        ATUManager.asserIsTrueAndReport(true,"Included Usage checkBox contains the expected texts");
    }
    public void validateTimeAggregation(WebDriver driver){
        String cssSelector ="select[ng-model=\"$parent.time\"]";
        WebElement selectElement = driver.findElement(By.cssSelector(cssSelector));
        String attribute = selectElement.getAttribute("ng-init");

        boolean isContainsYear = attribute.contains("Year");
        ATUManager.asserIsTrueAndReport(isContainsYear,"Year is set bu default");

        selectElement.click();
        List<WebElement> options = driver.findElements(By.cssSelector(cssSelector + ">option"));
        List<String> textsOfDropDown = convertListElementToListOfElementText(options);
        String[] split = timeAggregationDD.split(",");
        for (String singleText:split) {
            boolean contains = textsOfDropDown.contains(singleText.toLowerCase());
            if (!contains){
                ATUManager.asserIsTrueAndReport(false,singleText+" does not contain in time aggregation DD");
            }
        }
        ATUManager.asserIsTrueAndReport(true," Time aggregation DD contains the expected options");
    }
    public void validateGroupsDropDowns(List<WebElement> option) {
        List<String> actualList = convertListElementToListOfElementText(option);
        String[] splitted = groupDropDown.split(",");
        for (String expectedValue : splitted) {
            boolean contains = actualList.contains(expectedValue.toLowerCase());
            if (!contains) {
                ATUManager.asserIsTrueAndReport(false, "The dropDown does not contain " + expectedValue + " text");
            }
        }
        ATUManager.asserIsTrueAndReport(true, "The dropDown contain text required text");

    }
}
