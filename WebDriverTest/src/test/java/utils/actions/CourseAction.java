package utils.actions;

import utils.WaitDriverUtility;

import java.util.ArrayList;
import java.util.List;


public class CourseAction extends ActionsParent {

    public List<String> createCoures(String[] courses) throws InterruptedException {

        List<String> coursesBeenCreated = new ArrayList<>();

        login("Admin", true);
        admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");

        WaitDriverUtility.sleepInSeconds(3);
        System.out.println("Past4");
        manageAdhocCoursesEnrollmentsPage.waitForThePageToLoad();

        for (String courseName : courses) {
            manageAdhocCoursesEnrollmentsPage.clickOnNewCourse();
            manageAdhocCoursesEnrollmentsPage.waitForVisibility(createNewCourseWindow.course_id_input);
            courseName = courseName + System.currentTimeMillis();
            coursesBeenCreated.add(courseName);
            createNewCourseWindow.createNewCourse(courseName, courseName);
            createNewCourseWindow.clickOkInAlertIfPresent();
            manageAdhocCoursesEnrollmentsPage.exitInnerFrame();
            manageAdhocCoursesEnrollmentsPage.getIntoFrame(0);
        }
        driver.quit();
        return coursesBeenCreated;
    }

    public void enrollUsersToCourse(String courseName,List<String> uesrs) throws InterruptedException {
        login("Admin", true);
        admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
        WaitDriverUtility.sleepInSeconds(3);
        manageAdhocCoursesEnrollmentsPage.enrollInstructorToCourse(courseName, uesrs, windowmanageAdHocCoursesMembershipWindow);
        driver.quit();
    }
}
