package com.automation.main.report;


import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import com.automation.main.parent.BaseTest;
import com.automation.main.report.entity.Course;
import com.automation.main.report.page.DefaultInstructorContentSection;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.creator.CourseLoader;


@Listeners({ATUReportsListener.class, ConfigurationListener.class, MethodListener.class})
public class TC8739VerifyTheFilteringDropdownContentAsInstructor extends BaseTest {

    CourseLoader courseLoader = new CourseLoader();
    Course course;

    @BeforeTest
    public void setPreconditions() {
        //Storing pre-conditions data
        course = courseLoader.getCourseByType("A");
    }


    @Test
    public void test() throws InterruptedException {

        login(course.instructorA.userName, false);
        customAnalysisPage.navigateToCustomAnalysis();
        defaultInstructorContentSection = PageFactory.initElements(driver, DefaultInstructorContentSection.class);
        defaultInstructorContentSection.verifyUi();

    }
}
