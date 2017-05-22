package com.automation.main.parent;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeSuite;
import utils.RecordingActions;


public class BaseTest implements BasicTest {


    protected  WebDriver driver;

    @BeforeSuite
    private void beforeSuite(){
        System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

    }

    @AfterMethod
    public void tearDownTest(ITestResult testResult) {

        int status = testResult.getStatus();
        if (status == ITestResult.SUCCESS) {
            System.out.println("Done.");
            ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
        }

        if (status == ITestResult.FAILURE) {
            System.out.println("the test has failed "+testResult.getThrowable().getMessage());
            ATUReports.add("Message window." + testResult.getThrowable().getMessage(), "Done.", "Done.", LogAs.FAILED, null);
        }

        if (driver!=null){
            driver.quit();
        }

    }

    @BeforeGroups(groups = "AddRecordingToAb")
    public void addRecordingToAb(){

        RecordingActions recordingActions = new RecordingActions();
        try {
            recordingActions.addRecordingsToCourse("Ab");
        } catch (InterruptedException e) {

        }
    }

    @BeforeGroups(groups = "AddSrudentRecordingToAb")
    public void addSrudentRecordingToAb(){

        RecordingActions recordingActions = new RecordingActions();
        try {
            recordingActions.addStudentRecordingsToCourse("Ab");
        } catch (InterruptedException e) {

        }
    }


    @BeforeGroups(groups = "AddSTestRecordingToAb")
    public void addTestRecordingToAb(){

        RecordingActions recordingActions = new RecordingActions();
        try {
            recordingActions.addTestsRecordingsToCourse("Ab");
        } catch (InterruptedException e) {

        }
    }
}
