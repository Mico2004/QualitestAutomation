package utils.actions;

import utils.WaitDriverUtility;

import java.util.List;

/**
 * Created by Lenovo on 23/05/2017.
 */
public class EnrollmentActions extends ActionsParent{

    public void enrollUsersAsInstructor(String commonCourseName, List<String> users) throws InterruptedException {
        loginAndNavigateToEnrollmentPage();

        manageAdhocCoursesEnrollmentsPage.enrollInstructorToCourse(commonCourseName, users, windowmanageAdHocCoursesMembershipWindow);
        driver.quit();
    }

    public void enrollUsersAsStudent(String commonCourseName, List<String> users) throws InterruptedException {
        loginAndNavigateToEnrollmentPage();
        manageAdhocCoursesEnrollmentsPage.enrollStudentsToCourse(commonCourseName, users, windowmanageAdHocCoursesMembershipWindow);
        driver.quit();
    }

    private void loginAndNavigateToEnrollmentPage(){
        login("Admin",true);
        admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
        WaitDriverUtility.sleepInSeconds(3);
    }

    public void unEnrollIns(String courseName,List<String> users) throws InterruptedException {

        loginAndNavigateToEnrollmentPage();
        driver.switchTo().frame(0);
        for (String user : users){
            manageAdhocCoursesEnrollmentsPage.waitForVisibility(manageAdhocCoursesEnrollmentsPage.first_course_name);
            manageAdhocCoursesEnrollmentsPage.unEnrollInstructorToCourse(courseName, user, windowmanageAdHocCoursesMembershipWindow);
            manageAdhocCoursesEnrollmentsPage.waitForAlert(10);
            manageAdhocCoursesEnrollmentsPage.clickOkInAlertIfPresent();
        }
        driver.quit();
    }

    public void unEnrollStudents(String targetCourse, List<String> students) throws InterruptedException {
        loginAndNavigateToEnrollmentPage();
//        driver.switchTo().frame(0);
        for (String user : students){
//            manageAdhocCoursesEnrollmentsPage.waitForVisibility(manageAdhocCoursesEnrollmentsPage.first_course_name);
            manageAdhocCoursesEnrollmentsPage.unEnrollStusentsFromCourse(targetCourse, user, windowmanageAdHocCoursesMembershipWindow);
            manageAdhocCoursesEnrollmentsPage.waitForAlert(10);
            manageAdhocCoursesEnrollmentsPage.clickOkInAlertIfPresent();
            manageAdhocCoursesEnrollmentsPage.exitInnerFrame();
        }
        driver.quit();
    }
}
