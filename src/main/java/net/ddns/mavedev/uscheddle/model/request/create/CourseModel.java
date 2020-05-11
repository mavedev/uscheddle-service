package net.ddns.mavedev.uscheddle.model.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseModel {

    @JsonProperty("courseName")
    private String name;

    @JsonProperty("courseClassesType")
    private String classesType;

    @JsonProperty("courseInstructors")
    private String[] instructors;

    @JsonProperty("courseHours")
    private int hours;

    @JsonProperty("courseStudents")
    private int nStudents;

    public boolean isValid() {
        return this.name != null && this.classesType != null && this.instructors != null
                && this.hours > 0 && this.nStudents > 0;
    }
}
