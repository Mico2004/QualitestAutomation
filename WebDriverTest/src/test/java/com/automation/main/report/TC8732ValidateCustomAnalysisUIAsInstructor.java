package com.automation.main.report;


import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import com.automation.main.parent.BaseTest;
import com.automation.main.report.entity.Course;
import com.automation.main.validator.ui.CustomAnalysisInstructorUiValidator;
import com.automation.main.validator.ui.UiReportActions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.creator.CourseLoader;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC8732ValidateCustomAnalysisUIAsInstructor extends BaseTest {

    UiReportActions customAnalysisInsUiValidator = new CustomAnalysisInstructorUiValidator();

    Course course;

    @BeforeTest
    public void setPreconditions(){
    }


   @Test
    public void test() throws InterruptedException {
       CourseLoader courseLoader = new CourseLoader();
       course = courseLoader.getCourseByType("A");
       try {
           login(course.instructorA.userName,false);
           customAnalysisPage.navigateToCustomAnalysis();
           customAnalysisInsUiValidator.setExpectedReportTypeDropDownOption("Viewing report by chapter,Recording Report,Downloading Report");
           customAnalysisInsUiValidator.verifyingReportTypeFunctionality(customAnalysisPage.reportTypeDropDown);
           customAnalysisInsUiValidator.validateDropDownUi(driver);
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
}
