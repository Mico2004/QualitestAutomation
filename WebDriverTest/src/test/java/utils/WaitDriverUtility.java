package utils;


import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitDriverUtility {

    public static WebElement waitForElementBeDisplayed(WebDriver webDriver, By by, long timeoutInSeconds) {

        System.out.println("waiting for element be displayed");
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
                        WaitDriverUtility.sleepInSeconds(1);

                        if (driver.getTitle().equals(expectedBrowserTitle)) {
                            return;
                        }
                        timeOut--;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
}
