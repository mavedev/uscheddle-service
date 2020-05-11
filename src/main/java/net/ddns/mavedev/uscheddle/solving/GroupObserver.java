package net.ddns.mavedev.uscheddle.solving;

import lombok.Getter;

public class GroupObserver {

    @Getter
    private int groupNumber;
    @Getter
    private ClassObserver classObserver;
    private StudyLoadObserver studyLoadObserver;

    public GroupObserver(final int groupNumber, final ClassObserver classObsever,
            final int classesInDay) {
        this.groupNumber = groupNumber;
        this.classObserver = classObsever;
        this.studyLoadObserver = new StudyLoadObserver(classesInDay);
    }

    public boolean isBusyAt(final int day, final int lessonOrderNumber) {
        return this.studyLoadObserver.isTimeBusyAt(day, lessonOrderNumber);
    }

    public void makeBusyAt(final int day, final int lessonOrderNumber) {
        this.studyLoadObserver.makeBusyAt(day, lessonOrderNumber);
    }

}
