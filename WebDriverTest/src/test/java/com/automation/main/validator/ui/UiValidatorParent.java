package com.automation.main.validator.ui;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 29/05/2017.
 */
public abstract class UiValidatorParent {

    public List<String> convertListElementToListOfElementText(List<WebElement> elements) {
        List<String> texts = new ArrayList<>();
        for (WebElement webElement : elements) {
            texts.add(webElement.getText().toLowerCase());
        }
        return texts;
    }

}
