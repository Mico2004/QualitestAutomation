package com.automation.main.report.page;

import com.automation.main.impersonate.helper.LocationCalculator;
import com.automation.main.report.runreport.AggretionTimeValidator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.ATUManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 20/06/2017.
 */
public class ReportResultSection extends AbstractInstructorReportPage {

    public ReportResultSection(WebDriver browser) {
        super(browser);
    }

    public enum AggregationTime {
        Year("Year"), Month("Month"), Week("week");
        private String val;

        AggregationTime(String value) {
            this.val = value;
        }
    }


    @FindBy(id = "MainReportViewer_ctl05_ctl00_First_ctl01_ctl00")
    protected WebElement firstPage;

    @FindBy(id = "MainReportViewer_ctl05_ctl00_Previous_ctl01_ctl00")
    protected WebElement previousPage;

    @FindBy(id = "MainReportViewer_ctl05_ctl00_CurrentPage")
    protected WebElement currentPage;


    @FindBy(id = "MainReportViewer_ctl05_ctl00_Next_ctl01_ctl00")
    protected WebElement nextPage;

    @FindBy(id = "MainReportViewer_ctl05_ctl00_Last_ctl01_ctl00")
    protected WebElement lastPage;

    //*[@id="P056410618b684358904d9b4636ab886f_1_oReportCell"]/table/tbody/tr/td/table/tbody/tr[2]/td[3]/div
    @FindBy(css = "MainReportViewer_ctl09")
    protected WebElement textData;


    public void validateInputs() {
        //The top bar should have the following elements:
        switchToFrameById(By.id("reportFrame"));
        waitForVisibility(resultOfReport, 10);

        validateElementIsDisplay(firstPage, "validate fast back button is displayed");
        validateElementIsDisplay(previousPage, "validate Back button is displayed");
        validateElementIsDisplay(nextPage, "validate Next page button is displayed ");
        validateElementIsDisplay(lastPage, "validate Last page button is displayed");
        String value = currentPage.getAttribute("value");
        ATUManager.asserIsTrueAndReport(!value.equals(""), "the number of current page result should be displayed");

        List<WebElement> elements = new ArrayList<>();
        List<WebElement> div = driver.findElements(By.tagName("div"));
        for (WebElement element : div) {
            if (element.getText().contains("Viewing")) {
                elements.add(element);
            }
            ;
        }
        System.out.println("ss");
    }

    public void validateColumnsAreDisplayedCorrectlyAfterSelectTimeAggregation(AggregationTime aggregationTimeType) {

        validateHeaderBackgroundColorIsGray();

        List<WebElement> div = driver.findElements(By.tagName("div"));

        String theTextOfHeader = validateAggretionTime(aggregationTimeType, div);
        WebElement year = findReportColumnByText(div, theTextOfHeader);
        WebElement viewingDuration = findReportColumnByText(div, "Viewing Duration");
        WebElement timesViewed = findReportColumnByText(div, "Times Viewed");
        WebElement grandTotal = findReportColumnByText(div, "Grand Total");
        LocationCalculator locationCalculator = new LocationCalculator();

        ATUManager.asserIsTrueAndReport(locationCalculator.isElementUnderOnAnotherElement(viewingDuration, year),
                " The Viewing Duration cell is displayed below to year");
        ATUManager.asserIsTrueAndReport(locationCalculator.isElementUnderOnAnotherElement(timesViewed, year),
                "The Times Viewed is displayed below to year");

        String textAlign = viewingDuration.getCssValue("text-align");
        ATUManager.asserIsTrueAndReport(textAlign.equals("center"), "View duration text cell should be align to center");

        textAlign = timesViewed.getCssValue("text-align");
        ATUManager.asserIsTrueAndReport(textAlign.equals("center"), "Times Viewed text cell should be align to center");

        boolean reportColumnByRegex = findReportColumnByRegex(div, "^(?:(?:([01]?\\d|2[0-3]):)?([0-5]?\\d):)?([0-5]?\\d)$");
        ATUManager.asserIsTrueAndReport(reportColumnByRegex, "The Total Viewing Duration value should be in the format of h:mm:ss");
        boolean timesViewedValue = findReportColumnByRegex(div, "[0-9]+");
        ATUManager.asserIsTrueAndReport(timesViewedValue, "The right bottom cell should contain an integer which describes the \"Times Viewed\", the format is \"x\" \n");

        String backgroundColor = grandTotal.getCssValue("background-color");
        ATUManager.asserIsTrueAndReport(backgroundColor.contains("rgba(0, 0, 0, 0)"), "The grand total row background color should be white");


    }

    private void validateHeaderBackgroundColorIsGray() {
        WebElement element = driver.findElement(By.cssSelector("tr[valign=\"top\"]>td:nth-child(2)"));
        String cssValue = element.getCssValue("background-color");
        ATUManager.asserIsTrueAndReport(cssValue.equals("rgba(169, 169, 169, 1)"),"validated the header background color is \"DarkGray\"");
    }

    private String validateAggretionTime(AggregationTime aggregationTime, List<WebElement> elements) {
        AggretionTimeValidator aggregationTimeManger = new AggretionTimeValidator();
        String expected = aggregationTimeManger.validateAggregationTimeByType(aggregationTime);
        WebElement textResult = findReportColumnByText(elements, expected);
        ATUManager.asserIsTrueAndReport(textResult.getText().contains(expected), "Validate a " + aggregationTime.val + " time aggregation result");
        return expected;
    }

    private void validateElementIsDisplay(WebElement element, String description) {

        boolean displayed = element.isDisplayed();
        String type = element.getAttribute("type");
        boolean image = type.equals("image");
        ATUManager.asserIsTrueAndReport(displayed && image, description);
    }

    private WebElement findReportColumnByText(List<WebElement> elements, String textToSearch) {
        List<WebElement> matches = new ArrayList<>();
        for (WebElement element : elements) {
            if (element.getText().contains(textToSearch)) {
                matches.add(element);
            }
            ;
        }
        return matches.get(matches.size() - 1);

    }

    private boolean findReportColumnByRegex(List<WebElement> elements, String textToSearch) {
        List<WebElement> matches = new ArrayList<>();
        for (WebElement element : elements) {
            if (element.getText().matches(textToSearch)) {
                matches.add(element);
            }
            ;
        }
        return matches.size() > 0;
    }

}
