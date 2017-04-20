package utils;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitDriverUtility {

    public static WebElement waitForElementBeDisplayed(WebDriver webDriver, By by, long timeoutInSeconds) {

        System.out.println("waiting for element be displayed");
        WebDriverWait wait = new WebDriverWait(webDriver, timeoutInSeconds);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }


    public static boolean waitAndGetElementText(WebDriver webDriver, WebElement textBox, String expectedText) {


        int timeoutInSeconds = 15;

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


}
