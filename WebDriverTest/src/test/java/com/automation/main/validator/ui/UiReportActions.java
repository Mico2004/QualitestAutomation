package com.automation.main.validator.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by Lenovo on 31/05/2017.
 */
public interface UiReportActions {

    void validateDropBox(CustomAnalysisInstructorUiValidator.DropDownType downType, WebElement element);

    List<String> convertListElementToListOfElementText(List<WebElement> elements);

    void verifyingReportTypeFunctionality(List<WebElement> actualDropDown);

    void verifyTheSelectedOptionByDefault(WebElement element);

    void validateDropDownUi(WebDriver webDriver);

    void validateDropDownResultsSortedInAlphabeticalOrder(WebDriver driver, WebElement element, String expectedText, String textToSearch);

    void validateIncludedUsageCheckBox(List<WebElement> checkBox);

    void validateTimeAggregation(WebDriver driver);

    void validateGroupsDropDowns(List<WebElement> option);

    void validateCalendar(WebElement element, WebDriver driver, String ngModel);

    void setExpectedReportTypeDropDownOption(String expectedReportTypeDropDownOption);
}
