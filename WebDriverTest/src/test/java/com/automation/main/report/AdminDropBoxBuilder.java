package com.automation.main.report;

import com.automation.main.report.content.DropBox;
import com.automation.main.report.content.DropBoxActions;
import com.automation.main.report.content.DropBoxImpl;
import com.automation.main.validator.ui.CustomAnalysisInstructorUiValidator;

import java.util.ArrayList;
import java.util.List;


public class AdminDropBoxBuilder extends DropBoxActions {

    public AdminDropBoxBuilder(String typesOfCourses) {
        this.typesOfCourses = typesOfCourses;
        initCourses();
    }


    public DropBox getPopualtedDropBox(CustomAnalysisInstructorUiValidator.DropDownType downType){
        return null;
    };

        public DropBox getPopualtedDropBox(AdminAutocompleteValidator.TypeOfActions downType){


            DropBox dropBox = null;

            switch (downType){

                case Course:
                    dropBox = getCoursesNames();
                    break;
                case SingelCourse:
                    dropBox = new DropBoxImpl();
                    String courseNameByType = getCourseNameByType("A");
                    List<String> single = new ArrayList();
                    single.add(courseNameByType);
                    dropBox.setListOfContent(single);
                    break;
                case SingleIns:
                    dropBox =  getAllEnrolledUsers();
                    break;
                case Instructors:
                    dropBox = getAllInstructors();
                    break;
                case studentRecording:
                    dropBox = getAllStudentRecordings();
                    break;
            }
            return dropBox;
        }




}
