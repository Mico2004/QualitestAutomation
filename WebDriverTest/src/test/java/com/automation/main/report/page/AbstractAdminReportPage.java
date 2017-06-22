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
    public List<WebElement> groupOne;

    @FindBy(css = "select[ng-model=\"group2\"]>option")
    public List<WebElement> groupTwo;

    @FindBy(css = "select[ng-model=\"group3\"]>option")
    public List<WebElement> groupThree;

    @FindBy(css = "select[ng-model=\"group4\"]>option")
    public List<WebElement> groupFour;

    @FindBy(css = ".controls>label")
    public List<WebElement> inCloudedUsageLabels;

    @FindBy(css = ".controls>label>input")
    public List<WebElement> inCloudedUsageCheckBoxes;

    @FindBy(name = "searchCourse")
    public WebElement searchCourseTextBox;

    @FindBy(name = "searchInstructor")
    public WebElement searchInstructor;

    @FindBy(name = "searchRecording")
    public WebElement searchRecording;

    @FindBy(name = "searchDownloader")
    public WebElement searchDownloader;

    @FindBy(name = "searchViewer")
    public WebElement searchViewer;

    @FindBy(id = "typeReport")
    public WebElement typeReport;

    @FindBy(css = "option[value='0']")
    public WebElement option;

    @FindBy(css = "input[data-ng-model=\"data.dateFromInput\"]")
    public WebElement dateFrom;

    @FindBy(css = "input[data-ng-model=\"data.dateTillInput\"]")
    public WebElement dateTill;

    public CustomAnalysisAdminUiValidator customAnalysisAdminUiValidator = new CustomAnalysisAdminUiValidator();

    @FindBy(name = "searchRecording")
    public WebElement searchRecordingTextBox;

    @FindBy(css = "button[title=\"Run Report\"]")
    public WebElement runReportButton;

    @FindBy(id = "reportForm")
    public WebElement resultOfReport;

    @FindBy(css = "select[ng-model=\"$parent.time\"]")
    public WebElement aggretionTimeDropDown;


}
