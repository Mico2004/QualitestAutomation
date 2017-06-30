package com.automation.main.report.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Lenovo on 29/06/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class CourseViewManager {
    @JsonProperty("studentViewers")
    List<String>studentViewers;

    @JsonProperty("studentRecordings")
    List<String>studentRecordings;

    @JsonProperty("instructorViewer")
    List<String>instructorViewer;

    @JsonProperty("instructorRecordings")
    List<String>instructorRecordings;

    public List<String> getStudentViewers() {
        return studentViewers;
    }

    public List<String> getStudentRecordings() {
        return studentRecordings;
    }

    public List<String> getInstructorViewer() {
        return instructorViewer;
    }

    public List<String> getInstructorRecordings() {
        return instructorRecordings;
    }
}

