package com.automation.main.page_helpers.report.ins;

import com.automation.main.page_helpers.CustomAnalysisDropDown;
import com.automation.main.report.page.AbstractInstructorReportPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.ATUManager;

public class ViewingInstructorReportByChapterSection extends AbstractInstructorReportPage implements CustomAnalysisDropDown {


    @FindBy(css = "option[value='1']")
    protected WebElement option;


    public ViewingInstructorReportByChapterSection(WebDriver browser) {
        super(browser);
    }

    @Override
    public void verifyUi() {
        try {
            customAnalysisAdminUiValidator.validateDropDownResultsSortedInAlphabeticalOrder(driver, searchCourseTextBox, "Course", "abc");
            customAnalysisAdminUiValidator.validateDropDownResultsSortedInAlphabeticalOrder(driver, searchRecordingTextBox, "Recording", "Regular");
            customAnalysisAdminUiValidator.validateCalendar(dateFrom, driver, "data.dateFromInput");
            customAnalysisAdminUiValidator.validateCalendar(dateTill, driver, "data.dateTillInput");
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
