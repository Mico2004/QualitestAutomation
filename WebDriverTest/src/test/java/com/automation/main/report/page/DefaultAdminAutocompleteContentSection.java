package com.automation.main.report.page;

import com.automation.main.page_helpers.CustomAnalysisDropDown;
import com.automation.main.report.AdminAutocompleteValidator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.ATUManager;


public class DefaultAdminAutocompleteContentSection extends AbstractAdminReportPage implements CustomAnalysisDropDown {

    @FindBy(css = "option[value='0']")
    protected WebElement option;
    AdminAutocompleteValidator contentValidator = new AdminAutocompleteValidator("A,B,C,D");
    public DefaultAdminAutocompleteContentSection(WebDriver browser) {
        super(browser);
    }

    @Override
    public void verifyUi() {
        try {
            contentValidator.setDriver(driver);
            contentValidator.validateDropBox(AdminAutocompleteValidator.TypeOfActions.Course,searchCourseTextBox,"Coures");
            contentValidator.validateDropBoxSingleResult(AdminAutocompleteValidator.TypeOfActions.SingelCourse,searchCourseTextBox);
//            validate instructor dropBox
            contentValidator.validateDropBox(AdminAutocompleteValidator.TypeOfActions.Instructors,searchInstructor,"ReportIns");
            contentValidator.validateDropBoxSingleResult(AdminAutocompleteValidator.TypeOfActions.SingleIns,searchCourseTextBox);
//            validate student recordings dropBox
            contentValidator.validateDropBox(AdminAutocompleteValidator.TypeOfActions.studentRecording,searchRecording,"Stu");
            contentValidator.validateDropBoxSingleResult(AdminAutocompleteValidator.TypeOfActions.studentRecording,searchRecording);
//            validate student recordings dropBox
            contentValidator.validateDropBox(AdminAutocompleteValidator.TypeOfActions.Instructors,searchViewer,"ReportIns");
            contentValidator.validateDropBoxSingleResult(AdminAutocompleteValidator.TypeOfActions.SingleIns,searchViewer);
            contentValidator.resetAllFiltesr(this);

            contentValidator.validateOptionIsDisplayedAfterClicked(AdminAutocompleteValidator.TypeOfActions.Course,searchCourseTextBox);
            contentValidator.verifyInstructorAreFilttirngByCourse(searchInstructor);
            contentValidator.verifyViewerAreFilttirngByCourse(searchViewer);

            contentValidator.resetAllFiltesr(this);

            contentValidator.validatUserThatDoesnotHaveAnyRecordingsAsAnOwner(this);
            contentValidator.resetAllFiltesr(this);


//            contentValidator.resetAllFiltesr(this);

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
