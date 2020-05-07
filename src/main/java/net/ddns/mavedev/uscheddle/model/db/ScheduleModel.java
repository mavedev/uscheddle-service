package net.ddns.mavedev.uscheddle.model.db;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "schedules")
public class ScheduleModel {

    @Id
    @JsonProperty("id")
    private String id = "";
    @JsonProperty("ownerId")
    private String ownerId = "";
    @JsonProperty("name")
    private String name = "";
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

    public ScheduleModel(final String id, final String ownerId, final String name) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
    }

}
