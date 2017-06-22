package com.automation.main.report;


import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import com.automation.main.parent.BaseTest;
import com.automation.main.report.entity.Course;
import com.automation.main.report.entity.RunReportPage;
import com.automation.main.report.page.ReportResultSection;
import com.automation.main.report.page.RunReportAsAdmin;
import com.automation.main.report.page.RunReportPageUiAbstractPage;
import junitx.util.PropertyManager;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.creator.CourseLoader;


@Listeners({ATUReportsListener.class, ConfigurationListener.class, MethodListener.class})
public class TC8733ValidateResultOfRunReportUI extends BaseTest {

    CourseLoader courseLoader = new CourseLoader();
    Course course;
    RunReportPage runReportPage;
    ReportResultSection reportResultSection;

    @BeforeTest
    public void setPreconditions() {

        course = courseLoader.getCourseByType("A");
    }


    @Test
    public void test() throws InterruptedException {

        reportResultSection = PageFactory.initElements(driver, ReportResultSection.class);
        String admin = PropertyManager.getProperty("Admin");
        String instructor = course.instructorA.getUserName();

        runReportPage = PageFactory.initElements(driver, RunReportAsAdmin.class);
        validateByAggregtionTimeByUser(admin);

        runReportPage = PageFactory.initElements(driver, RunReportPageUiAbstractPage.class);
        validateByAggregtionTimeByUser(instructor);

    }

    private void validateByAggregtionTimeByUser(String user) {
        login(user, false);

        customAnalysisPage.navigateToCustomAnalysis();
        runReportPage.chooseWhatToFillter("CouresD", null, null, null);

        for (ReportResultSection.AggregationTime aggregationTime : ReportResultSection.AggregationTime.values()) {
            System.out.println("Starting to validate " + aggregationTime.name());
            runReportPage.runReportAndWaitForResult(aggregationTime);
            reportResultSection.validateInputs();
            reportResultSection.validateColumnsAreDisplayedCorrectlyAfterSelectTimeAggregation(aggregationTime);
            driver.switchTo().parentFrame();
        }

        customAnalysisPage.signOut();


    }
}
