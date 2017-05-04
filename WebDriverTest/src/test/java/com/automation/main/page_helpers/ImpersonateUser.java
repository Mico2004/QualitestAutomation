package com.automation.main.page_helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.WaitDriverUtility;

public class ImpersonateUser extends Page {

    public ImpersonateUser(WebDriver browser) {
        super(browser);
        // TODO Auto-generated constructor stub
    }

    @FindBy(xpath = ".//*[@id='tegrityBreadcrumbsBox']/li/a")
    public WebElement AdminDashboard;
    @FindBy(id = "CustomizeHeading")
    public WebElement ImpersonateUserTitle;
    @FindBy(css = ".btn.btn-default.ng-scope")
    public WebElement impersonateButton;
    @FindBy(css = "label[for='user-field")
    public WebElement userField;
    @FindBy(css = "a[title='Impersonate user']")
    public WebElement toolTip;


    @FindBy(id = "user-field")
    public WebElement textBox;

    // the function enter the user id and press on impersonate
    public void EnterTheUserIdAndPressOnImpersonate(String user) {
        sendKeysToWebElementInput(userField, user);
        clickElementWithOutIdJS(impersonateButton);
    }

    public void enterAsAnotherUser(String userName){
        WaitDriverUtility.waitForElementBeDisplayed(driver, By.id("user-field"),10);
        textBox.sendKeys(userName);
        impersonateButton.click();

    }




}
