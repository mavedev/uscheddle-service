package net.ddns.mavedev.uscheddle.model.request;

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

    @JsonProperty("courseInstructor")
    private String[] instructor;

    @JsonProperty("courseHours")
    private int hours;

    public boolean isValid() {
        return this.name != null && this.classesType != null && this.instructor != null
                && this.hours > 0;
    }
}
