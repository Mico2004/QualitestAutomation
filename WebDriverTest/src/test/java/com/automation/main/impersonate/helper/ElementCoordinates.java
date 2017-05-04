package com.automation.main.impersonate.helper;


import org.openqa.selenium.WebElement;

public class ElementCoordinates {

    private int x;
    private int y;
    private int expectedXLeftBorder;
    private int expectedXRightBorder;
    private int expectedYupBorder;
    private int expectedYDownBorder;

    public ElementCoordinates(WebElement element, int expectedXLeftBorder, int expectedXRightBorder, int expectedYupBorder, int expectedYDownBorder) {
        this.x = element.getLocation().getX();
        this.y = element.getLocation().getY();
        this.expectedXLeftBorder = expectedXLeftBorder;
        this.expectedXRightBorder = expectedXRightBorder;
        this.expectedYupBorder = expectedYupBorder;
        this.expectedYDownBorder = expectedYDownBorder;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getExpectedXLeftBorder() {
        return expectedXLeftBorder;
    }

    public int getExpectedXRightBorder() {
        return expectedXRightBorder;
    }

    public int getExpectedYupBorder() {
        return expectedYupBorder;
    }

    public int getExpectedYDownBorder() {
        return expectedYDownBorder;
    }
}
