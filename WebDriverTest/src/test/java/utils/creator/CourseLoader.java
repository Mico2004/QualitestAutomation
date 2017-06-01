package utils.creator;

import com.automation.main.report.Course;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;


public class CourseLoader implements EntityLoader{

    String pathToJson = "\\\\teg-fs1\\qa\\Test Library\\Qualitest Automation\\Test-resources\\reportCourseData\\Course";

    ObjectMapper mapper = new ObjectMapper();


    public Course getCourseByType(String courseType){
        Course course = null;
        File jsonFile = new File(pathToJson+courseType+".json");
        if (jsonFile.exists()){
            try {
                course = mapper.readValue(jsonFile, Course.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            throw new RuntimeException("The required json file does not exist !");
        }
     return  course;
    }
}
