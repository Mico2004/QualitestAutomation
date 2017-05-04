package com.automation.main.impersonate.helper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.ATUManager;


public class LocationCalculator {


    public void isElementLocatedAtExpectedLocation(ElementCoordinates elementCoordinates) {
        //verify X coordintaes between the expected range
        boolean isXinRange = verifyCoordintaesBetweenTheExpectedRange(elementCoordinates.getExpectedXLeftBorder(),
                elementCoordinates.getExpectedXRightBorder(), elementCoordinates.getX());

        ATUManager.asserIsTrueAndReport(isXinRange, "the X element should be located in expected range", "", "");

        //verify Y coordintaes between the expected range

        boolean isYInRange = verifyCoordintaesBetweenTheExpectedRange(elementCoordinates.getExpectedYupBorder(),
                elementCoordinates.getExpectedYDownBorder(), elementCoordinates.getY());
        ATUManager.asserIsTrueAndReport(isYInRange, "the Y element should be loacted in expectd range", "", "");

    }

    private boolean verifyCoordintaesBetweenTheExpectedRange(int leftBorder, int rightBorder, int elementX) {
        if (elementX > leftBorder && elementX < rightBorder) {
            return true;
        }
        return false;
    }

    public boolean isElementDisplayedToLefElement(WebElement rightElement, WebElement leftElement) {
        if (leftElement.getLocation().getX() <= rightElement.getLocation().getX()) {
            return true;
        }
        return false;
    }


    public boolean isElementUnderOnAnotherElement(WebElement underElement, WebElement upElement) {
        if (underElement.getLocation().getY() > upElement.getLocation().getY()) {
            return true;
        }
        return false;
    }

    public boolean isElementOnRightToAnotherElement(WebElement leftElement, WebElement rightElement) {
        if (rightElement.getLocation().getX() > leftElement.getLocation().getX()) {
            return true;
        }
        return false;
    }


}
