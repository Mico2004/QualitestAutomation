package com.automation.main.report.page;

import com.automation.main.page_helpers.Page;
import com.automation.main.validator.ui.CustomAnalysisInstructorUiValidator;
import com.automation.main.validator.ui.UiReportActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class AbstractInstructorReportPage extends Page {

    public AbstractInstructorReportPage(WebDriver browser) {
        super(browser);
    }

    @FindBy(css = "select[ng-model=\"group1\"]>option")
    protected List<WebElement> groupOne;

    @FindBy(css = "select[ng-model=\"group2\"]>option")
    protected List<WebElement> groupTwo;

    @FindBy(css = "select[ng-model=\"group3\"]>option")
    protected List<WebElement> groupThree;

    @FindBy(css = "select[ng-model=\"group4\"]>option")
    protected List<WebElement> groupFour;

    @FindBy(css = ".controls>label")
    protected List<WebElement> inCloudedUsageLabels;

    @FindBy(css = ".controls>label>input")
    protected List<WebElement> inCloudedUsageCheckBoxes;

    @FindBy(css = "select[ng-model=\"selected.Course\"]")
    public WebElement searchCourseTextBox;

    @FindBy(css = "select[ng-model=\"selected.Instructor\"]")
    public WebElement searchInstructor;

    @FindBy(css = "select[ng-model=\"selected.Recording\"]")
    public WebElement searchRecording;

    @FindBy(css = "select[ng-model=\"selected.Downloader\"]")
    public WebElement searchDownloader;

    @FindBy(css = "select[ng-model=\"selected.Viewer\"]")
    public WebElement searchViewer;

    @FindBy(id = "typeReport")
    protected WebElement typeReport;

    @FindBy(css = "option[value='0']")
    protected WebElement option;

    @FindBy(css = "input[data-ng-model=\"data.dateFromInput\"]")
    protected WebElement dateFrom;

    @FindBy(css = "input[data-ng-model=\"data.dateTillInput\"]")
    protected WebElement dateTill;

    protected UiReportActions customAnalysisAdminUiValidator = new CustomAnalysisInstructorUiValidator();

    @FindBy(css = "select[ng-model=\"selected.Recording\"]")
    public WebElement searchRecordingTextBox;


}
