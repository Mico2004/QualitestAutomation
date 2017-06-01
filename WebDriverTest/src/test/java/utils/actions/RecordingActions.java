package utils.actions;

import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.ping.helper.LogInAsAnotherUser;
import org.openqa.selenium.WebDriver;

import java.util.List;

/**
 * Created by Lenovo on 21/05/2017.
 */
public class RecordingActions extends ActionsParent {

    EditRecordingActions editRecordingActions;

    public RecordingActions(WebDriver driver) {
        super(driver);
    }

    public RecordingActions() {
    }

    public void addRecordingsToCourse(String courseName) throws InterruptedException {

        LogInAsAnotherUser anotherUser = new LogInAsAnotherUser();
        anotherUser.openAnotherSession("SuperUser", true);
        anotherUser.getDriver().navigate().back();
        CoursesHelperPage course = anotherUser.course;
        RecordingHelperPage record = anotherUser.record;

        courseName = course.selectCourseThatStartingWith(courseName);
        if (record.getCourseRecordingList().size() < 2) {
            anotherUser.getDriver().navigate().back();
            System.out.println("There is no recordings ! adding  2 recordings");
            course.selectCourseThatStartingWith("BankVa");
            record.clickOnRecordingsTab();
            record.clickCheckBoxByIndex(1);
            record.clickCheckBoxByIndex(2);
            System.out.println("*********** Trying to copy to " + courseName);
            record.copyRecordsToAnotherCourse(courseName);
            System.out.println("2 recordings have been copied ! ");
        }
        anotherUser.killWebDriver();


    }

    public void addStudentRecordingsToCourse(String courseName) throws InterruptedException {
        LogInAsAnotherUser anotherUser = new LogInAsAnotherUser();
        anotherUser.openAnotherSession("SuperUser", true);
        anotherUser.getDriver().navigate().back();
        CoursesHelperPage course = anotherUser.course;
        RecordingHelperPage record = anotherUser.record;

        courseName = course.selectCourseThatStartingWith(courseName);

        anotherUser.getDriver().navigate().back();
        System.out.println("There is no student recordings ! adding  2 recordings");
        course.selectCourseThatStartingWith("BankVa");
        record.clickOnStudentRecordingsTab();
        record.clickCheckBoxByIndex(1);
        record.clickCheckBoxByIndex(2);
        System.out.println("*********** Trying to copy to " + courseName);
        record.copyRecordsToAnotherCourse(courseName);
        System.out.println("2 recordings have been copied ! ");
        anotherUser.killWebDriver();
    }

    public void addTestsRecordingsToCourse(String courseName) throws InterruptedException {

        LogInAsAnotherUser anotherUser = new LogInAsAnotherUser();
        anotherUser.openAnotherSession("SuperUser", true);
        anotherUser.getDriver().navigate().back();
        CoursesHelperPage course = anotherUser.course;
        RecordingHelperPage record = anotherUser.record;

        courseName = course.selectCourseThatStartingWith(courseName);

        anotherUser.getDriver().navigate().back();
        System.out.println("There is no student recordings ! adding  2 recordings");
        course.selectCourseThatStartingWith("BankVa");
        record.clickOnTestsTab();
        record.clickCheckBoxByIndex(1);
        record.clickCheckBoxByIndex(2);
        System.out.println("*********** Trying to copy to " + courseName);
        record.copyRecordsToAnotherCourse(courseName);
        System.out.println("2 recordings have been copied ! ");

        anotherUser.killWebDriver();
    }

    public void addRegularRecordingsToCourseByNames(String courseName, List<String> recordingsNames, RecordingType recordingType) throws InterruptedException {

        if (isPopulatedList(recordingsNames)) {
            boolean thereIsRecordingToCopy = false;
            login("SuperUser", true);

            driver.navigate().back();

            coursesHelperPage.selectCourseThatStartingWith("BankVa");
            clickAtRecordngsType(recordingType);

            //if it's test recordgins the first recordings should be check
            if (recordingType.equals(RecordingType.Test)) {
                recordingHelperPage.clickTheFirstCheckBoxOfRecordingsByIndex(1);
                thereIsRecordingToCopy = true;
            } else {
                for (String recordingName : recordingsNames) {
                    editRecordingActions = new EditRecordingActions();
                    if (!recordingName.isEmpty()) {
                        recordingHelperPage.waitForRecordingsStatusBeDisappear(recordingName);
                        recordingHelperPage.clickCheckBoxByName(recordingName);
                        thereIsRecordingToCopy = true;
                    }

                }
            }

            if (thereIsRecordingToCopy) {
                System.out.println("*********** Trying to copy to " + courseName);
                recordingHelperPage.copyRecordsToAnotherCourse(courseName);
                System.out.println("recordings have been copied ! ");
            }

            driver.quit();
        }

    }

    public void deleteRecordings(String userName, boolean isUserFromProperties, String courseName, RecordingType recordingType, List<String> recordingsNames) {

        if (isPopulatedList(recordingsNames)) {
            login(userName, isUserFromProperties);
            coursesHelperPage.selectCourseThatStartingWith(courseName);
            clickAtRecordngsType(recordingType);
            for (String singleRecording : recordingsNames) {
                if (!singleRecording.isEmpty()) {
                    recordingHelperPage.clickCheckBoxByName(singleRecording);
                }
            }
            try {
                recordingHelperPage.clickOnRecordingTaskThenDelete();
                deleteMenu.clickOnDeleteButton();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.quit();
        }

    }


}
