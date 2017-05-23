package utils.actions;

import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.ping.helper.LogInAsAnotherUser;

import java.util.List;

/**
 * Created by Lenovo on 23/05/2017.
 */
public class PastCourseActions extends ActionsParent {


    //compose list of past courses whiche active then moving them to past course
    public void unActivePastCourses() throws InterruptedException {
        LogInAsAnotherUser superUser = login("SuperUser", true);
        CoursesHelperPage course = superUser.course;
        List<String> pastCourses = course.getPastCourses();
        if (!pastCourses.isEmpty()){
            for (String singleCourse : pastCourses){
                course.clickOnTargetCourseName(singleCourse);
                superUser.record.clickOnCourseTaskThenMoveToPastCourses();
                driver = superUser.getDriver();
                initPageObjects();
                confirmation_menu.clickOnOkButton();
            }
            superUser.killWebDriver();
        }
    }

}
