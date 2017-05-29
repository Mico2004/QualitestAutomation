package com.automation.main.page_helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by Lenovo on 29/05/2017.
 */
public class CustomAnalysisPage  extends Page{

    @FindBy(css = "#typeReport>option")
    public List<WebElement> reportTypeDropDown;

    @FindBy(id = "tegrityTopNavigationBar")
    WebElement top_bar;

    @FindBy(id = "CustomAnalysis")
    WebElement customAnalysisOption;

    @FindBy(id = "ReportsLink")
    WebElement reportsLink;

    @FindBy(className = "customAnalysis")
    WebElement mainDiv;


    public CustomAnalysisPage(WebDriver browser) {
        super(browser);
    }

    public void navigateToCustomAnalysis(){
        waitForVisibility(top_bar);
        moveToElement(reportsLink ,driver).perform();
        waitForVisibility(customAnalysisOption);
        customAnalysisOption.click();
        waitForVisibility(mainDiv);
    }
}
