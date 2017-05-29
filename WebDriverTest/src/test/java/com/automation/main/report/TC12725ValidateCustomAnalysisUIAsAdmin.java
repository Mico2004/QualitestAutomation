package com.automation.main.report;


import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import com.automation.main.parent.BaseTest;
import com.automation.main.validator.ui.CustomAnalysisUiValidator;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC12725ValidateCustomAnalysisUIAsAdmin extends BaseTest {

    CustomAnalysisUiValidator customAnalysisUiValidator = new CustomAnalysisUiValidator();

   @Test
    public void test() throws InterruptedException {
       login("Admin",true);
       customAnalysisPage.navigateToCustomAnalysis();
       customAnalysisUiValidator.verifyingReportTypeFunctionality(customAnalysisPage.reportTypeDropDown);
       customAnalysisUiValidator.validateDropDownUi(driver);
    }
}
