package utils.creator;


import com.automation.main.ping.helper.LogInAsAnotherUser;
import com.automation.main.report.entity.CourseViewManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import junitx.util.PropertyManager;
import utils.ATUManager;
import utils.actions.ActionsParent;
import utils.creator.json.ConvertCourseToJson;
import utils.creator.json.CourseTypeTemplate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportsDataCreator extends DataCreator {

    private String users = "ReportInsA,ReportInsB,ReportStudA,ReportStudB";
    private String courses = "CouresA,CouresB,CouresC,CouresD";
    String pathToJson = "\\src\\test\\resources\\json\\reports\\Course";

    public List<String> createdUsers;
    public List<String> createdCourses;
    private long currentDate;
    String courseType ;


    public ReportsDataCreator() {

        Date current = new Date();
        currentDate = current.getTime();
    }


    public void createUsers() {
        usersActions.disableEulaPage();
        String[] splited = users.split(",");
        try {
            createdUsers = usersActions.createNewUser(splited);
            if (createdUsers.size() < 3) {
                throw new RuntimeException("It's should be created 4 users !");
            }
            activatedUsers();

        } catch (Exception e) {
            ATUManager.asserIsTrueAndReport(false, "Fails to create users for testing report", "", "");
        }
    }

    public void createCourses() {

        String[] splited = courses.split(",");
        try {
            createdCourses = courseAction.createCoures(splited);

            if (createdCourses.size() < 3) {
                throw new RuntimeException("It's should be created 4 courses !");
            }

        } catch (Exception e) {
            ATUManager.asserIsTrueAndReport(false, "Fails to create courses for testing report " + e, "", "");
        }

    }

    public void activatedUsers(){
        for (String user:createdUsers) {
            LogInAsAnotherUser logInAsAnotherUser = new LogInAsAnotherUser();
            logInAsAnotherUser.openAnotherSession(user,false);
            logInAsAnotherUser.killWebDriver();
        }

    }

    public void enrollActiveUsers() {
        String courseToEnroll = getFullCourseName("Coures" + courseType);
        try {
            CourseTypeTemplate course = getCourseTypeTemplate(courseType);
            String superUser = PropertyManager.getProperty("SuperUser");
            List<String> insNames = getFullNameUsers(course.instructors);
            insNames.add(superUser);
            enrollmentManager.enrollUsersAsInstructor(courseToEnroll, insNames);

            List<String> students = getFullNameUsers(course.students);
            enrollmentManager.enrollUsersAsStudent(courseToEnroll, students);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void unEnrollUsers()  {


//        String courseType = "D";
        CourseTypeTemplate courseTypeTemplate = getCourseTypeTemplate(courseType);
        if (courseTypeTemplate.isPastCourse.equals("true")){
            String targetCourse = getFullCourseName("Coures" + courseType);
            List<String> ins = getFullNameUsers(courseTypeTemplate.instructors);
            List<String> students = getFullNameUsers(courseTypeTemplate.students);
            try {
                enrollmentManager.unEnrollIns(targetCourse,ins);
                enrollmentManager.unEnrollStudents(targetCourse,students);
            } catch (InterruptedException e) {
                ATUManager.asserIsTrueAndReport(false,"fails to unEnroll users","","");
            }
        }
    }

    public void addRecordingsToCourse() {
        String targetCourse = getFullCourseName("Coures" + courseType);
        CourseTypeTemplate courseTypeTemplate = getCourseTypeTemplate(courseType);
        String insOfCourse = getFullSigngleUser(courseTypeTemplate.mainInstructor);
        String userUplouder = getFullSigngleUser("ReportInsA");
        usersActions.disableEulaPage();
        try {
             recordingActions.addRegularRecordingsToCourseByNames(targetCourse, courseTypeTemplate.uploudRecByInsA,ActionsParent.RecordingType.Regular);
////
            editRecordingActions.changeTheRecordingOwner(targetCourse,courseTypeTemplate.uploudRecByInsA,userUplouder, ActionsParent.RecordingType.Regular,null);


            userUplouder = getFullSigngleUser("ReportInsB");
////
            recordingActions.addRegularRecordingsToCourseByNames(targetCourse, courseTypeTemplate.uploudRecByInsB,ActionsParent.RecordingType.Regular);
////
            editRecordingActions.changeTheRecordingOwner(targetCourse,courseTypeTemplate.uploudRecByInsB,userUplouder, ActionsParent.RecordingType.Regular,null);
            activatedStudents();

////
//            //student
           userUplouder = getFullSigngleUser("ReportStudA");
            recordingActions.addRegularRecordingsToCourseByNames(targetCourse, courseTypeTemplate.uploudRecByStuA,ActionsParent.RecordingType.student);
            editRecordingActions.changeTheRecordingOwner(targetCourse,courseTypeTemplate.uploudRecByStuA,insOfCourse, ActionsParent.RecordingType.student,userUplouder);
//
//
            recordingActions.addRegularRecordingsToCourseByNames(targetCourse, courseTypeTemplate.uploudTestRecByStuA,ActionsParent.RecordingType.Test);
            editRecordingActions.changeTheRecordingOwner(targetCourse,courseTypeTemplate.uploudTestRecByStuA,insOfCourse, ActionsParent.RecordingType.Test,userUplouder);

            watchRecordingsAsStudents();

//            delete the required recordings
            recordingActions.deleteRecordings(insOfCourse,false,targetCourse, ActionsParent.RecordingType.Regular,courseTypeTemplate.recordingsToDelete);

            ConvertCourseToJson convertCourseToJson = new ConvertCourseToJson();
            convertCourseToJson.convertToJson(createdUsers,targetCourse,courseTypeTemplate);

        } catch (Exception e) {
            e.printStackTrace();
            ATUManager.asserIsTrueAndReport(false, "Fails to copy recordings", "", "");

        }
    }

    private void activatedStudents() {
        for (String user:createdUsers) {
            if (user.startsWith("ReportStud")){
                usersActions.activatedStudent(user);
            }
        }
    }

    private CourseTypeTemplate getCourseTypeTemplate(String courseType) {
        ObjectMapper objectMapper = new ObjectMapper();
        CourseTypeTemplate courseTypeTemplate = null;
        try {
            courseTypeTemplate = objectMapper.readValue(new File(System.getProperty("user.dir") + pathToJson + courseType + ".json"), CourseTypeTemplate.class);
        } catch (IOException e) {
            ATUManager.asserIsTrueAndReport(false, "couldn't deserialize the json file with type " + courseType, "", "");
        }
        return courseTypeTemplate;
    }

    private String getFullCourseName(String courseToSearch) {
        for (String course : createdCourses) {
            if (course.startsWith(courseToSearch)) {
                return course;
            }
        }
        throw new RuntimeException("the required course does not found");
    }

    private List<String> getFullNameUsers(List<String> prefixUsers) {
        List<String> users = new ArrayList<>();
        for (String userPrefix : prefixUsers) {
            for (String createdUser : createdUsers) {
                if (createdUser.startsWith(userPrefix)) {
                    users.add(createdUser);
                }
            }
        }
        return users;
    }

    private String getFullSigngleUser(String prefixUsers) {
            for (String createdUser : createdUsers) {
                if (createdUser.startsWith(prefixUsers)) {
                    return createdUser;
                }
        }
        throw  new RuntimeException("The user does not found");
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public void watchRecordingsAsStudents(){
        CourseTypeTemplate course = getCourseTypeTemplate(courseType);
        CourseViewManager courseViewManager = course.courseViewManager;
        String fullCourseName = getFullCourseName("Coures" + courseType);
        //instructors
        boolean validList = isValidList(courseViewManager.getInstructorViewer());
        if (validList){
            for (String insViewer:courseViewManager.getInstructorViewer()){
                if (!insViewer.isEmpty()){
                    String fullSigngleUser = getFullSigngleUser(insViewer);

                    recordingActions.watchRecording(fullSigngleUser,courseViewManager.getInstructorRecordings(),fullCourseName);
                }
            }
        }

        validList = isValidList(courseViewManager.getStudentViewers());
        if (validList){
            for (String stuViewer:courseViewManager.getStudentViewers()){
                if (!stuViewer.isEmpty()){
                    String fullSigngleUser = getFullSigngleUser(stuViewer);
                    recordingActions.watchRecording(fullSigngleUser,courseViewManager.getStudentRecordings(),fullCourseName);
                }
            }
        }
    }

    private boolean isValidList(List<String>list){
        if (list!=null&&list.size()>0){
            return true;
        }
        return false;
    }

}
