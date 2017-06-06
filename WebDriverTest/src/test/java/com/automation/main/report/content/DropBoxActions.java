package com.automation.main.report.content;

import com.automation.main.report.entity.Course;
import com.automation.main.validator.ui.CustomAnalysisInstructorUiValidator;
import utils.creator.CourseLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lenovo on 05/06/2017.
 */
public abstract class DropBoxActions {
    
    protected String typesOfCourses;
    protected List<Course> relevantCourses;

    public abstract DropBox getPopualtedDropBox(CustomAnalysisInstructorUiValidator.DropDownType downType);


    protected DropBox getRecordingsFromAllCourses(){
        List<String> recordings = new ArrayList<>();
        for (Course singleCourse:relevantCourses) {
            recordings.addAll(extarctValidList(singleCourse.listOfRegularRecording));
            recordings.addAll(extarctValidList(singleCourse.getListOfStudentRecording()));
            recordings.addAll(extarctValidList(singleCourse.getListOfTestRecording()));
        }
        DropBox recordingsBox = new DropBoxImpl();
        recordingsBox.setBoxName("Recordings");
        recordingsBox.setListOfContent(recordings);
        return recordingsBox;
    }

    protected DropBox getAllEnrolledUsers(){
        List<String> users = new ArrayList<>();
        for (Course singleCourse:relevantCourses) {
            addUserIfNotExistInList(singleCourse.instructorA.getUserName(),users);
            addUserIfNotExistInList(singleCourse.instructorB.getUserName(),users);
            addUserIfNotExistInList(singleCourse.studentA.getUserName(),users);
            addUserIfNotExistInList(singleCourse.studentB.getUserName(),users);
        }
        DropBox recordingsBox = new DropBoxImpl();
        recordingsBox.setBoxName("Instructor");
        recordingsBox.setListOfContent(users);
        return recordingsBox;
    }

    protected DropBox getCoursesNames(){
        List<String> courses = new ArrayList<>();
        for (Course singleCourse:relevantCourses) {
            courses.add(singleCourse.courseName);
        }
        DropBox coursesBox = new DropBoxImpl();
        coursesBox.setBoxName("Courses");
        coursesBox.setListOfContent(courses);
        return coursesBox;
    }

    protected Collection<? extends String> extarctValidList(List<String> listOfRegularRecording) {
        List<String> recordings = new ArrayList<>();
        for (String reco:listOfRegularRecording) {
            if (!reco.isEmpty()){
                recordings.add(reco);
            }
        }
        return recordings;
    }

    protected void initCourses(){
        relevantCourses = new ArrayList<>();
        String[] split = typesOfCourses.split(",");
        CourseLoader courseLoader = new CourseLoader();
        for (String singleCourse:split) {
            Course courseByType = courseLoader.getCourseByType(singleCourse);
            relevantCourses.add(courseByType);
        }
    }

    protected List<String> makeValidUsersList(Course course){
        List <String> validList = new ArrayList<>();
        addToList(course.studentB.getUserName(),validList);
        addToList(course.studentA.getUserName(),validList);
        addToList(course.instructorA.getUserName(),validList);
        addToList(course.instructorB.getUserName(),validList);
        return validList;
    }

    protected void addToList(String userName, List<String> validList) {
        if (!userName.isEmpty()){
            validList.add(userName);
        }
    }

    protected void addUserIfNotExistInList(String userName,List<String>list){
        if (!list.contains(userName)&& !userName.isEmpty()){
            list.add(userName);
        }

    }

    public List<String>getAllCoursesOfSpecificIns(String ins){
        List<String>instructorCourses = new ArrayList<>();
        for (Course course :relevantCourses){
            if (course.instructorA.getUserName().startsWith(ins)){
                instructorCourses.add(course.getCourseName());
            }
        }
        return instructorCourses;
    }

    public String getCourseNameByType(String type){
        String name = null;
        for (Course course :relevantCourses ){
            if (course.courseType.equals(type)){
                name = course.getCourseName();
            }
        }
        return  name;
    }

    protected DropBox getAllInstructors() {
        List<String> users = new ArrayList<>();
        for (Course singleCourse:relevantCourses) {
            addUserIfNotExistInList(singleCourse.instructorA.getUserName(),users);
            addUserIfNotExistInList(singleCourse.instructorB.getUserName(),users);
        }
        DropBox recordingsBox = new DropBoxImpl();
        recordingsBox.setBoxName("Instructor");
        recordingsBox.setListOfContent(users);
        return recordingsBox;
    }

    protected DropBox getAllStudentRecordings() {

        List<String> recordings = new ArrayList<>();
        for (Course singleCourse:relevantCourses) {
            List<String> listOfStudentRecording = singleCourse.getListOfStudentRecording();
            if (!listOfStudentRecording.get(0).isEmpty()){
                for (String studentRecordings: listOfStudentRecording){
                    if (!studentRecordings.isEmpty()){
                        recordings.add(studentRecordings);
                    }
                }
            }
        }

        DropBox recordingsBox = new DropBoxImpl();
        recordingsBox.setBoxName("student recordings");
        recordingsBox.setListOfContent(recordings);
        return  recordingsBox;
    }


    public List<String>  getUesrsOfCourse(String type){
        List<String> users = new ArrayList<>();
        Course c1 = getCourseByType(type);
        addToList(c1.instructorA.userName,users);
        addToList(c1.instructorB.userName,users);
        addToList(c1.studentB.getUserName(),users);
        addToList(c1.studentA.getUserName(),users);
        return users;
    }

    private Course getCourseByType(String type) {
        Course course = null;
        for (Course singleCourse:relevantCourses) {
            if (singleCourse.courseType.equals(type)){
                course = singleCourse;
            }
        }
        return course;
    }

        public List<String> getInstructorsOfCurse(String type){
        Course courseByName = getCourseByType(type);
        List<String> instructors = new ArrayList<>();
        addToList(courseByName.instructorA.getUserName(),instructors);
        addToList(courseByName.instructorB.getUserName(),instructors);
        return instructors;
    }
}
