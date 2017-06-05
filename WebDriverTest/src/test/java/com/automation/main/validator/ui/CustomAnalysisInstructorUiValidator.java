package com.automation.main.validator.ui;

import com.automation.main.page_helpers.CustomAnalysisDropDown;
import com.automation.main.page_helpers.report.ins.DefaultInsReportSection;
import com.automation.main.page_helpers.report.ins.DownloadInstructorReportSection;
import com.automation.main.page_helpers.report.ins.RecordingInstructorReportSection;
import com.automation.main.page_helpers.report.ins.ViewingInstructorReportByChapterSection;
import com.google.common.collect.Ordering;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.ATUManager;
import utils.WaitDriverUtility;

import java.util.ArrayList;
import java.util.List;


public class CustomAnalysisInstructorUiValidator extends UiValidatorParent {

    public enum DropDownType {
        Course(""), Recording("Recording"), Inst("instructor"), student("student"), Viewer("Viewer");
        private String val;

        DropDownType(String value) {
            this.val = value;
        }
    }

    @Override
    public void validateDropDownUi(WebDriver webDriver) {
        setExpectedReportTypeDropDownOption(expectedReportTypeDropDownOption);

        listOfDropDownObjects.add(new DefaultInsReportSection(webDriver));
        listOfDropDownObjects.add(new DownloadInstructorReportSection(webDriver));
        listOfDropDownObjects.add(new RecordingInstructorReportSection(webDriver));
        listOfDropDownObjects.add(new ViewingInstructorReportByChapterSection(webDriver));

        for (CustomAnalysisDropDown dropDown : listOfDropDownObjects) {
            CustomAnalysisDropDown dd = PageFactory.initElements(webDriver, dropDown.getClass());
            System.out.println("Strating to validate " + dd.getDropDownType());
            dd.navigateToPage();
            dd.verifyUi();
        }
    }

    @Override
    public void validateDropDownResultsSortedInAlphabeticalOrder(WebDriver driver, WebElement element, String expectedText, String textToSearch) {
        WaitDriverUtility.waitToElementVisibility(element, driver);
        element.click();
        WaitDriverUtility.sleepInSeconds(3);
        WaitDriverUtility.waitForChildElementBeDisplayed(element, By.tagName("option"));

        List<WebElement> searchResults = element.findElements(By.cssSelector("option"));
        List<String> textInTitle = getElementTitles(searchResults);
        List<String> displayedTexts = convertListElementToListOfElementText(searchResults);
        validateDropDownByType(expectedText, textInTitle, displayedTexts, searchResults);
        //the first and the last (None,sandBox)text are not sorted, so we need to delete them and then check if is sorted
        textInTitle.remove(0);
        textInTitle.remove(textInTitle.size() - 1);
        boolean ordered = Ordering.natural().isOrdered(textInTitle);
        try {
            ATUManager.asserIsTrueAndReport(ordered, "The drop down result should be sorted in alphabetical order");
        } catch (Exception e) {
            e.printStackTrace();
        }
        element.click();

    }

    private void validateDropDownByType(String dropDownType, List<String> textInTitle, List<String> ddResults, List<WebElement> optionAsElements) {
        DropDownType result = null;
        try {
            result = DropDownType.valueOf(dropDownType);
            switch (result) {
                case Course:
                    validateCourseOptinsFormat(textInTitle);
                    validateTextLonger41HasChanged(optionAsElements);
                    break;
            }
        } catch (IllegalArgumentException e) {
            //other types validation are some
        }

    }

    public List<String> getElementTitles(List<WebElement> elements) {
        List<String> texts = new ArrayList<>();
        for (WebElement webElement : elements) {
            texts.add(webElement.getAttribute("title").toLowerCase());
        }
        return texts;
    }

    ;

    private void validateCourseOptinsFormat(List<String> options) {

        for (String option : options) {
            if (!option.contains("(") && option.contains(")")) {
                ATUManager.asserIsTrueAndReport(false, "The course does not in correct format");
            }
        }
    }

    private void validateTextLonger41HasChanged(List<WebElement> options) {
        for (WebElement element : options) {
            boolean isLonger = element.getAttribute("title").length() > 41;
            if (isLonger) {
                boolean isChanged = isContainsDots(element.getText());
                if (!isChanged) {
                    ATUManager.asserIsTrueAndReport(false, "When the text is longer then 41 characters end of the text should be added '...', text with error " + element.getText());
                }
            }
        }
        ATUManager.asserIsTrueAndReport(true, "When the text is longer then 41 characters end of the text should be added '...'");

    }

    private boolean isContainsDots(String s) {
        char[] chars = s.toCharArray();
        for (char c : chars) {
            if (c == '\u00a6') {
                return true;
            }
        }
        return false;
    }


}
