package net.ddns.mavedev.uscheddle.solving;

import java.util.BitSet;

public class DayObserver {

    private BitSet classesPerDayFlags;

    private DayObserver(final int classesInDay) {
        this.classesPerDayFlags = new BitSet(classesInDay);
    }

    public static DayObserver fromMaxClassesPerDay(final int classesInDay) {
        return new DayObserver(classesInDay);
    }

    public boolean isTimeBusyAt(final int lessonOrderNumber) {
        return this.classesPerDayFlags.get(lessonOrderNumber);
    }

    public void setBusyAt(final int lessonOrderNumber) {
        this.classesPerDayFlags.set(lessonOrderNumber);
    }

}
