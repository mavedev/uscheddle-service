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

    public ClassObserver(final String name, final int meetingsPerWeek, final boolean isLecture,
            final int groupsAmount, final int classesInDay) {
        this.name = name;
        this.unallocatedMeetingsPerWeek = meetingsPerWeek;
        this.isLecture = isLecture;
        this.groups = new GroupObserver[groupsAmount];
        for (int i = 0; i < groups.length; ++i) {
            this.groups[i] = new GroupObserver(i + 1, classesInDay);
        }
    }

    public void allocateMeeting() {
        --this.unallocatedMeetingsPerWeek;
    }

}
