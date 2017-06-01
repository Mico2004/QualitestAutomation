package com.automation.main.validator.ui;

import com.automation.main.page_helpers.*;
import com.automation.main.page_helpers.report.admin.*;
import com.google.common.collect.Ordering;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.ATUManager;
import utils.WaitDriverUtility;
import java.util.List;


public class CustomAnalysisAdminUiValidator extends UiValidatorParent {

    @Override
    public void validateDropDownUi(WebDriver webDriver) {

        listOfDropDownObjects.add(new ViewingAdminReportByChapterDiv(webDriver));
        listOfDropDownObjects.add(new RecordingAdminReportSection(webDriver));
        listOfDropDownObjects.add(new DownloadAdminReportSection(webDriver));
        listOfDropDownObjects.add(new QuotaAdminReportSection(webDriver));
        listOfDropDownObjects.add(new DefaultAdminReportSection(webDriver));

        for (CustomAnalysisDropDown dropDown : listOfDropDownObjects) {
            CustomAnalysisDropDown dd = PageFactory.initElements(webDriver, dropDown.getClass());
            System.out.println("Strating to validate "+dd.getDropDownType());
            dd.navigateToPage();
            dd.verifyUi();
        }
    }

    @Override
    public void validateDropDownResultsSortedInAlphabeticalOrder(WebDriver driver, WebElement element, String expectedText, String textToSearch) {
        WaitDriverUtility.waitToElementVisibility(element, driver);
        element.sendKeys(textToSearch);
        WaitDriverUtility.sleepInSeconds(3);
        WebElement elementParent = WaitDriverUtility.getElementParent(element);

        WaitDriverUtility.waitForChildElementBeDisplayed(elementParent, By.cssSelector("ul>li>a"));

        List<WebElement> searchResults = elementParent.findElements(By.cssSelector("ul>li>a"));
        List<String> resultsInText = convertListElementToListOfElementText(searchResults);
        boolean ordered = Ordering.natural().isOrdered(resultsInText);
        ATUManager.asserIsTrueAndReport(ordered, "The drop down result should be sorted in alphabetical order");
        element.clear();

    }

}
