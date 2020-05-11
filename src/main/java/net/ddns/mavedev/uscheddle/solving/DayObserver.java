package net.ddns.mavedev.uscheddle.solving;

import java.util.BitSet;

public class DayObserver {

    private BitSet classesPerDayFlags;

    private DayObserver(final int maxClassesPerDay) {
        this.classesPerDayFlags = new BitSet(maxClassesPerDay);
    }

    public static DayObserver fromMaxClassesPerDay(final int maxClassesPerDay) {
        return new DayObserver(maxClassesPerDay);
    }

    public boolean isTimeBusyAt(final int lessonOrderNumber) {
        return this.classesPerDayFlags.get(lessonOrderNumber);
    }

}
