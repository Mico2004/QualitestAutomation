package utils.creator;

import com.automation.main.report.entity.Course;
import com.fasterxml.jackson.databind.ObjectMapper;
import utils.UniversityConfigure;

import java.io.File;
import java.io.IOException;


public class CourseLoader implements EntityLoader {

    public CourseLoader() {
        setPathToUniversityDataFolder();
    }

    private String pathToJson = "\\\\teg-fs1\\qa\\Test Library\\Qualitest Automation\\Test-resources\\reportCourseData";

    ObjectMapper mapper = new ObjectMapper();


    public Course getCourseByType(String courseType) {
        Course course = null;
        File jsonFile = new File(pathToJson + courseType + ".json");
        if (jsonFile.exists()) {
            try {
                course = mapper.readValue(jsonFile, Course.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("The required json file does not exist !");
        }
        return course;
    }

    private void setPathToUniversityDataFolder() {
        try {
            String universityName = UniversityConfigure.getUniversityName();
            pathToJson = pathToJson + "\\" + universityName + "\\Course";

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
