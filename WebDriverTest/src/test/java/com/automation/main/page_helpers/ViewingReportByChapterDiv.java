package com.automation.main.page_helpers;

import com.automation.main.report.page.AbstractReportPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.ATUManager;

public class ViewingReportByChapterDiv extends AbstractReportPage implements CustomAnalysisDropDown {


    @FindBy(css = "option[value='1']")
    protected WebElement option;


    public ViewingReportByChapterDiv(WebDriver browser) {
        super(browser);
    }

    @Override
    public void verifyUi() {
        try {
            customAnalysisUiValidator.validateDropDownResultsSortedInAlphabeticalOrder(driver, searchCourseTextBox, "Course", "abc");
            customAnalysisUiValidator.validateDropDownResultsSortedInAlphabeticalOrder(driver, searchRecordingTextBox, "Recording", "Regular");
            customAnalysisUiValidator.validateCalendar(dateFrom, driver, "data.dateFromInput");
            customAnalysisUiValidator.validateCalendar(dateTill, driver, "data.dateTillInput");
        } catch (Exception e) {
            ATUManager.asserIsTrueAndReport(false, "Some of elements not found on " + getDropDownType() + " section" + e);

            return;
        }
        ATUManager.asserIsTrueAndReport(true, "The whole expected elements are found on " + getDropDownType() + " section");

    }

    @Override
    public String getDropDownType() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void navigateToPage() {
        waitForVisibility(typeReport);
        clickElement(typeReport);
        clickElement(option);
    }

}
