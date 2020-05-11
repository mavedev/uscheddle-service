package net.ddns.mavedev.uscheddle.solving;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClassObserver {

    private String name;
    private int unallocatedMeetingsPerWeek;
    private boolean isLecture;

    public ClassObserver(final String name, final int meetingsPerWeek, final boolean isLecture,
            final int classesInDay) {
        this.name = name;
        this.unallocatedMeetingsPerWeek = meetingsPerWeek;
        this.isLecture = isLecture;
    }

    public void allocateMeeting() {
        --this.unallocatedMeetingsPerWeek;
    }

}
