package net.ddns.mavedev.uscheddle.model.response;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.ddns.mavedev.uscheddle.model.db.ScheduleModel;

@Data
@AllArgsConstructor
public class ResponseModel {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("editable")
    private boolean isEditable;
    @JsonProperty("mon")
    private List<String[]> mon;
    @JsonProperty("tue")
    private List<String[]> tue;
    @JsonProperty("wed")
    private List<String[]> wed;
    @JsonProperty("thu")
    private List<String[]> thu;
    @JsonProperty("fri")
    private List<String[]> fri;
    @JsonProperty("sat")
    private List<String[]> sat;

    private ResponseModel() {
    }

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

    public static ResponseModel empty() {
        return new ResponseModel();
    }

    public ResponseModel editable() {
        this.isEditable = true;
        return this;
    }

    public boolean isValid() {
        return Stream.of(id, name, mon, tue, wed, thu, fri, sat).noneMatch(Objects::isNull);
    }

}
