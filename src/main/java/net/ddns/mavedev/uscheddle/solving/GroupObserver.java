package net.ddns.mavedev.uscheddle.solving;

import lombok.Getter;

public class GroupObserver {

    @Getter
    private int groupNumber;
    @Getter
    private ClassObserver classObserver;
    private StudyLoadObserver studyLoadObserver;

    public GroupObserver(final int groupNumber, final ClassObserver classObserver,
            final int maxClassesPerDay) {
        this.groupNumber = groupNumber;
        this.classObserver = classObserver;
        this.studyLoadObserver = new StudyLoadObserver(maxClassesPerDay);
    }

}
