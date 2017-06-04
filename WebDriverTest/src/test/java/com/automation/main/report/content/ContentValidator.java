package com.automation.main.report.content;

import com.automation.main.validator.ui.CustomAnalysisInstructorUiValidator;
import org.openqa.selenium.WebElement;

/**
 * Created by Lenovo on 01/06/2017.
 */
public interface ContentValidator {

    void validateDropBox(CustomAnalysisInstructorUiValidator.DropDownType downType, WebElement element);
}
