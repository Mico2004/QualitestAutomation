package com.automation.main.report.content;

import com.automation.main.report.entity.Course;
import com.automation.main.validator.ui.CustomAnalysisInstructorUiValidator;
import utils.creator.CourseLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class InstructorDropBoxBuilder {

    public InstructorDropBoxBuilder(String typesOfCourses) {
        this.typesOfCourses = typesOfCourses;
        initCourses();
    }

    private String typesOfCourses;
    private List<Course>relevantCourses;

        public DropBox getPopualtedDropBox(CustomAnalysisInstructorUiValidator.DropDownType downType){

            DropBox dropBox = null;

            switch (downType){

                case Recording:
                    dropBox = getRecordingsFromAllCourses();
                    break;
                case Course:
                    dropBox = getCoursesNames();
                    break;
                case Inst:
                    dropBox = getAllEnrolledUsers();
                    break;
                case Viewer:
                    dropBox = getAllEnrolledUsers();
                    break;
            }
            return dropBox;
        }

        private DropBox getRecordingsFromAllCourses(){
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

    private DropBox getAllEnrolledUsers(){
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

    private DropBox getCoursesNames(){
        List<String> courses = new ArrayList<>();
        for (Course singleCourse:relevantCourses) {
            courses.add(singleCourse.courseName);
        }
        DropBox coursesBox = new DropBoxImpl();
        coursesBox.setBoxName("Courses");
        coursesBox.setListOfContent(courses);
        return coursesBox;
    }

    private Collection<? extends String> extarctValidList(List<String> listOfRegularRecording) {
        List<String> recordings = new ArrayList<>();
        for (String reco:listOfRegularRecording) {
            if (!reco.isEmpty()){
                recordings.add(reco);
            }
        }
        return recordings;
    }

    private void initCourses(){
            relevantCourses = new ArrayList<>();
            String[] split = typesOfCourses.split(",");
            CourseLoader courseLoader = new CourseLoader();
            for (String singleCourse:split) {
                Course courseByType = courseLoader.getCourseByType(singleCourse);
                relevantCourses.add(courseByType);
            }
    }

    private List<String> makeValidUsersList(Course course){
        List <String> validList = new ArrayList<>();
        addToList(course.studentB.getUserName(),validList);
        addToList(course.studentA.getUserName(),validList);
        addToList(course.instructorA.getUserName(),validList);
        addToList(course.instructorB.getUserName(),validList);
        return validList;
    }

    private void addToList(String userName, List<String> validList) {
        if (!userName.isEmpty()){
            validList.add(userName);
        }
    }

    private void addUserIfNotExistInList(String userName,List<String>list){
        if (!list.contains(userName)){
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
}
