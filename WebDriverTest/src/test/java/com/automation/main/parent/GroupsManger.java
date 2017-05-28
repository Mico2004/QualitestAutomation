package com.automation.main.parent;

import org.testng.annotations.BeforeGroups;
import utils.actions.PastCourseActions;
import utils.actions.RecordingActions;

/**
 * Created by Lenovo on 23/05/2017.
 */
public abstract class GroupsManger {

    @BeforeGroups(groups = "AddRecordingToAb")
    public void addRecordingToAb(){

        RecordingActions recordingActions = new RecordingActions();
        try {
            recordingActions.addRecordingsToCourse("Ab");
        } catch (Exception e) {
            System.out.println("Fails to add recodrindg to course that starting with Ab");
        }
    }

    @BeforeGroups(groups = "AddSrudentRecordingToAb")
    public void addSrudentRecordingToAb(){

        RecordingActions recordingActions = new RecordingActions();
        try {
            recordingActions.addStudentRecordingsToCourse("Ab");
        } catch(Exception e) {
            System.out.println("Fails to add student recodrindgs to course that starting with Ab");
        }
    }


    @BeforeGroups(groups = "AddSTestRecordingToAb")
    public void addTestRecordingToAb(){

        RecordingActions recordingActions = new RecordingActions();
        try {
            recordingActions.addTestsRecordingsToCourse("Ab");
        } catch (Exception e) {
            System.out.println("Fails to add test recodrindgs to course that starting with Ab");
        }
    }

    @BeforeGroups(groups = "pastCourse")
    public void moveCoursesToPast(){
        PastCourseActions pastCourseActions = new PastCourseActions();
        try {
            pastCourseActions.unActivePastCourses();
        } catch (Exception e) {
            System.out.println("Couldn't moving past courses which are active to past");
        }
    }
}
