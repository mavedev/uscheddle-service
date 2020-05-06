package net.ddns.mavedev.uscheddle.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;
import lombok.Setter;

public class CourseModel {
    @Getter
    @Setter
    @Autowired
    @JsonProperty("courseName")
    private String name;

    @Getter
    @Setter
    @Autowired
    @JsonProperty("courseClassesType")
    private String classesType;

    @Getter
    @Setter
    @Autowired
    @JsonProperty("courseInstaructor")
    private String instructor;

    @Getter
    @Setter
    @Autowired
    @JsonProperty("courseHours")
    private int hours;
}