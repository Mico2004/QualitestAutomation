package com.automation.main.report.page;

import com.automation.main.page_helpers.Page;
import com.automation.main.validator.ui.CustomAnalysisAdminUiValidator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class AbstractAdminReportPage extends Page {

    public AbstractAdminReportPage(WebDriver browser) {
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

    @FindBy(name = "searchCourse")
    protected WebElement searchCourseTextBox;

    @FindBy(name = "searchInstructor")
    protected WebElement searchInstructor;

    @FindBy(name = "searchRecording")
    protected WebElement searchRecording;

    @FindBy(name = "searchDownloader")
    protected WebElement searchDownloader;

    @FindBy(name = "searchViewer")
    protected WebElement searchViewer;

    @FindBy(id = "typeReport")
    protected WebElement typeReport;

    @FindBy(css = "option[value='0']")
    protected WebElement option;

    @FindBy(css = "input[data-ng-model=\"data.dateFromInput\"]")
    protected WebElement dateFrom;

    @FindBy(css = "input[data-ng-model=\"data.dateTillInput\"]")
    protected WebElement dateTill;

    protected CustomAnalysisAdminUiValidator customAnalysisAdminUiValidator = new CustomAnalysisAdminUiValidator();

    @FindBy(name = "searchRecording")
    protected WebElement searchRecordingTextBox;


}
