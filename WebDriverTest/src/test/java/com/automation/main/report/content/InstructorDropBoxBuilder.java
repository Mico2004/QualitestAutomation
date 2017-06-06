package com.automation.main.report.content;

import com.automation.main.validator.ui.CustomAnalysisInstructorUiValidator;


public class InstructorDropBoxBuilder extends DropBoxActions {

    public InstructorDropBoxBuilder(String typesOfCourses) {
        this.typesOfCourses = typesOfCourses;
        initCourses();
    }


        @Override
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


}
