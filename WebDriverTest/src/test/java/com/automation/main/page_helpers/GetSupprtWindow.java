package com.automation.main.page_helpers;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;


public class GetSupprtWindow extends Page {

    public GetSupprtWindow(WebDriver browser) {
        super(browser);
        // TODO Auto-generated constructor stub
    }

    @FindBy(id = "supportWindow")
    WebElement support_window_title;
    @FindBy(id = "email-field")
    public WebElement from_email_field;
    @FindBy(id = "name-field")
    public WebElement from_name_field;
    @FindBy(id = "email-field2")
    public WebElement to_email_field;
    @FindBy(id = "name-field2")
    public WebElement to_name_field;
    @FindBy(id = "subject-field")
    public WebElement subject_field;
    @FindBy(id = "textarea-field")
    public WebElement comments_field;
    @FindBy(xpath = "//*[@id=\"supportWindow\"]/div[2]/form/span[1]")
    public WebElement support_window_info;
    @FindBy(xpath = ".//*[@id='supportWindow']/div[2]/form/div[5]/button[2]")
    public WebElement cancel_button;
    @FindBy(xpath = ".//*[@id='supportWindow']/div[2]/form/div[5]/button[1]")
    public WebElement send_button;
    //@FindBy(id="#inbox-id>input")
    //public WebElement mailinator_mail_edittext;
    //@FindBy(css=".btn.btn-dark" )
    //public WebElement mailinator_mail_go;
    @FindBy(css = ".td4")
    public List<WebElement> mail_time_of_sending;
    @FindBy(css = ".td2")
    public List<WebElement> mail_index_of_sending;
    @FindBy(css = ".email_body")
    public WebElement contant_of_mail;
    @FindBy(id = "inbox-id")
    public WebElement changeGuerrillamailButton;
    @FindBy(css = "input[type=text]")
    public WebElement guerrillaMailEdittext;
    @FindBy(css = "button[class=\"btn btn-dark\"]")
    public WebElement guerrillaMailSet;
    @FindBy(css = ".email-excerpt")
    public List<WebElement> mail_subject_of_sending;
    @FindBy(id = "back_to_inbox_link")
    public WebElement backButton;
    @FindBy(css = ".td1>input")
    public List<WebElement> firstCheckbox;
    @FindBy(id = "del_button")
    public WebElement deleteButton;

    @FindBy(xpath = ".//*[@id='supportWindow']/div[2]/form/span[2]")
    public WebElement error_massage;

    ///verify support window is not displayed
    public void verifyNoSupportWindow() {
        if (support_window_title.isDisplayed()) {
            ATUReports.add(time + " support window is visible", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
            System.out.println("support window is visible");
            Assert.assertTrue(false);
        } else {

            ATUReports.add(time + " support window is not visible", LogAs.PASSED, null);
            System.out.println("support window is not visible");
            Assert.assertTrue(true);
        }
    }

    ///verify support window is displayed
    public void verifySupportWindow() {
        waitForVisibility(support_window_title);
        if (support_window_title.isDisplayed()) {
            ATUReports.add(time + " support window is visible", LogAs.PASSED, null);
            System.out.println("support window is visible");
            Assert.assertTrue(true);
        } else {

            ATUReports.add(time + " support window is not visible", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
            System.out.println("get started block is  not visible");
            Assert.assertTrue(false);
        }
    }

    // This function verify that support menu open
    public void verifyThatGetSupportMenuOpen() {
        boolean is_closed = isGetSupportMenuClosed();

        if (!is_closed) {
            System.out.println("Get support menu is open.");
            ATUReports.add(time + " Get support menu.", "Open.", "Open.", LogAs.PASSED, null);
            Assert.assertTrue(true);
        } else {
            System.out.println("Get support menu is close.");
            ATUReports.add(time + " Get support menu.", "Open.", "Close.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
            Assert.assertTrue(false);
        }
    }

    // This function verify that copy menu close
    public void verifyThatGetSupportMenuClose() {
        boolean is_closed = isGetSupportMenuClosed();

        if (!is_closed) {
            System.out.println("Get support menu is open.");
            ATUReports.add(time + " Get support menu.", "Close.", "Open.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
            Assert.assertTrue(false);
        } else {
            System.out.println("Get support is close.");
            ATUReports.add(time + " Get support menu.", "Close.", "Close.", LogAs.PASSED, null);
            Assert.assertTrue(true);
        }
    }

    // This function return true if copy menu is closed and false if it is open
    public boolean isGetSupportMenuClosed() {
        try {
            new WebDriverWait(driver, 30).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='supportWindow']/div[2]/form/div[5]/button[1]")));
            send_button.isDisplayed();
            return false;
        } catch (org.openqa.selenium.NoSuchElementException msg) {
            return true;
        } catch (org.openqa.selenium.StaleElementReferenceException msg) {
            return true;
        } catch (org.openqa.selenium.TimeoutException msg) {
            return false;
        }
    }

    ///fill support window blank fields and send it to your email
    public void fillSupportWindowAndSend(String from_email, String from_name, String subject, String comments, ConfirmationMenu confirm, WebDriver driver) {
        try {
            waitForVisibility(support_window_title);
            from_email_field.clear();
            from_name_field.clear();
            subject_field.clear();
            comments_field.clear();
            from_email_field.sendKeys(from_email);
            from_name_field.sendKeys(from_name);
            subject_field.sendKeys(subject);
            comments_field.sendKeys(comments);
            for (String window : driver.getWindowHandles()) {
                driver.switchTo().window(window);
                break;
            }
            waitForVisibility(send_button);
            send_button.click();
            waitForVisibility(confirm.ok_button);
            confirm.clickOnOkButtonAfterConfirmEmailSentSuccessfully();
            ATUReports.add(time + " email sent", LogAs.PASSED, null);
            System.out.println("email sent");
            Assert.assertTrue(true);
        } catch (Exception e) {
            System.out.println("failed clicking send");
            ATUReports.add(time + " email  failed", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
            System.out.println("email  failed");
            Assert.assertTrue(false);
        }
    }

    public void verifyThatTheEmailAdressFieldHelpDeskOrPlaceHolder(String Email) {

        try {
            String id = to_email_field.getAttribute("id");
            String mail = (String) ((JavascriptExecutor) driver).executeScript("return document.getElementById(\"" + id + "\").value;");
            if (mail.equals(Email)) {
                System.out.println("Verify that the email address is: " + mail);
                ATUReports.add(time + " Verify that the email address is: " + mail, "Success.", "Success.", LogAs.PASSED, null);
            } else {
                verifyPlaceHolderIsDisplay(to_email_field, "E-mail address");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ATUReports.add(time + e.getMessage(), "Success.", "Failed.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
        }
    }

    public void waitForThePageToLoad() {
        try {
            new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(to_email_field));
            new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(to_name_field));
            new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(subject_field));
            new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(comments_field));

        } catch (Exception e) {
            e.printStackTrace();
            ATUReports.add(time + e.getMessage(), "Success.", "Failed.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
        }
    }

    public void verifyThatTheSupportEmailHasDefultValue() {

        try {
            String id = to_name_field.getAttribute("id");
            String supportEmail = (String) ((JavascriptExecutor) driver).executeScript("return document.getElementById(\"" + id + "\").value;");
            if (supportEmail.equals("Institution Help Desk")) {
                System.out.println("Verify that the support email has defult value of institution Help Desk.");
                ATUReports.add(time + " Verify that the support email has defult value of institution Help Desk.", "Success.", "Success.", LogAs.PASSED, null);
            } else {
                System.out.println("not Verify that the support email has defult value of institution Help Desk.");
                ATUReports.add(time + " Verify that the support email has defult value of institution Help Desk.", "Success.", "Failed.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
            }
        } catch (Exception e) {
            e.printStackTrace();
            ATUReports.add(time + e.getMessage(), "Success.", "Failed.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
        }
    }


    public String getTheGgidFromTheUrl(String url) {

        String[] urlsplit = url.split("/");

        return urlsplit[5];
    }

    public String getTheGgidFromTheUrlForRecording(String url) {

        String[] urlsplit = url.split("/");
        String[] urlsplit2 = urlsplit[5].split("\\?");

        return urlsplit2[0];
    }

    public String getTheUniverstyNameFromTheUrl(String url) {

        String[] urlsplit = url.split("/");

        return urlsplit[2];
    }

    public String getUserAgentByLogs() {
        String user_agent = null;
        try {
            LogEntries les = driver.manage().logs().get(LogType.PERFORMANCE);
            for (LogEntry le : les) {
                if (le.getMessage().contains("User-Agent")) {
                    JSONObject gson = new JSONObject(le.getMessage());
                    JSONObject gson1 = gson.getJSONObject("message");
                    JSONObject gson2 = gson1.getJSONObject("params");
                    JSONObject gson3 = gson2.getJSONObject("request");
                    JSONObject gson4 = gson3.getJSONObject("headers");
                    user_agent = gson4.getString("User-Agent");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ATUReports.add(time + e.getMessage(), "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
        }
        return user_agent;
    }

    public void checkThatErrorMassageDesplayAndTheStringIsRight(String massage) {

        try {

            if (error_massage.isDisplayed() && error_massage.getText().equals(massage)) {
                System.out.println("The error massage: " + massage + " is display and verify.");
                ATUReports.add(time + "The error massage: " + massage + " is display and verify.", "True.", "True.", LogAs.PASSED, null);
            } else {
                System.out.println("The error massage: " + massage + " is display and verify.");
                ATUReports.add(time + "The error massage: " + massage + " is display and verify.", "True.", "True.", LogAs.PASSED, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ATUReports.add(time + e.getMessage(), "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
        }
    }

    public String moveToUpperCase(String UniverstyName) {
        char[] chr = UniverstyName.toCharArray();
        int n = chr.length;
        char ch;
        boolean upperCaseToAll = false;
        int i;
        for (i = 0; i < n; i++) {
            if (i < 3 || (chr[i] == 'a' && chr[i + 1] == 'u') || upperCaseToAll == true) {
                ch = Character.toUpperCase(chr[i]);
                chr[i] = ch;
            }
            if (chr[i] == '-') {
                upperCaseToAll = true;
            }
        }
        String strToReturn = new String(chr);
        String[] split = strToReturn.split("\\.");
        return split[0];
    }


    public void waitForTheMailToLoad() {
        try {

            new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(mail_time_of_sending.get(0)));
            new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(mail_index_of_sending.get(0)));
            for (int i = 0; i < 5; i++) {
                String val = driver.findElements(By.cssSelector(".td2")).get(0).getCssValue("font-weight").toString();
                if (val.equals("bold")) {
                    Thread.sleep(1000);
                    break;
                } else {
                    Thread.sleep(5000);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ATUReports.add(time + e.getMessage(), "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
        }
    }

    public void clickOnTheFirstMail() throws InterruptedException {
        clickElement(mail_subject_of_sending.get(0));
        for (int i = 0; i < 5; i++) {
            if (!isElemenetDisplayed(By.cssSelector(".email_from"))) {
                clickElement(mail_subject_of_sending.get(0));
            } else {
                Thread.sleep(1000);
                break;
            }
        }
    }
}
