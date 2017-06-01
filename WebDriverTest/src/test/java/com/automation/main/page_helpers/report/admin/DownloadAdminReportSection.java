package com.automation.main.page_helpers.report.admin;

import com.automation.main.page_helpers.CustomAnalysisDropDown;
import com.automation.main.report.page.AbstractAdminReportPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.ATUManager;


public class DownloadAdminReportSection extends AbstractAdminReportPage implements CustomAnalysisDropDown {

    @FindBy(css = "option[value='3']")
    protected WebElement option;

    public DownloadAdminReportSection(WebDriver browser) {
        super(browser);
    }

    @Override
    public void verifyUi() {
        try {
            customAnalysisAdminUiValidator.validateDropDownResultsSortedInAlphabeticalOrder(driver,searchCourseTextBox,"Course","abc");
            customAnalysisAdminUiValidator.validateDropDownResultsSortedInAlphabeticalOrder(driver,searchInstructor,"Instructor","instructorTe");
            customAnalysisAdminUiValidator.validateDropDownResultsSortedInAlphabeticalOrder(driver,searchRecording,"Recording","Regular");
            customAnalysisAdminUiValidator.validateDropDownResultsSortedInAlphabeticalOrder(driver,searchDownloader,"Downloader","stu");
            customAnalysisAdminUiValidator.validateCalendar(dateFrom,driver,"data.dateFromInput");
            customAnalysisAdminUiValidator.validateCalendar(dateTill,driver,"data.dateTillInput");
            customAnalysisAdminUiValidator.validateGroupsDropDowns(groupOne);
            customAnalysisAdminUiValidator.validateGroupsDropDowns(groupTwo);
            customAnalysisAdminUiValidator.validateGroupsDropDowns(groupThree);
            customAnalysisAdminUiValidator.validateGroupsDropDowns(groupFour);
            customAnalysisAdminUiValidator.validateTimeAggregation(driver);
        } catch (Exception e) {
            ATUManager.asserIsTrueAndReport(false, "Some of elements not found on " + getDropDownType() + " section" + e);
            return;
        }
        ATUManager.asserIsTrueAndReport(true, "The whole expected elements are found"+ getDropDownType() + " section");
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
