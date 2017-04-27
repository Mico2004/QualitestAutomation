package utils;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import org.junit.Assert;


public class ATUManager {

    public static void asserIsTrueAndReport(boolean isTrue, String description, String expectedValue, String actualValue) {

        try {
            Assert.assertTrue(isTrue);
            ATUReports.add(description, expectedValue, actualValue, LogAs.PASSED, null);
        } catch (AssertionError e) {
            System.out.println("The assertion description: "+description);
            ATUReports.add("Message window." + e.getMessage(), "Done.", "Done.", LogAs.FAILED, null);
        }
    }
}
