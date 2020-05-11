package net.ddns.mavedev.uscheddle.solving;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ClassObserver {

    @Getter
    private int hours;
    @Getter
    private int unallocatedMeetingsPerWeek;

    public void allocateMeeting() {
        --this.unallocatedMeetingsPerWeek;
    }

}
