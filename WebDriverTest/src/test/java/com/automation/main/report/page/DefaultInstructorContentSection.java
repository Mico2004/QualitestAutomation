package com.automation.main.report.page;

import com.automation.main.page_helpers.CustomAnalysisDropDown;
import com.automation.main.report.content.InstructorContentValidator;
import com.automation.main.validator.ui.CustomAnalysisInstructorUiValidator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.ATUManager;


public class DefaultInstructorContentSection extends AbstractInstructorReportPage implements CustomAnalysisDropDown {

    @FindBy(css = "option[value='0']")
    protected WebElement option;
    InstructorContentValidator contentValidator = new InstructorContentValidator("A,B,C,D");
    public DefaultInstructorContentSection(WebDriver browser) {
        super(browser);
    }

    @Override
    public void verifyUi() {
        try {

            contentValidator.validateDropBox(CustomAnalysisInstructorUiValidator.DropDownType.Course,searchCourseTextBox,"ReportInsA");
            contentValidator.validateDropBox(CustomAnalysisInstructorUiValidator.DropDownType.Inst,searchInstructor);
            contentValidator.validateDropBox(CustomAnalysisInstructorUiValidator.DropDownType.Recording,searchRecordingTextBox);
            contentValidator.validateDropBox(CustomAnalysisInstructorUiValidator.DropDownType.Viewer,searchViewer);
            contentValidator.validateUserWhichDontHaveRecoAsOwner(searchInstructor,searchRecordingTextBox);
            contentValidator.resetAllFiltesr(this);
            contentValidator.validateUserOwnedRecordings(searchInstructor,searchRecordingTextBox,searchCourseTextBox);
            contentValidator.resetAllFiltesr(this);
            contentValidator.validateInstructorRecordingsDDFromAllCourses(searchInstructor,searchRecordingTextBox,searchCourseTextBox);
            contentValidator.resetAllFiltesr(this);
            contentValidator.validaterRecordingsDDFromCourseA(searchInstructor,searchRecordingTextBox,searchCourseTextBox);
            contentValidator.resetAllFiltesr(this);
            contentValidator.validateSelectedRecordingChangeTheCourseFilter(searchInstructor,searchRecordingTextBox,searchCourseTextBox);
            contentValidator.resetAllFiltesr(this);
            contentValidator.validateEnrollmentUsersToCourseD(searchViewer,searchCourseTextBox);

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
