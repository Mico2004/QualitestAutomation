package com.automation.main.page_helpers.report.ins;

import com.automation.main.page_helpers.CustomAnalysisDropDown;
import com.automation.main.report.page.AbstractInstructorReportPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.ATUManager;
import utils.WaitDriverUtility;


public class DefaultInsReportSection extends AbstractInstructorReportPage implements CustomAnalysisDropDown {

    @FindBy(css = "option[value='0']")
    protected WebElement option;

    public DefaultInsReportSection(WebDriver browser) {
        super(browser);
    }

    @Override
    public void verifyUi() {
        try {
            customAnalysisAdminUiValidator.validateTimeAggregation(driver);
            customAnalysisAdminUiValidator.validateDropDownResultsSortedInAlphabeticalOrder(driver, searchCourseTextBox, "Course", "abc");
            customAnalysisAdminUiValidator.validateDropDownResultsSortedInAlphabeticalOrder(driver, searchInstructor, "Instructor", "instructorTe");
            customAnalysisAdminUiValidator.validateDropDownResultsSortedInAlphabeticalOrder(driver, searchRecording, "Recording", "Regular");
            customAnalysisAdminUiValidator.validateDropDownResultsSortedInAlphabeticalOrder(driver, searchViewer, "Viewer", "stu");
            customAnalysisAdminUiValidator.validateCalendar(dateFrom, driver, "data.dateFromInput");
            customAnalysisAdminUiValidator.validateCalendar(dateTill, driver, "data.dateTillInput");
            customAnalysisAdminUiValidator.validateGroupsDropDowns(groupOne);
            customAnalysisAdminUiValidator.validateGroupsDropDowns(groupTwo);
            customAnalysisAdminUiValidator.validateGroupsDropDowns(groupThree);
            customAnalysisAdminUiValidator.validateGroupsDropDowns(groupFour);
            customAnalysisAdminUiValidator.validateIncludedUsageCheckBox(inCloudedUsageLabels);
            WaitDriverUtility.verifyAllCheckBoxAreChecked(inCloudedUsageCheckBoxes);
        } catch (Exception e) {
            ATUManager.asserIsTrueAndReport(false, "Some of elements not found on " + getDropDownType() + " section" + e);
            return;
        }
        ATUManager.asserIsTrueAndReport(true, "The whole expected elements are found" + getDropDownType() + " section");
    }

    @Override
    public String getDropDownType() {
        return "Default Report";
    }

    @Override
    public void navigateToPage() {
        waitForVisibility(typeReport);
        clickElement(typeReport);
        clickElement(option);
    }


}
