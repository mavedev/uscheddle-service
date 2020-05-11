package net.ddns.mavedev.uscheddle.solving;

import java.util.ArrayList;
import java.util.List;
import net.ddns.mavedev.uscheddle.model.request.create.CourseModel;
import net.ddns.mavedev.uscheddle.model.request.create.GenerateRequestModel;

public class Solver {

    private Solver() {
    }

    public static boolean solve(final GenerateRequestModel data) {
        int classesInDay = 7; // TODO: get from data.
        int minInGroup = data.getMinInGroup();
        int weeks = 14; // TODO: get from data.
        CourseModel[] courses = data.getCourses();
        List<InstructorObserver> instructors = new ArrayList<>();
        for (CourseModel course : courses) {
            String name = course.getName();
            boolean isLecture = course.getClassesType().equals("lecture");
            int meetingsPerWeek = (int) Math.ceil((double) (course.getHours() / weeks));
            ClassObserver aClass = new ClassObserver(name, meetingsPerWeek, isLecture);
            for (String instructorName : course.getInstructors()) {

            }
        }
        return true;
    }

}
