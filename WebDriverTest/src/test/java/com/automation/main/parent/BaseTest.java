package com.automation.main.parent;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;


public class BaseTest implements BasicTest {


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

    }
}
