package com.automation.main.validator.ui;

import atu.testng.reports.ATUReports;
import com.automation.main.page_helpers.CustomAnalysisDropDown;
import com.automation.main.report.content.DropBox;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.ATUManager;
import utils.WaitDriverUtility;

import java.util.ArrayList;
import java.util.List;


public abstract class UiValidatorParent implements UiReportActions {

    protected String expectedReportTypeDropDownOption = "Viewing report by chapter,Recording Report,Downloading Report,Quota Usage Report";

    protected List<CustomAnalysisDropDown> listOfDropDownObjects = new ArrayList<>();

    protected String expectedIncludedUsageCheckBoxTexts = "Desktop/laptop viewing,Desktop/laptop webcast viewing,Mobile viewing,Mobile webcast viewing";

    protected String timeAggregationDD = "Week,Month,Year";

    String groupDropDown = "none,course,instructor,recording";

    @Override
    public void validateDropBox(CustomAnalysisInstructorUiValidator.DropDownType downType, WebElement element) {

    }

    @Override
    public List<String> convertListElementToListOfElementText(List<WebElement> elements) {
        List<String> texts = new ArrayList<>();
        for (WebElement webElement : elements) {
            texts.add(webElement.getText().toLowerCase());
        }
        return texts;
    }

    @Override
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

    @Override
    public void verifyTheSelectedOptionByDefault(WebElement element) {
        String expectedValue = "true";
        String actualValue = element.getAttribute("selected");
        String text = element.getText();
        boolean result = expectedValue.equals(actualValue) && text.equals("Viewing report by course, recording, instructor, viewer");
        ATUManager.asserIsTrueAndReport(result, "the selected option by default should be 'Viewing report by course, recording, instructor, viewer' ", "", "");
    }


    @Override
    public void validateIncludedUsageCheckBox(List<WebElement> checkBox) {
        List<String> labels = convertListElementToListOfElementText(checkBox);
        String[] split = expectedIncludedUsageCheckBoxTexts.split(",");
        for (String singleText : split) {
            boolean contains = labels.contains(singleText.toLowerCase());
            if (!contains) {
                ATUManager.asserIsTrueAndReport(false, singleText + " does not contain");
            }
        }
        ATUManager.asserIsTrueAndReport(true, "Included Usage checkBox contains the expected texts");
    }

    @Override
    public void validateTimeAggregation(WebDriver driver) {
        String cssSelector = "select[ng-model=\"$parent.time\"]";
        WebElement selectElement = driver.findElement(By.cssSelector(cssSelector));
        String attribute = selectElement.getAttribute("ng-init");

        boolean isContainsYear = attribute.contains("Year");
        ATUManager.asserIsTrueAndReport(isContainsYear, "The default time aggregation is year" );

        selectElement.click();
        List<WebElement> options = driver.findElements(By.cssSelector(cssSelector + ">option"));
        List<String> textsOfDropDown = convertListElementToListOfElementText(options);
        String[] split = timeAggregationDD.split(",");
        for (String singleText : split) {
            boolean contains = textsOfDropDown.contains(singleText.toLowerCase());
            if (!contains) {
                ATUManager.asserIsTrueAndReport(false, singleText + " does not contain in time aggregation dropDown");
            }
        }
        ATUManager.asserIsTrueAndReport(true, " Time aggregation DD contains the expected options");
    }

    @Override
    public void validateGroupsDropDowns(List<WebElement> option) {
        List<String> actualList = convertListElementToListOfElementText(option);
        WaitDriverUtility.getElementParent(option.get(0)).click();
        String[] splitted = groupDropDown.split(",");
        for (String expectedValue : splitted) {
            boolean contains = actualList.contains(expectedValue.toLowerCase());
            if (!contains) {
                ATUManager.asserIsTrueAndReport(false, "The dropDown does not contain " + expectedValue + " text");
            }
        }
        ATUManager.asserIsTrueAndReport(true, "The dropBox contain required texts as expected");

    }

    @Override
    public void validateCalendar(WebElement element, WebDriver driver, String ngModel) {
        element.click();
        WebElement dataPicker = WaitDriverUtility.waitForElementBeDisplayed(driver, By.cssSelector("div[data-ng-model=\"" + ngModel + "\"]"), 10);
        ATUManager.asserIsTrueAndReport(dataPicker.isDisplayed(), "The calendar should be displayed ");
        ATUReports.getWebDriver().findElement(By.tagName("Body")).click();
    }

    @Override
    public void setExpectedReportTypeDropDownOption(String expectedReportTypeDropDownOption) {
        this.expectedReportTypeDropDownOption = expectedReportTypeDropDownOption;
    }

    protected void clickAtOptionByValue(String value, List<WebElement> options) {
        WebElement match = null;
        for (WebElement option : options) {
            if (option.getAttribute("title").startsWith(value) || option.getText().startsWith(value)) {
                match = option;
            }
        }
        if (match != null) {
            match.click();
        } else {
            throw new RuntimeException("Couldn't find the expected option");
        }
    }
}
