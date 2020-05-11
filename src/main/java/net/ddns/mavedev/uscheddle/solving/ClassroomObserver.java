package net.ddns.mavedev.uscheddle.solving;

import lombok.Getter;

public class ClassroomObserver {

    @Getter
    private String number;
    @Getter
    private boolean isLectureSuitable;
    private StudyLoadObserver studyLoadObserver;

    public ClassroomObserver(final String number, final boolean isLectureSuitable,
            final int maxClassesPerDay) {
        this.number = number;
        this.isLectureSuitable = isLectureSuitable;
        this.studyLoadObserver = new StudyLoadObserver(maxClassesPerDay);
    }

    public boolean isBusyAt(final int day, final int lessonOrderNumber) {
        return this.studyLoadObserver.isTimeBusyAt(day, lessonOrderNumber);
    }

}
