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
        int classesInDay = data.getClassesInDay();
        ScheduleModel schedule = new ScheduleModel();
        InstructorSet instructors = getRootObserverSet(data);
        ClassroomObserver[] classroomObservers = getClassroomObservers(data);
        for (InstructorObserver instructor : instructors.getInstructors()) {
            interrogateGroups(schedule, instructor, classroomObservers, classesInDay);
        }

        addFillers(schedule, classesInDay);
        return schedule;
    }

    private static void interrogateGroups(final ScheduleModel schedule,
            final InstructorObserver instructor, final ClassroomObserver[] classroomObservers,
            final int classesInDay) {
        for (GroupObserver group : instructor.getGroupObservers()) {
            boolean isLectureSuitableNeeded = group.getClassObserver().isLecture();
            interrogateClassrooms(schedule, instructor, group, classroomObservers,
                    isLectureSuitableNeeded, classesInDay);
        }
    }

    private static void interrogateClassrooms(final ScheduleModel schedule,
            final InstructorObserver instructor, final GroupObserver group,
            final ClassroomObserver[] classrooms, final boolean isLectureSuitableNeeded,
            final int classesInDay) {
        while (group.getClassObserver().getUnallocatedMeetingsPerWeek() > 0) {
            for (ClassroomObserver classroom : Arrays.stream(classrooms)
                    .filter(c -> c.isLectureSuitable() == isLectureSuitableNeeded)
                    .toArray(ClassroomObserver[]::new)) {
                boolean hasFound =
                        tryToFillNonBusyTime(schedule, classroom, group, instructor, classesInDay);
                if (hasFound) {
                    break;
                }
            }
        }
    }

    private static boolean tryToFillNonBusyTime(final ScheduleModel schedule,
            final ClassroomObserver classroom, final GroupObserver group,
            final InstructorObserver instructor, final int classesInDay) {
        for (int day = 0; day < StudyLoadObserver.DAYS_IN_WEEK; ++day) {
            for (int lessonOrder = 0; lessonOrder < classesInDay; ++lessonOrder) {
                if (checkAreNotBusy(schedule, classroom, group, instructor, day, lessonOrder)) {
                    fillDayData(schedule, classroom, group, instructor, day, lessonOrder);
                    processParticipants(schedule, classroom, group, instructor, day, lessonOrder);
                    return true; // Success.
                }
            }
        }
        return false; // Fail.
    }

    private static boolean checkAreNotBusy(final ScheduleModel schedule,
            final ClassroomObserver classroom, final GroupObserver group,
            final InstructorObserver instructor, final int day, final int lessonOrder) {
        return !classroom.isBusyAt(day, lessonOrder) && !group.isBusyAt(day, lessonOrder)
                && !instructor.isBusyAt(day, lessonOrder);
    }

    private static void fillDayData(final ScheduleModel schedule, final ClassroomObserver classroom,
            final GroupObserver group, final InstructorObserver instructor, final int day,
            final int lessonOrder) {
        List<String[]> dayData = schedule.getDayData(day);
        dayData.add(new String[] {String.valueOf(lessonOrder), group.getClassObserver().getName(),
                instructor.getName(), String.valueOf(group.getGroupNumber()),
                group.getClassObserver().isLecture() ? "lecture" : "practice",
                classroom.getNumber()});
    }

    private static void processParticipants(final ScheduleModel schedule,
            final ClassroomObserver classroom, final GroupObserver group,
            final InstructorObserver instructor, final int day, final int lessonOrder) {
        List<String[]> dayData = schedule.getDayData(day);
        dayData.sort((a, b) -> a[0].compareTo(b[0]));
        group.getClassObserver().allocateMeeting();
        instructor.makeBusyAt(day, lessonOrder);
        group.makeBusyAt(day, lessonOrder);
        classroom.makeBusyAt(day, lessonOrder);
    }

    private static InstructorSet getRootObserverSet(final GenerateRequestModel data) {
        int classesInDay = data.getClassesInDay();
        int minInGroup = data.getMinInGroup();
        int weeks = data.getWeeks();
        CourseModel[] courses = data.getCourses();
        InstructorSet instructors = new InstructorSet(classesInDay);
        for (CourseModel course : courses) {
            String name = course.getName();
            boolean isLecture = course.isLecture();
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
                    classrooms[i].isLectureSuitable(), data.getClassesInDay());
        }
        return classroomObservers;
    }

    private static void addFillers(final ScheduleModel schedule, final int classesInDay) {
        for (int day = 0; day < StudyLoadObserver.DAYS_IN_WEEK; ++day) {
            List<String[]> dayData = schedule.getDayData(day);
            int sizeDiff = 0;
            if ((sizeDiff = dayData.size() - classesInDay) < 0) {
                for (int i = 0; i < Math.abs(sizeDiff); ++i) {
                    dayData.add(new String[] {"", "", "", "", "", ""});
                }
            }
        }
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
