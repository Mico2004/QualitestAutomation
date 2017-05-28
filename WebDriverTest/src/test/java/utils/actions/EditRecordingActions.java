package utils.actions;

import org.openqa.selenium.WebDriver;

import java.util.List;

/**
 * Created by Lenovo on 21/05/2017.
 */
public class EditRecordingActions extends ActionsParent {


    public EditRecordingActions(WebDriver driver) {
        super(driver);
    }

    public EditRecordingActions() {
    }

    public void changeTheRecordingOwner(String courseName, List<String> recordingsNames, String loginAsUserName, RecordingType recordingType, String ownerName) throws InterruptedException {

        if (isPopulatedList(recordingsNames)){
            login(loginAsUserName, false);

            if (ownerName != null) {
                loginAsUserName = ownerName;
            }
            coursesHelperPage.selectCourseThatStartingWith(courseName);
            clickAtRecordngsType(recordingType);
            if (recordingType.equals(RecordingType.Test)) {
                String recordingName = recordingHelperPage.clickTheFirstCheckBoxRecordingsByIndex(1);
                recordingHelperPage.waitForRecordingsStatusBeDisappear(recordingName);
                changeOwner(loginAsUserName);
            } else {
                for (String recordingName : recordingsNames) {
                    if (!recordingName.isEmpty()) {
                        recordingHelperPage.waitForRecordingsStatusBeDisappear(recordingName);
                        recordingHelperPage.clickCheckBoxByName(recordingName);
                        changeOwner(loginAsUserName);
                        recordingHelperPage.waitForRecordingsStatusBeDisappear(recordingName);
                        recordingHelperPage.clickCheckBoxByName(recordingName);
                    }
                }
            }

            driver.quit();

        }


    }

    private void changeOwner(String newOwner) throws InterruptedException {
        recordingHelperPage.toEditRecordingPropertiesMenu();
        editRecordingPropertiesWindow.changeOwner(newOwner);
        editRecordingPropertiesWindow.clickOnSaveButton();
        confirmation_menu.clickOnOkButtonAfterConfirmEditRecordingProperties();
    }
}
