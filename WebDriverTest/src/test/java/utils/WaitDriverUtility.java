package utils;


import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class WaitDriverUtility {

    public static WebElement waitForElementBeDisplayed(WebDriver webDriver, By by, long timeoutInSeconds) {

        System.out.println("waiting for element be displayed "+by);
        WebDriverWait wait = new WebDriverWait(webDriver, timeoutInSeconds);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }


    public static boolean waitAndGetElementText(WebDriver webDriver, WebElement textBox, String expectedText) {


        int timeoutInSeconds = 20;

        while (timeoutInSeconds > 0) {
            System.out.println("trying to capture the following text: " + expectedText);
            String foundText = webDriver.findElements(By.cssSelector(".angucomplete-searching.ng-binding")).get(1).getText();
            textBox.sendKeys(Keys.ARROW_DOWN);
            if (expectedText.equals(foundText)) {
                return true;
            }
            try {
                Thread.sleep(500);
                timeoutInSeconds--;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        throw new RuntimeException("the element could not be found");
    }

    public static void performHoverOnElement(WebDriver webDriver, WebElement element) {
        Actions action = new Actions(webDriver);
        action.moveToElement(element).build().perform();
    }

    public static void waitToPageBeLoaded(WebDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, 30);

        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver wdriver) {
                return ((JavascriptExecutor) wdriver).executeScript(
                        "return document.readyState"
                ).equals("complete");
            }
        });
    }

    public static void sleepInSeconds(int timeInSeconds) {
        try {
            Thread.sleep(1000 * timeInSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static WebElement getElementParent(WebElement element) {
        return element.findElement(By.xpath(".."));
    }

    public static void waitToElementVisibility(WebElement webElement, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(
                ExpectedConditions.visibilityOf(webElement));
    }

    public static void switchToNewTab(WebDriver driver, String expectedBrowserTitle) {
        try {
            int timeOut = 10;

            sleepInSeconds(2);
            while (timeOut > 0) {
                    String currentTab = driver.getWindowHandle();
                    for (String tab : driver.getWindowHandles()) {
                        if (!tab.equals(currentTab)) {
                            driver.close();
                            driver.switchTo().window(tab);
                            System.out.println("waiting 5 sec and then switching tabs");
                            WaitDriverUtility.sleepInSeconds(5);

                            if (driver.getTitle().equals(expectedBrowserTitle)) {
                                System.out.println("Switching tabs successfully completed");
                                return;
                            }
                            timeOut--;
                        }
                    }
            }
        } catch (Exception e) {
        }
    }

    public static void switchToNewTabAndDoNotCloseOther(WebDriver driver, String expectedBrowserTitle) {
        int timeOut = 10;
        sleepInSeconds(2);
        while (timeOut > 0) {
            String currentTab = driver.getWindowHandle();
            for (String tab : driver.getWindowHandles()) {
                if (!tab.equals(currentTab)) {
                    driver.switchTo().window(tab);
                    if (driver.getTitle().equals(expectedBrowserTitle)) {
                        return;
                    }
                    WaitDriverUtility.sleepInSeconds(1);
                    timeOut--;
                }
            }
        }
    }

    public static String getCursorType(WebElement element) {
        String cursor = element.getCssValue("cursor");
        if (cursor != null) {
            return cursor;
        }
        throw new RuntimeException("Couldn't get the cursor type !");
    }

    public static void switchToMainTabAndCloseOthersTabs(WebDriver driver, String impersonateTabId) {
        for (String tab : driver.getWindowHandles()) {
            if (!tab.equals(impersonateTabId)) {
                driver.close();
            }
        }
        driver.switchTo().window(impersonateTabId);
        WaitDriverUtility.sleepInSeconds(1);
    }

    public static void waitForChildElementBeDisplayed(WebElement parent,By by){
        int timeOut = 10;

        while (timeOut > 0) {
            List<WebElement> elements = parent.findElements(by);
            if (elements.size()>0){
                return;
            }
            sleepInSeconds(1);
            timeOut--;
        }
        throw new RuntimeException("the child element does not located !" + by);

    }

    public static void verifyAllCheckBoxAreChecked(List<WebElement> checkboxes){
        for (WebElement singleCheckBox: checkboxes){
            if (!singleCheckBox.isSelected()){
                ATUManager.asserIsTrueAndReport(false,"There is checkBox that not checked");
            }
        }
        ATUManager.asserIsTrueAndReport(true,"The all checkBoxes are checked");
    }


    public static void waitForWebElementsListBePopulated(WebDriver driver,By by) {
        int timeout = 20;
        List<WebElement> elements1 = driver.findElements(by);
        while ( timeout > 0) {
            if (elements1.size()>0){
                return;
            }
            timeout--;
            sleepInSeconds(1);
        }
        throw new RuntimeException("Couldn't located the list of element " + by);

    }
}
