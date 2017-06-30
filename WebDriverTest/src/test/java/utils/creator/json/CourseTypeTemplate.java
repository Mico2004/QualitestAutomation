package utils.creator.json;

import com.automation.main.report.entity.CourseViewManager;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseTypeTemplate {

    @JsonProperty("isPastCourse")
    public String isPastCourse;

    @JsonProperty("courseType")
    public String courseType;

    @JsonProperty("instructors")
    public List<String> instructors;

    @JsonProperty("students")
    public List<String> students;

    @JsonProperty("uploudRecByinsA")
    public List<String> uploudRecByInsA;

    @JsonProperty("uploudRecByinsB")
    public List<String> uploudRecByInsB;

    @JsonProperty("uploudRecByStuA")
    public List<String> uploudRecByStuA;

    @JsonProperty("uploudTestRecByStuA")
    public List<String> uploudTestRecByStuA;


    @JsonProperty("recordingsToDelete")
    public List<String> recordingsToDelete;

    @JsonProperty("mainInstructor")
    public String mainInstructor;

    @JsonProperty("views")
    public CourseViewManager courseViewManager;


}
