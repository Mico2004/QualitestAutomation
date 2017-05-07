package com.automation.main.validator.ui;

import com.automation.main.page_helpers.RecordingHelperPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.ATUManager;
import utils.WaitDriverUtility;


public class RecordingsUiValidator {

    private RecordingHelperPage record;
    private WebDriver webDriver;

    public RecordingsUiValidator(RecordingHelperPage record) {
        this.record = record;
        webDriver = record.driver;
    }


    public void validateOnHoverAndPlaySymbole() {
        validateElementSizeBeeingBigger();
        validtePlaySymboleExist();

    }

    private void validateElementSizeBeeingBigger() {
        WebElement chapter = WaitDriverUtility.waitForElementBeDisplayed(webDriver, By.xpath("//*[@class='video-outer ng-scope'][2]"), 10);
        WebElement descriptionOfChapter = chapter.findElements(By.tagName("p")).get(2);
        int beforeMouseHoverSize = descriptionOfChapter.getSize().getHeight();
        record.moveToElement(chapter, webDriver).perform();
        int afterMouseHoverSize = descriptionOfChapter.getSize().getHeight();
        boolean isGetBigger = afterMouseHoverSize > beforeMouseHoverSize;
        ATUManager.asserIsTrueAndReport(isGetBigger, "the element should be bigger when hover", "", "");
    }

    private void validtePlaySymboleExist() {
        By by = By.className("video-thumbnail");
        WaitDriverUtility.waitForElementBeDisplayed(webDriver, by, 10);
        WebElement singleChapter = webDriver.findElements(by).get(0);
        record.moveToElement(singleChapter, webDriver).perform();
        WebElement playSymbole = singleChapter.findElement(By.className("play-button"));
        ATUManager.asserIsTrueAndReport(playSymbole.isDisplayed(), "the play symbole should be  displayed to user", "", "");
    }


    private void validateCursorChangedToHand() {
        WebElement first_chapter_recording = record.first_chapter_recording;
        record.moveToElement(first_chapter_recording, webDriver).perform();
        String cursorType = WaitDriverUtility.getCursorType(first_chapter_recording);
        ATUManager.asserIsTrueAndReport(cursorType.equals("ponter"), "the cursor should be chnaged to pointer", "", "");
    }
}
