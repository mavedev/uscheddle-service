package net.ddns.mavedev.uscheddle.solving;

import lombok.Getter;

public class GroupObserver {

    @Getter
    private int groupNumber;
    private StudyLoadObserver studyLoadObserver;

    public GroupObserver(final int groupNumber, final int classesInDay) {
        this.groupNumber = groupNumber;
        this.studyLoadObserver = new StudyLoadObserver(classesInDay);
    }

    public boolean isBusyAt(final int day, final int lessonOrderNumber) {
        return this.studyLoadObserver.isTimeBusyAt(day, lessonOrderNumber);
    }

}
