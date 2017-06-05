package utils;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import org.junit.Assert;


public class ATUManager {

    {System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");}

    public static void asserIsTrueAndReport(boolean isTrue, String description, String expectedValue, String actualValue) {
        setSystemProperty();
        try {
            Assert.assertTrue(isTrue);
            ATUReports.add(description, expectedValue, actualValue, LogAs.PASSED, null);
        } catch (AssertionError e) {
            System.out.println("The assertion description: " + description);
            ATUReports.add(description + "Stacktrace " + e.getMessage(), "Done.", "Done.", LogAs.FAILED, new CaptureScreen(CaptureScreen.ScreenshotOf.BROWSER_PAGE));
        }
    }

    public static void asserIsTrueAndReport(boolean isTrue, String description) {
        setSystemProperty();
        if (isTrue) {
            ATUReports.add(description, "", "", LogAs.PASSED, null);
        } else {
            ATUReports.add(description + "Stacktrace ", "Done.", "Done.", LogAs.FAILED, new CaptureScreen(CaptureScreen.ScreenshotOf.BROWSER_PAGE));
        }
    }

    private static void setSystemProperty() {
        System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");
    }
}
