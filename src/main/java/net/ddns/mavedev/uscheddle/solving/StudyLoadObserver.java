package net.ddns.mavedev.uscheddle.solving;

import lombok.Data;

@Data
public class StudyLoadObserver {

    public static int DAYS_IN_WEEK = 6;
    private DayObserver[] dayObservers = new DayObserver[DAYS_IN_WEEK];

    public StudyLoadObserver(final int classesInDay) {
        for (int i = 0; i < dayObservers.length; ++i) {
            this.dayObservers[i] = DayObserver.fromMaxClassesPerDay(classesInDay);
        }
    }

    public boolean isTimeBusyAt(final int day, final int lessonOrderNumber) {
        return this.dayObservers[day].isTimeBusyAt(lessonOrderNumber);
    }

}
