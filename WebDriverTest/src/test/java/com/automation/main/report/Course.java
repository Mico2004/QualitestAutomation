package com.automation.main.report;

import java.util.List;


public class Course {

    public String courseName;

    public String courseType;

    public Instructor instructorA;

    public Instructor instructorB;

    public Student studentA;

    public Student studentB;

    public Student isCoursePast;

    public List<String> listOfRegularRecording;
    public List<String> listOfStudentRecording;
    public List<String> listOfTestRecording;

    public Course() {

    }

    public void setInstructorA(Instructor instructorA) {
        this.instructorA = instructorA;
    }

    public void setInstructorB(Instructor instructorB) {
        this.instructorB = instructorB;
    }

    public void setStudentA(Student studentA) {
        this.studentA = studentA;
    }

    public void setStudentB(Student studentB) {
        this.studentB = studentB;
    }

    public void setListOfRegularRecording(List<String> listOfRegularRecording) {
        this.listOfRegularRecording = listOfRegularRecording;
    }

    public List<String> getListOfStudentRecording() {
        return listOfStudentRecording;
    }

    public void setListOfStudentRecording(List<String> listOfStudentRecording) {
        this.listOfStudentRecording = listOfStudentRecording;
    }

    public List<String> getListOfTestRecording() {
        return listOfTestRecording;
    }

    public void setListOfTestRecording(List<String> listOfTestRecording) {
        this.listOfTestRecording = listOfTestRecording;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public Student getIsCoursePast() {
        return isCoursePast;
    }

    public void setIsCoursePast(Student isCoursePast) {
        this.isCoursePast = isCoursePast;
    }
}
