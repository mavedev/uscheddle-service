package net.ddns.mavedev.uscheddle.solving;

import lombok.Data;

@Data
public class StudyLoadObserver {

    private static int DAYS_IN_WEEK = 6;
    private DayObserver[] dayObservers = new DayObserver[DAYS_IN_WEEK];

    public StudyLoadObserver(final int maxClassesPerDay) {
        for (int i = 0; i < dayObservers.length; ++i) {
            this.dayObservers[i] = DayObserver.fromMaxClassesPerDay(maxClassesPerDay);
        }
    }

    public boolean isTimeBusyAt(final int day, final int lessonOrderNumber) {
        return this.dayObservers[day].isTimeBusyAt(lessonOrderNumber);
    }

}
