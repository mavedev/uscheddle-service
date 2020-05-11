package net.ddns.mavedev.uscheddle.solving;

import net.ddns.mavedev.uscheddle.model.request.create.CourseModel;
import net.ddns.mavedev.uscheddle.model.request.create.GenerateRequestModel;

public class Solver {

    private Solver() {
    }

    public static boolean solve(final GenerateRequestModel data) {
        CourseModel[] courses = data.getCourses();
        return true;
    }

}
