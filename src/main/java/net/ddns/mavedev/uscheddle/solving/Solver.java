package net.ddns.mavedev.uscheddle.solving;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import net.ddns.mavedev.uscheddle.model.request.create.ClassroomModel;
import net.ddns.mavedev.uscheddle.model.request.create.CourseModel;
import net.ddns.mavedev.uscheddle.model.request.create.GenerateRequestModel;

public class Solver {

    private Solver() {
    }

    public static void solve(final GenerateRequestModel data) {
        InstructorSet instructors = getRootObserverSet(data);
        ClassroomObserver[] classroomObservers = data.getClassrooms();
        for (InstructorObserver instructor : instructors.getInstructors()) {
            for (GroupObserver group : instructor.getGroupObservers()) {

            }
        }
    }

    private static InstructorSet getRootObserverSet(final GenerateRequestModel data) {
        int classesInDay = 7; // TODO: get from data.
        int minInGroup = data.getMinInGroup();
        int weeks = 14; // TODO: get from data.
        CourseModel[] courses = data.getCourses();
        InstructorSet instructors = new InstructorSet(classesInDay);
        for (CourseModel course : courses) {
            String name = course.getName();
            boolean isLecture = course.getClassesType().equals("lecture");
            int meetingsPerWeek = (int) Math.ceil((double) (course.getHours() / weeks));
            int groupsAmount = course.getNStudents() / minInGroup;
            ClassObserver aClass =
                    new ClassObserver(name, meetingsPerWeek, isLecture, classesInDay);

            String[] instructorNames = Arrays.copyOfRange(course.getInstructors(), 0, groupsAmount);
            for (int i = 0; i < instructorNames.length; ++i) {
                if (!instructors.has(instructorNames[i])) {
                    instructors.add(instructorNames[i]);
                }
                GroupObserver group = new GroupObserver(i + 1, aClass, classesInDay);
                instructors.getInstructor(instructorNames[i]).addGroupObserver(group);
            }
        }
        return instructors;
    }

    private ClassroomObserver[] getClassroomObservers(final GenerateRequestModel data) {
        ClassroomModel[] classrooms = data.getClassrooms();
        ClassroomObserver[] classroomObservers = new ClassroomObserver[classrooms.length];
        for (int i = 0; i < classrooms.length; ++i) {
            classroomObservers[i] = new ClassroomObserver(classrooms[i].getNumber(),
                    classrooms[i].isLectureSuitable(), 7 // TODO: get from data.
            );
        }
        return classroomObservers;
    }

    private static class InstructorSet {

        @Getter
        private List<InstructorObserver> instructors = new ArrayList<>();
        private int classesInDay;

        public InstructorSet(final int classesInDay) {
            this.classesInDay = classesInDay;
        }

        public void add(final String name) {
            this.instructors.add(new InstructorObserver(name, this.classesInDay));
        }

        public boolean has(final String name) {
            Optional<InstructorObserver> optional =
                    this.instructors.stream().filter(i -> i.getName().equals(name)).findAny();
            return optional.isPresent();
        }

        public InstructorObserver getInstructor(final String name) {
            Optional<InstructorObserver> optional =
                    this.instructors.stream().filter(i -> i.getName().equals(name)).findAny();
            return optional.isPresent() ? optional.get() : null;
        }

    }

}
