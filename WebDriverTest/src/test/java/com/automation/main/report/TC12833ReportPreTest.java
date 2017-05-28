package com.automation.main.report;


import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import com.automation.main.parent.BaseTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.ATUManager;
import utils.creator.ReportsDataCreator;

import java.util.List;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC12833ReportPreTest extends BaseTest {


    //create 2 instructors

    //create 2 student

    //create 4 courses

    //adding 3 regular recordings, 1 student recordings, 1 test recording.

    ReportsDataCreator reportsDataCreator = new ReportsDataCreator();
    ;
    List<String> users;

    List<String> courses;

    String courseTypes = "A,B,C,D";


    @Test
    public void test() {

//        create users
        createUsers();

//        Create courses
        createCourses();
//        List<String>split = new ArrayList<>();
//        split.add("B");

        String[] split = courseTypes.split(",");
        for (String type : split) {
            if (!type.isEmpty()) {
                System.out.println(". " + type);
                reportsDataCreator.setCourseType(type);
                enrollUsers();
                addRecordings();
                unEnrollmentUsers();
            }
        }
    }

    private void addRecordings() {
        reportsDataCreator.addRecordingsToCourse();
    }

    private void unEnrollmentUsers() {
        reportsDataCreator.unEnrollUsers();
    }

    private void enrollUsers() {
        reportsDataCreator.enrollActiveUsers();
    }

    private void createUsers() {
        reportsDataCreator.createUsers();
        users = reportsDataCreator.createdUsers;
        ATUManager.asserIsTrueAndReport(true, "2 ins and 2 stu are created successfully !", "", "");
    }

    private void createCourses() {
        reportsDataCreator.createCourses();
        courses = reportsDataCreator.createdCourses;
        ATUManager.asserIsTrueAndReport(true, "2 ins and 2 stu are created successfully !", "", "");
    }


}
