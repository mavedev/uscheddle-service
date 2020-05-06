package net.ddns.mavedev.uscheddle.model.request;

import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateRequestModel {

    @JsonProperty("courses")
    private CourseModel[] courses;

    @JsonProperty("classes")
    private ClassroomModel[] classrooms;

    @JsonProperty("students")
    private int students;

    @JsonProperty("minInGroup")
    private int minInGroup;

    public boolean isValid() {
        return true;
    }
}
