package net.ddns.mavedev.uscheddle.solving;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import net.ddns.mavedev.uscheddle.model.db.ScheduleModel;
import net.ddns.mavedev.uscheddle.model.request.create.ClassroomModel;
import net.ddns.mavedev.uscheddle.model.request.create.CourseModel;
import net.ddns.mavedev.uscheddle.model.request.create.GenerateRequestModel;

public class Solver {

    private Solver() {
    }

    public static ScheduleModel solve(final GenerateRequestModel data) {
        int classesInDay = 7; // TODO: get from data;
        ScheduleModel schedule = new ScheduleModel();
        InstructorSet instructors = getRootObserverSet(data);
        ClassroomObserver[] classroomObservers = getClassroomObservers(data);
        for (InstructorObserver instructor : instructors.getInstructors()) {
            for (GroupObserver group : instructor.getGroupObservers()) {
                boolean isLectureSuitableNeeded = group.getClassObserver().isLecture();
                while (group.getClassObserver().getUnallocatedMeetingsPerWeek() > 0) {
                    groupSatisfying: for (ClassroomObserver classroom : Arrays
                            .stream(classroomObservers)
                            .filter(c -> c.isLectureSuitable() == isLectureSuitableNeeded)
                            .toArray(ClassroomObserver[]::new)) {
                        for (int day = 0; day < StudyLoadObserver.DAYS_IN_WEEK; ++day) {
                            for (int lessonOrder = 0; lessonOrder < classesInDay; ++lessonOrder) {
                                if (!classroom.isBusyAt(day, lessonOrder)
                                        && !group.isBusyAt(day, lessonOrder)
                                        && !instructor.isBusyAt(day, lessonOrder)) {
                                    List<String[]> dayData = schedule.getDayData(day);
                                    dayData.add(new String[] {String.valueOf(lessonOrder),
                                            group.getClassObserver().getName(),
                                            instructor.getName(),
                                            String.valueOf(group.getGroupNumber()), "1 - 14",
                                            classroom.getNumber()});
                                    dayData.sort((a, b) -> a[0].compareTo(b[0]));
                                    group.getClassObserver().allocateMeeting();
                                    instructor.makeBusyAt(day, lessonOrder);
                                    group.makeBusyAt(day, lessonOrder);
                                    classroom.makeBusyAt(day, lessonOrder);
                                    break groupSatisfying;
                                }
                            }
                        }
                    }
                }
            }
        }
        return schedule;
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
            int groupsAmount = isLecture ? 1 : course.getNStudents() / minInGroup;

            String[] instructorNames = Arrays.copyOfRange(course.getInstructors(), 0, groupsAmount);
            int nameShift = 0;
            for (int i = 0; i < instructorNames.length; ++i) {
                if (instructorNames[i] == null) {
                    instructorNames[i] = instructorNames[nameShift++];
                }
            }

            for (int i = 0; i < instructorNames.length; ++i) {
                if (!instructors.has(instructorNames[i])) {
                    instructors.add(instructorNames[i]);
                }
                GroupObserver group = new GroupObserver(i + 1,
                        new ClassObserver(name, meetingsPerWeek, isLecture, classesInDay),
                        classesInDay);
                instructors.getInstructor(instructorNames[i]).addGroupObserver(group);
            }
        }
        return instructors;
    }

    private static ClassroomObserver[] getClassroomObservers(final GenerateRequestModel data) {
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

    public static void main(String[] args) {
        CourseModel[] courses = new CourseModel[3];
        courses[0] = new CourseModel("name1", "lecture", new String[] {"a"}, 20, 21);
        courses[1] = new CourseModel("name1", "practice", new String[] {"a", "b"}, 20, 21);
        courses[2] = new CourseModel("name3", "lecture", new String[] {"c"}, 20, 21);
        ClassroomModel[] classrooms = new ClassroomModel[4];
        classrooms[0] = new ClassroomModel("1-223", true);
        classrooms[1] = new ClassroomModel("1-224", true);
        classrooms[2] = new ClassroomModel("1-225", false);
        classrooms[3] = new ClassroomModel("1-226", true);
        GenerateRequestModel request =
                new GenerateRequestModel("n1", "o1", courses, classrooms, 20, 10);
        ScheduleModel schedule = solve(request);
        for (int i = 0; i < 6; ++i) {
            List<String[]> dayData = schedule.getDayData(i);
            System.out.println(i);
            for (String[] dayFields : dayData) {
                System.out.println(Arrays.toString(dayFields));
            }
        }
    }

}
