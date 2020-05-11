package net.ddns.mavedev.uscheddle.solving;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClassObserver {

    private String name;
    private int unallocatedMeetingsPerWeek;
    private boolean isLecture;
    private GroupObserver[] groups;

    public ClassObserver(final GroupObserver[] groups) {
        this.groups = groups;
    }

    public void allocateMeeting() {
        --this.unallocatedMeetingsPerWeek;
    }

}
