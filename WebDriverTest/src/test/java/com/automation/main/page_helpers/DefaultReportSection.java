package com.automation.main.page_helpers;

import com.automation.main.report.page.AbstractReportPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.ATUManager;
import utils.WaitDriverUtility;


public class DefaultReportSection   extends AbstractReportPage implements CustomAnalysisDropDown {

    @FindBy(css = "option[value='0']")
    protected WebElement option;

    public DefaultReportSection(WebDriver browser) {
        super(browser);
    }

    @Override
    public void verifyUi() {
        try {
            customAnalysisUiValidator.validateTimeAggregation(driver);
            customAnalysisUiValidator.validateDropDownResultsSortedInAlphabeticalOrder(driver,searchCourseTextBox,"Course","abc");
            customAnalysisUiValidator.validateDropDownResultsSortedInAlphabeticalOrder(driver,searchInstructor,"Instructor","instructorTe");
            customAnalysisUiValidator.validateDropDownResultsSortedInAlphabeticalOrder(driver,searchRecording,"Recording","Regular");
            customAnalysisUiValidator.validateDropDownResultsSortedInAlphabeticalOrder(driver,searchViewer,"Viewer","stu");
            customAnalysisUiValidator.validateCalendar(dateFrom,driver,"data.dateFromInput");
            customAnalysisUiValidator.validateCalendar(dateTill,driver,"data.dateTillInput");
            customAnalysisUiValidator.validateGroupsDropDowns(groupOne);
            customAnalysisUiValidator.validateGroupsDropDowns(groupTwo);
            customAnalysisUiValidator.validateGroupsDropDowns(groupThree);
            customAnalysisUiValidator.validateGroupsDropDowns(groupFour);
            customAnalysisUiValidator.validateIncludedUsageCheckBox(inCloudedUsageLabels);
            WaitDriverUtility.verifyAllCheckBoxAreChecked(inCloudedUsageCheckBoxes);
        } catch (Exception e) {
            ATUManager.asserIsTrueAndReport(false, "Some of elements not found on " + getDropDownType() + " section" + e);
            return;
        }
        ATUManager.asserIsTrueAndReport(true, "The whole expected elements are found"+ getDropDownType() + " section");
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
