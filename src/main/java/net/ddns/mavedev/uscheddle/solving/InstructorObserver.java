package net.ddns.mavedev.uscheddle.solving;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class InstructorObserver {

    @Getter
    private String name;
    @Getter
    private List<ClassObserver> classObservers = new ArrayList<>();
    private StudyLoadObserver studyLoadObserver;

    public InstructorObserver(final String name, final int classesInDay) {
        this.name = name;
        this.studyLoadObserver = new StudyLoadObserver(classesInDay);
    }

    public void addClassObserver(final ClassObserver classObserver) {
        this.classObservers.add(classObserver);
    }

    public boolean isBusyAt(final int day, final int lessonOrderNumber) {
        return this.studyLoadObserver.isTimeBusyAt(day, lessonOrderNumber);
    }

}