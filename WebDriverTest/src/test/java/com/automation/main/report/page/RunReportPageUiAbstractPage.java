package com.automation.main.report.page;

import com.automation.main.report.entity.RunReportPage;
import com.automation.main.report.runreport.RunInstructorReportValidator;
import com.automation.main.report.runreport.RunReportValidator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Created by Lenovo on 20/06/2017.
 */
public class RunReportPageUiAbstractPage extends AbstractInstructorReportPage implements RunReportPage {

    public RunReportPageUiAbstractPage(WebDriver browser) {
        super(browser);
    }


    @Override
    public void runReportAndWaitForResult(ReportResultSection.AggregationTime year) {
        clickAtAggertionTimeByType(year);
        if (runReportButton.isDisplayed()) {
            runReportButton.click();
        }
        switchToFrameById(By.id("reportFrame"));
        waitForVisibility(resultOfReport,10);
        driver.switchTo().parentFrame();
    }


    @Override
    public void chooseWhatToFillter(String course, String instructor, String recording, String viewer) {
        WebElement [] dropdwons = {searchCourseTextBox,searchInstructor,searchRecording,searchViewer};
        String[] split = (course + "," + instructor + "," + recording + "," + viewer).split(",");
        for (String value:split){
            for (WebElement singleDropdown:dropdwons){
                singleDropdown.click();
                if (!value.equals("null")){
                    Select select = new Select(singleDropdown);
                    clickAtOptionByText(select.getOptions(),value);
                }
            }
        }
        System.out.println("ss");
    }

    private void clickAtOptionByText(List<WebElement>elements,String textToSearch){
        for (WebElement element: elements){
            waitForVisibility(element);
            if (element.getText().contains(textToSearch)){
                element.click();
            }
        }
    }

    public void clickAtAggertionTimeByType(ReportResultSection.AggregationTime year) {
        Select dropDown = new Select(aggretionTimeDropDown);
        dropDown.selectByValue(year.name());
    }

}
