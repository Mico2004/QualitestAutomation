package com.automation.main.report;


import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import com.automation.main.parent.BaseTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners({ATUReportsListener.class, ConfigurationListener.class, MethodListener.class})
public class TC12866VerifyTheFilteringAutocompleteContentAsAdmin extends BaseTest {

    @Test
    public void test() throws InterruptedException {

        login("Admin", true);
        customAnalysisPage.navigateToCustomAnalysis();
        defaultAdminAutocompleteContentSection.verifyUi();
    }
}
