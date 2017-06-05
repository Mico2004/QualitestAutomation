package utils.creator.json;

import com.automation.main.report.entity.Course;
import com.automation.main.report.entity.Instructor;
import com.automation.main.report.entity.Student;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class ConvertCourseToJson {

    public ConvertCourseToJson() {
        setPathToUniversityDataFolder();
    }

    private List<String> createdUsers;
    String pathToJson = "\\\\teg-fs1\\qa\\Test Library\\Qualitest Automation\\Test-resources\\reportCourseData";

    public void convertToJson(List<String> users, String courseName, CourseTypeTemplate courseTypeTemplate) {
        createdUsers = users;
        Course course = new Course();
        course.setCourseType(courseTypeTemplate.courseType);
        course.setCourseName(courseName);
        course.setInstructorA(getInstructor("ReportInsA"));
        course.setInstructorB(getInstructor("ReportInsB"));
        course.setStudentA(getStudent("ReportStudA"));
        course.setStudentB(getStudent("ReportStudB"));


        course.setListOfRegularRecording(courseTypeTemplate.uploudRecByInsA);
        course.setListOfStudentRecording(courseTypeTemplate.uploudRecByStuA);
        course.setListOfTestRecording(courseTypeTemplate.uploudTestRecByStuA);

        ObjectMapper mapper = new ObjectMapper();
        try {
            setPathToUniversityDataFolder();
            File newCourseJsonFile = new File(pathToJson + course.getCourseType() + ".json");
            if (!newCourseJsonFile.exists()) {
                newCourseJsonFile.createNewFile();
            }
            mapper.writeValue(newCourseJsonFile, course);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPathToUniversityDataFolder() {
        try {
            String pageUrl = System.getProperty("UniversityURL");
            String s = pageUrl.replaceAll("https://\\b", "");
            String universityName = s.replaceAll(".tegrity.com\\b", "");
            File universityFolder = new File(pathToJson + "\\" + universityName);
            if (!universityFolder.exists()) {
                System.out.println("Creating folder at " + pathToJson + " for storing course details");
                universityFolder.mkdir();
            }
            pathToJson = pathToJson + "\\" + universityName + "\\Course";


        } catch (Exception e) {

        }
    }

    private String getFullUserName(String preFixUserName) {
        for (String user : createdUsers) {
            if (user.startsWith(preFixUserName)) {
                return user;
            }
        }
        throw new RuntimeException("The user does not found");
    }

    private Instructor getInstructor(String prefixName) {
        Instructor instructor = new Instructor();
        String fullUserName = getFullUserName(prefixName);
        instructor.setUserName(fullUserName);
        return instructor;
    }

    private Student getStudent(String prefixName) {
        Student instructor = new Student();
        String fullUserName = getFullUserName(prefixName);
        instructor.setUserName(fullUserName);
        return instructor;
    }

}
