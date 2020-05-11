package net.ddns.mavedev.uscheddle.solving;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class InstructorObserver {

    @Getter
    private String name;
    @Getter
    private List<GroupObserver> groupObservers = new ArrayList<>();
    private StudyLoadObserver studyLoadObserver;

    public InstructorObserver(final String name, final int classesInDay) {
        this.name = name;
        this.studyLoadObserver = new StudyLoadObserver(classesInDay);
    }

    public void addGroupObserver(final GroupObserver groupObserver) {
        this.groupObservers.add(groupObserver);
    }

    public boolean isBusyAt(final int day, final int lessonOrderNumber) {
        return this.studyLoadObserver.isTimeBusyAt(day, lessonOrderNumber);
    }

}
