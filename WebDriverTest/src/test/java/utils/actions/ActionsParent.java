package utils.actions;

import com.automation.main.page_helpers.*;
import com.automation.main.ping.helper.LogInAsAnotherUser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by Lenovo on 21/05/2017
 */

public abstract class ActionsParent {

    protected WebDriver driver;
    protected LoginHelperPage tegrity;
    protected AdminDashboardPage admin_dashboard_page;
    protected WebDriverWait wait;
    protected ManageAdhocCoursesEnrollmentsPage mange_adhoc_course_enrollments;
    protected ConfirmationMenu confirmation_menu;
    protected CreateNewUserWindow createNewUserWindow;
    protected ManageAdhocUsersPage manageAdhocUsersPage;
    protected ManageAdhocCoursesEnrollmentsPage manageAdhocCoursesEnrollmentsPage;
    protected CreateNewCourseWindow createNewCourseWindow;
    protected ManageAdHocCoursesMembershipWindow windowmanageAdHocCoursesMembershipWindow;
    protected RecordingHelperPage recordingHelperPage;
    protected CoursesHelperPage coursesHelperPage;
    protected EditRecordingPropertiesWindow editRecordingPropertiesWindow;
    protected AdvancedServiceSettingsPage advancedServiceSettingsPage;
    protected DeleteMenu deleteMenu;
    protected PlayerPage playerPage;


    public enum RecordingType {
        Regular("regular"), Test("test"), student("student"), additionalContent("additional content");
        private String val;

        RecordingType(String value) {
            this.val = value;
        }
    }

    public ActionsParent(WebDriver driver) {
        this.driver = driver;
        initPageObjects();
    }

    public ActionsParent() {

    }

    protected void initPageObjects() {

        tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
        admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);
        mange_adhoc_course_enrollments = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);
        confirmation_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
        createNewUserWindow = PageFactory.initElements(driver, CreateNewUserWindow.class);
        manageAdhocUsersPage = PageFactory.initElements(driver, ManageAdhocUsersPage.class);
        manageAdhocCoursesEnrollmentsPage = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);
        createNewCourseWindow = PageFactory.initElements(driver, CreateNewCourseWindow.class);
        windowmanageAdHocCoursesMembershipWindow = PageFactory.initElements(driver, ManageAdHocCoursesMembershipWindow.class);
        recordingHelperPage = PageFactory.initElements(driver, RecordingHelperPage.class);
        coursesHelperPage = PageFactory.initElements(driver, CoursesHelperPage.class);
        editRecordingPropertiesWindow = PageFactory.initElements(driver, EditRecordingPropertiesWindow.class);
        advancedServiceSettingsPage = PageFactory.initElements(driver, AdvancedServiceSettingsPage.class);
        deleteMenu = PageFactory.initElements(driver, DeleteMenu.class);
        playerPage = PageFactory.initElements(driver, PlayerPage.class);
    }

    protected LogInAsAnotherUser login(String userName, boolean thisUserFromPropFile) {
        LogInAsAnotherUser anotherUser = new LogInAsAnotherUser();
        anotherUser.openAnotherSession(userName, thisUserFromPropFile);
        driver = anotherUser.getDriver();
        initPageObjects();
        anotherUser.getDriver().navigate().back();
        return anotherUser;
    }


    protected void clickAtRecordngsType(RecordingType recordingType) {

        switch (recordingType) {
            case Regular:
                if (recordingHelperPage.recordings_tab.isDisplayed()){
                    recordingHelperPage.clickOnRecordingsTab();
                }

                break;
            case Test:
                if (recordingHelperPage.test_tab.isDisplayed()){
                    recordingHelperPage.clickOnTestsTab();
                }

                break;
            case student:
                if (recordingHelperPage.student_recordings_tab.isDisplayed()){
                    recordingHelperPage.clickOnStudentRecordingsTab();
                }

                break;
            case additionalContent:
                if (recordingHelperPage.additional_content_tab.isDisplayed()){
                    {
                        recordingHelperPage.clickOnAdditionContentTab();
                    }
                }

        }
    }

    boolean isPopulatedList(List<String> listToCheck) {

        if (listToCheck != null) {
            for (String value : listToCheck) {
                if (!value.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }
}
