package net.ddns.mavedev.uscheddle.model.response;

import java.util.Objects;
import java.util.stream.Stream;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ddns.mavedev.uscheddle.model.db.ScheduleModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseModel {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("mon")
    private String[] mon = new String[6];
    @JsonProperty("tue")
    private String[] tue = new String[6];
    @JsonProperty("wed")
    private String[] wed = new String[6];
    @JsonProperty("thu")
    private String[] thu = new String[6];
    @JsonProperty("fri")
    private String[] fri = new String[6];
    @JsonProperty("sat")
    private String[] sat = new String[6];

    public static ResponseModel fromScheduleModel(final ScheduleModel schedule) {
        ResponseModel response = new ResponseModel();
        response.id = schedule.getId();
        response.name = schedule.getName();
        response.mon = schedule.getMon();
        response.tue = schedule.getTue();
        response.wed = schedule.getWed();
        response.thu = schedule.getThu();
        response.fri = schedule.getFri();
        response.sat = schedule.getSat();
        return response;
    }

    public boolean isValid() {
        return Stream.of(id, name, mon, tue, wed, thu, fri, sat).noneMatch(Objects::isNull);
    }

}
