package net.ddns.mavedev.uscheddle.model.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@Document(collection = "schedules")
public class ScheduleModel {

    @Id
    @JsonProperty("id")
    private String id;
    @JsonProperty("ownerId")
    private String ownerId;
    @JsonProperty("name")
    private String name;
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

    public ScheduleModel() {
        this.mon = new ArrayList<String[]>();
        this.tue = new ArrayList<String[]>();
        this.wed = new ArrayList<String[]>();
        this.thu = new ArrayList<String[]>();
        this.fri = new ArrayList<String[]>();
        this.sat = new ArrayList<String[]>();
    }

    public boolean isValid() {
        return Stream.of(id, name, mon, tue, wed, thu, fri, sat).noneMatch(Objects::isNull);
    }

    @JsonIgnore
    public List<String[]> getDayData(final int day) {
        switch (day) {
            case 0:
                return this.mon;
            case 1:
                return this.tue;
            case 2:
                return this.wed;
            case 3:
                return this.thu;
            case 4:
                return this.fri;
            case 5:
                return this.sat;
            default:
                return null;
        }
    }

}
