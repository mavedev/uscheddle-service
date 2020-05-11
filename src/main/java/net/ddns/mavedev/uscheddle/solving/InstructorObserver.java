package net.ddns.mavedev.uscheddle.solving;

import lombok.Getter;

public class InstructorObserver {

    @Getter
    private ClassObserver[] classObservers;
    private StudyLoadObserver studyLoadObserver;

    public InstructorObserver(final ClassObserver[] classObservers, final int classesInDay) {
        this.classObservers = classObservers;
        this.studyLoadObserver = new StudyLoadObserver(classesInDay);
    }

    public boolean isBusyAt(final int day, final int lessonOrderNumber) {
        return this.studyLoadObserver.isTimeBusyAt(day, lessonOrderNumber);
    }

}
