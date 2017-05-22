package utils;

import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.ping.helper.LogInAsAnotherUser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by Lenovo on 21/05/2017.
 */
public class RecordingActions extends ActionsParent {


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


}
