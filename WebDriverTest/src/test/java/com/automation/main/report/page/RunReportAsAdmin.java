package com.automation.main.report.page;

import com.automation.main.report.entity.RunReportPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.WaitDriverUtility;

import java.util.List;

/**
 * Created by Lenovo on 22/06/2017.
 */
public class RunReportAsAdmin extends AbstractAdminReportPage implements RunReportPage {

    public RunReportAsAdmin(WebDriver browser) {
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

        WaitDriverUtility.waitToElementVisibility(searchCourseTextBox, driver);
        searchCourseTextBox.sendKeys(course);
        WaitDriverUtility.sleepInSeconds(3);
        WebElement elementParent = WaitDriverUtility.getElementParent(searchCourseTextBox);
        WaitDriverUtility.waitForChildElementBeDisplayed(elementParent, By.cssSelector("ul>li>a"));
        List<WebElement> searchResults = elementParent.findElements(By.cssSelector("ul>li>a"));
        searchResults.get(0).click();
    }


    public void clickAtAggertionTimeByType(ReportResultSection.AggregationTime year) {
        Select dropDown = new Select(aggretionTimeDropDown);
        dropDown.selectByValue(year.name());
    }
}
